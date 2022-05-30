package com.foo.kafkaexamplekotlin.config

import org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.CommonClientConfigs.RETRIES_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaProducerConfig {

    val configs = mapOf<String, Any>(
        BOOTSTRAP_SERVERS_CONFIG to "kafka:9092",
        RETRIES_CONFIG to 0,
        BUFFER_MEMORY_CONFIG to 33554432,
        KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
    )

    @Bean
    fun producerFactory() : ProducerFactory<String, String> {
        return DefaultKafkaProducerFactory(configs)
    }

    @Bean
    fun kafkaTemplate() : KafkaTemplate<String, String>{
        return KafkaTemplate(producerFactory())
    }
}