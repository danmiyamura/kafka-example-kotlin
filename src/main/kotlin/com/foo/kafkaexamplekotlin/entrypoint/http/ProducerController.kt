package com.foo.kafkaexamplekotlin.entrypoint.http

import com.foo.kafkaexamplekotlin.domain.data.Customer
import com.google.gson.Gson
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private const val topic = "customer-message"

@RestController
@RequestMapping("/producer")
class ProducerController(
    @Qualifier("producerKafkaTemplate")
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val log: Logger
) {

    @PostMapping("/customer")
    fun sendMessage(@RequestBody customer: Customer) {
        val customerJson = Gson().toJson(customer)

        //Se ao instanciar um ProducerRecord inserir uma chave, ele enviará mensagem apenas para uma partição do tópico
        val record = ProducerRecord<String, String>(topic, customerJson)

        kafkaTemplate.send(record)
        log.info("Send mensage for topic: {}", topic)
    }
}