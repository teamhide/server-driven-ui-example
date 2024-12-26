package com.teamhide.serverdrivenui.microservice

import com.teamhide.serverdrivenui.microservice.dto.Coupon
import com.teamhide.serverdrivenui.microservice.dto.GetCouponListResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/coupon")
class CouponServerController {
    @GetMapping
    suspend fun getCouponList(): GetCouponListResponse {
        val coupon1 = Coupon(
            couponId = 1L, name = "coupon1", expiredAt = LocalDateTime.now().plusDays(7), forFirstOrder = false,
        )
        val coupon2 = Coupon(
            couponId = 2L, name = "coupon2", expiredAt = LocalDateTime.now().plusDays(5), forFirstOrder = true,
        )
        return GetCouponListResponse(coupons = listOf(coupon1, coupon2))
    }
}
