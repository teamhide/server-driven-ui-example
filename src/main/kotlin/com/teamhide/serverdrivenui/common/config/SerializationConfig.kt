package com.teamhide.serverdrivenui.common.config

import kotlinx.serialization.json.Json
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SerializationConfig {
    @Bean
    fun kotlinJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = false
            allowSpecialFloatingPointValues = false
            useArrayPolymorphism = false
            encodeDefaults = false
        }
    }
}
