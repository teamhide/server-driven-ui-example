package com.teamhide.serverdrivenui.common.filter.log

import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.ServerWebExchangeDecorator

class LoggingDecorator(exchange: ServerWebExchange) : ServerWebExchangeDecorator(exchange) {
    private val requestDecorator = LoggingRequestDecorator(request = exchange.request)
    private val responseDecorator = LoggingResponseDecorator(request = exchange.request, response = exchange.response)

    override fun getRequest(): ServerHttpRequest {
        return requestDecorator
    }

    override fun getResponse(): ServerHttpResponse {
        return responseDecorator
    }
}
