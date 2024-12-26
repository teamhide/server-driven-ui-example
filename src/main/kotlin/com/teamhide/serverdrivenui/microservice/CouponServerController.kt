package com.teamhide.serverdrivenui.microservice

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/coupon")
class CouponServerController {
    @GetMapping
    fun getCouponList() {
    }
}
