package com.teamhide.serverdrivenui.common.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper,
) : ServerAuthenticationEntryPoint {
    override fun commence(exchange: ServerWebExchange, ex: AuthenticationException): Mono<Void> {
        val errorResponse = AuthenticationErrorResponse()
        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
        exchange.response.headers.contentType = MediaType.APPLICATION_JSON

        return exchange.response.writeWith(
            Mono.just(exchange.response.bufferFactory().wrap(objectMapper.writeValueAsBytes(errorResponse)))
        )
    }
}
