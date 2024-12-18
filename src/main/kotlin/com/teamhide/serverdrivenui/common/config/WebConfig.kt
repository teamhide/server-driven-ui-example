package com.teamhide.serverdrivenui.common.config

import kotlinx.serialization.json.Json
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.http.codec.json.KotlinSerializationJsonEncoder
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
class WebConfig(
    private val kotlinJson: Json,
) : WebFluxConfigurer {
    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        configurer.defaultCodecs().configureDefaultCodec { KotlinSerializationJsonHttpMessageConverter(kotlinJson) }
        configurer.defaultCodecs().kotlinSerializationJsonEncoder(KotlinSerializationJsonEncoder(kotlinJson))
        configurer.defaultCodecs().kotlinSerializationJsonDecoder(KotlinSerializationJsonDecoder(kotlinJson))
    }
}
