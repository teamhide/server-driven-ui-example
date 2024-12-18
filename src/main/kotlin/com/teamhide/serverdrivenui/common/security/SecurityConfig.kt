package com.teamhide.serverdrivenui.common.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val jwtAuthenticationManager: JwtAuthenticationManager,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
) {
    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .httpBasic { httpBasic -> httpBasic.disable() }
            .csrf { csrf -> csrf.disable() }
            .formLogin { formLogin -> formLogin.disable() }
            .logout { logout -> logout.disable() }
            .authenticationManager(jwtAuthenticationManager)
            .addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .exceptionHandling { authenticationException ->
                authenticationException.authenticationEntryPoint(customAuthenticationEntryPoint)
            }
            .authorizeExchange {
                exchange ->
                exchange
                    .pathMatchers(*AuthIgnorePaths.authIgnorePaths.toTypedArray())
                    .permitAll()
                    .anyExchange()
                    .authenticated()
            }
            .build()
    }
}
