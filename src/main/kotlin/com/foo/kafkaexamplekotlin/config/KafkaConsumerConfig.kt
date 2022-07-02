package com.foo.kafkaexamplekotlin.config

import org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.CommonClientConfigs.GROUP_ID_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.*
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.ContainerProperties.AckMode
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.util.backoff.FixedBackOff

@EnableKafka
@Configuration
class KafkaConsumerConfig(
    @Qualifier("producerKafkaTemplate")
    val kafkaTemplate: KafkaTemplate<String, String>
) {

    val configs = mapOf<String, Any>(
        BOOTSTRAP_SERVERS_CONFIG to "kafka:9092",
        GROUP_ID_CONFIG to "message-group",
        KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        ENABLE_AUTO_COMMIT_CONFIG to false
    )

    @Bean("containerFactory")
    fun kafkaListenerFactory() : ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        //Configura o DefaultErrorHandler para esse topico
        factory.setCommonErrorHandler(defaultErrorHandler())
        factory.consumerFactory = consumerFactory()
        factory.containerProperties.ackMode = AckMode.MANUAL
        return factory
    }

    @Bean("dltContainerFactory")
    fun dltKafkaListenerFactory() : ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory()
        factory.containerProperties.ackMode = AckMode.MANUAL
        return factory
    }

    @Bean
    fun consumerFactory() : ConsumerFactory<String, String>{
        return DefaultKafkaConsumerFactory(configs)
    }

    //Configuracao do DefaultErrorHandler, mensagens que irao para DLT vao para o topic.DLT
    @Bean
    fun defaultErrorHandler(): DefaultErrorHandler{
        val recoverer = DeadLetterPublishingRecoverer(kafkaTemplate)
        return DefaultErrorHandler(recoverer, FixedBackOff(1000L, 2))
    }

}