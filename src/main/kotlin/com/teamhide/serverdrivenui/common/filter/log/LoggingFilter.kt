package com.teamhide.serverdrivenui.common.filter.log

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.CoWebFilter
import org.springframework.web.server.CoWebFilterChain
import org.springframework.web.server.ServerWebExchange

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class LoggingFilter : CoWebFilter() {
    override suspend fun filter(exchange: ServerWebExchange, chain: CoWebFilterChain) {
        return chain.filter(LoggingDecorator(exchange = exchange))
    }
}
