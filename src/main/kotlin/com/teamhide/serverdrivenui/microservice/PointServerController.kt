package com.teamhide.serverdrivenui.microservice

import com.teamhide.serverdrivenui.microservice.dto.GetPointInfoResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/point")
class PointServerController {
    @GetMapping("/{userId}")
    suspend fun getPointInfo(@PathVariable("userId") userId: Long): GetPointInfoResponse {
        return GetPointInfoResponse(userId = userId, currentPoint = 25000)
    }
}
