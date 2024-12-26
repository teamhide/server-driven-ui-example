package com.teamhide.serverdrivenui.microservice

import com.teamhide.serverdrivenui.microservice.dto.GetUserOrderInfoResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/order")
class OrderServerController {
    @GetMapping("/{userId}")
    suspend fun getUserOrderInfo(@PathVariable("userId") userId: Long): GetUserOrderInfoResponse {
        return GetUserOrderInfoResponse(
            userId = userId, isFirstOrder = true, accumulatedOrderAmount = 150000,
        )
    }
}
