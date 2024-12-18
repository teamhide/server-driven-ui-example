package com.teamhide.serverdrivenui.common.security

import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationFilter(
    jwtAuthenticationConverter: JwtAuthenticationConverter,
    jwtAuthenticationManager: JwtAuthenticationManager,
    customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
) : AuthenticationWebFilter(jwtAuthenticationManager) {
    init {
        this.setServerAuthenticationConverter(jwtAuthenticationConverter)
        this.setAuthenticationFailureHandler { webFilterExchange, exception ->
            customAuthenticationEntryPoint.commence(webFilterExchange.exchange, exception)
        }
    }

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        if (shouldNotFilter(exchange = exchange)) {
            return chain.filter(exchange)
        }
        return super.filter(exchange, chain)
    }

    private fun shouldNotFilter(exchange: ServerWebExchange): Boolean {
        val request = exchange.request
        val path = request.path.toString()
        val method = request.method
        return AuthIgnorePaths.contain(authType = AuthType.JWT, method = method, path = path)
    }
}
