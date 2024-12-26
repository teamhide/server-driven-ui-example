package com.teamhide.serverdrivenui.microservice.dto

data class GetUserOrderInfoResponse(
    val userId: Long,
    val isFirstOrder: Boolean,
    val accumulatedOrderAmount: Int,
)
