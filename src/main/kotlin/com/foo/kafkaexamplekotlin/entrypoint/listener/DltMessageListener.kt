package com.foo.kafkaexamplekotlin.entrypoint.listener

import com.foo.kafkaexamplekotlin.domain.data.CustomerDLT
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Service

@Service
@EnableKafka
class DltMessageListener(
    val log: Logger
) {

    var dltRecordList = ArrayList<CustomerDLT>()

    @KafkaListener(
        id = "message-dlt",
        topics = ["customer-message.DLT"],
        containerFactory = "dltContainerFactory"
    )
    fun dltListener(record: ConsumerRecord<String, String>, acknowledgment: Acknowledgment){
        log.info("Add record to DLT list")
        dltRecordList.add(CustomerDLT(record, acknowledgment))
    }
}