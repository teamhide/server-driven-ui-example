package com.teamhide.serverdrivenui.common.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig(
    @Value("\${spring.data.redis.host}")
    private val host: String,

    @Value("\${spring.data.redis.port}")
    private val port: Int,
) {
    @Bean
    fun reactiveRedisConnectionFactory() = LettuceConnectionFactory(host, port)

    @Bean
    fun reactiveRedisTemplate(reactiveRedisFactory: LettuceConnectionFactory): ReactiveRedisTemplate<String, String> {
        val keySerializer = StringRedisSerializer()
        val valueSerializer = Jackson2JsonRedisSerializer(String::class.java)
        val context = RedisSerializationContext.newSerializationContext<String, String>()
            .key(keySerializer)
            .value(valueSerializer)
            .hashKey(keySerializer)
            .hashValue(valueSerializer)
            .build()
        return ReactiveRedisTemplate(reactiveRedisFactory, context)
    }
}
