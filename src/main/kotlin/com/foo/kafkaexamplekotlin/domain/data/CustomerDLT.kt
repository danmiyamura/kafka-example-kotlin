package com.foo.kafkaexamplekotlin.domain.data

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.support.Acknowledgment

class CustomerDLT(
    val menssage: ConsumerRecord<String, String>,
    val acknowledge: Acknowledgment
)