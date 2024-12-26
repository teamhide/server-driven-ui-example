package com.teamhide.serverdrivenui.microservice

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserServerController {
    @GetMapping("/{userId}")
    fun getUserInfo(@PathVariable("userId") userId: Long) {
    }
}
