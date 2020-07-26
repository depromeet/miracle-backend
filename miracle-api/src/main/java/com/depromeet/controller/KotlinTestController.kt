package com.depromeet.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class KotlinTestController {

    @GetMapping("/ping/kotlin")
    fun pingKotlin(): String {
        return "pong"
    }

}
