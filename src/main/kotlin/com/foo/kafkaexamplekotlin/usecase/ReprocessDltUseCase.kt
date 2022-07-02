package com.foo.kafkaexamplekotlin.usecase

import com.foo.kafkaexamplekotlin.domain.data.Customer
import com.foo.kafkaexamplekotlin.domain.data.CustomerDLT
import com.foo.kafkaexamplekotlin.entrypoint.listener.DltMessageListener
import com.google.gson.Gson
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

private const val topic = "customer-message"

@Service
class ReprocessDltUseCase(
    @Qualifier("producerKafkaTemplate")
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val listener: DltMessageListener,
    private val log: Logger
) {

    fun reprocess(): String{
        if(!listener.dltRecordList.isEmpty()){
            var listAux = ArrayList<CustomerDLT>()
            var count = 0

            //Adiciona lista de DLT no momento do acionamento do metodo para lista auxiliar, para nao haver perdas de mensagens
            listener.dltRecordList.forEach {
                count++
                listAux.add(it)
            }

            log.info("Process {} message(s) from {}.DLT", count, topic)

            listAux.forEach {
                val record = ProducerRecord<String, String>(topic, removeException(it.menssage))

                //Envia mensagem para topico principal
                kafkaTemplate.send(record)

                //Faz o commit da mensagem
                it.acknowledge.acknowledge()

                //Remove o registro da lista principal
                listener.dltRecordList.remove(it)

            }
            return String.format("Processed %d from %s", count, topic)

        } else {
            log.info("DLT list is empty")
        }
        return String.format("No messages from %s.DLT", topic)
    }

    fun removeException(record: ConsumerRecord<String, String>): String {
        var data = Gson().fromJson(record.value(), Customer::class.java)

        data.name = "Exception removed"
        data.message = "Reprocessed"

        return Gson().toJson(data)
    }
}
