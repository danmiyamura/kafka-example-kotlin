package com.foo.kafkaexamplekotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaExampleKotlinApplication

fun main(args: Array<String>) {
	runApplication<KafkaExampleKotlinApplication>(*args)
}
