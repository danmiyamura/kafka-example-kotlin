package com.foo.kafkaexamplekotlin.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InjectionPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope


@Configuration
class LoggerConfig {

    @Bean
    @Scope("prototype") // Cria uma nova instancia a cada ponto de injecao
    fun logger(injectionPoint: InjectionPoint): Logger {
        return LoggerFactory.getLogger(
            injectionPoint.methodParameter?.containingClass // constructor
                ?:injectionPoint.field?.declaringClass) // or field injection
    }
}