package com.teamhide.serverdrivenui.common.filter.context

import io.micrometer.context.ContextRegistry
import jakarta.annotation.PostConstruct
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.util.context.Context
import java.util.UUID

@Component
class ContextLocalFilter : WebFilter {
    companion object {
        const val MDC_KEY = "MDC_KEY"
        const val CONTEXT_KEY = "X-Request-Id"
    }

    @PostConstruct
    fun setUp() {
        ContextRegistry.getInstance().registerThreadLocalAccessor(
            MDC_KEY,
            MDC::getCopyOfContextMap,
            MDC::setContextMap,
            MDC::clear,
        )
    }
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val uuid = UUID.randomUUID()
        return chain.filter(exchange).contextWrite(Context.of(MDC_KEY, mapOf(CONTEXT_KEY to uuid)))
    }
}
