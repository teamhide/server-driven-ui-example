package com.teamhide.serverdrivenui.common.security

import com.teamhide.serverdrivenui.common.util.jwt.DecodeTokenException
import com.teamhide.serverdrivenui.common.util.jwt.TokenProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationConverter(
    @Value("\${jwt.secret-key}")
    private val secretKey: String,
) : ServerAuthenticationConverter {
    companion object {
        private const val TOKEN_PREFIX = "Bearer "
    }

    private val tokenProvider by lazy { TokenProvider(secretKey = secretKey) }

    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        return Mono.justOrEmpty(exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION))
            .filter { it.startsWith(TOKEN_PREFIX) }
            .switchIfEmpty(Mono.defer { Mono.error(JwtAuthenticationFailException()) })
            .map {
                tokenProvider.decrypt(token = it.removePrefix(TOKEN_PREFIX))
            }
            .onErrorMap(DecodeTokenException::class.java) { _ ->
                JwtAuthenticationFailException()
            }
            .map { payload ->
                val currentUser = CurrentUser(id = payload.userId)
                UsernamePasswordAuthenticationToken(currentUser, null, currentUser.authorities)
            }
    }
}
