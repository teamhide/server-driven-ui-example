package com.teamhide.serverdrivenui.microservice

import com.teamhide.serverdrivenui.microservice.dto.GetUserInfoResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserServerController {
    @GetMapping("/{userId}")
    suspend fun getUserInfo(@PathVariable("userId") userId: Long): GetUserInfoResponse {
        return GetUserInfoResponse(userId = userId, nickname = "hide")
    }
}
