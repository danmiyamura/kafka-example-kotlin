package com.foo.kafkaexamplekotlin.entrypoint.listener

import com.foo.kafkaexamplekotlin.domain.data.Customer
import com.google.gson.Gson
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
@EnableKafka
class CustomerMessageListener(
    private val log: Logger
) {

    @KafkaListener(
        id = "message",
        topics = ["customer-message"],
        containerFactory = "containerFactory"
    )
    fun listenerTopicMessage(record: ConsumerRecord<String, String>, ack: Acknowledgment){

        val data = Gson().fromJson(record.value(), Customer::class.java)

        log.info("{} - {} years old, says: {}", data.name, data.age, data.message)

        //Faz o commit da mensagem e marca ela como lida
        ack.acknowledge()
    }
}
