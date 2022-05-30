package com.foo.kafkaexamplekotlin.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class TopicConfig {

    @Bean
    fun admin() = KafkaAdmin(mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to "kafka:9092"))

    @Bean
    @Primary
    fun topicCustomerMessage() = TopicBuilder
        .name("customer-message")
        .partitions(8)
        .build()
}