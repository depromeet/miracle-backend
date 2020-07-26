package com.depromeet.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {

    @GetMapping("/ping")
    fun pingKotlin(): String {
        return "pong"
    }

}
