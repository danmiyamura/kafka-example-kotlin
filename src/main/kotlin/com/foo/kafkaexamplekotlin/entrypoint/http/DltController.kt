package com.foo.kafkaexamplekotlin.entrypoint.http

import com.foo.kafkaexamplekotlin.usecase.ReprocessDltUseCase
import org.slf4j.Logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/dlt")
@RestController
class DltController(
    val useCase: ReprocessDltUseCase,
    val log: Logger
) {

    @GetMapping("/reprocess")
    fun reprocessDlt(): String{
        log.info("reprocess invoked")
        return useCase.reprocess()
    }
}