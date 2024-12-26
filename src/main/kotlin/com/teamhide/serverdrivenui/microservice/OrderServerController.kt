package com.teamhide.serverdrivenui.microservice

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/order")
class OrderServerController {
    @GetMapping("/{userId}")
    fun getUserOrderInfo(@PathVariable("userId") userId: Long) {
    }
}
