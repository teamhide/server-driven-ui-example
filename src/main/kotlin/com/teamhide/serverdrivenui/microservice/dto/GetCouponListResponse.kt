package com.teamhide.serverdrivenui.microservice.dto

import java.time.LocalDateTime

data class Coupon(
    val couponId: Long,
    val name: String,
    val expiredAt: LocalDateTime,
    val forFirstOrder: Boolean,
)

data class GetCouponListResponse(
    val coupons: List<Coupon>,
)
