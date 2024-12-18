package com.teamhide.serverdrivenui.common.filter.log

import io.github.oshai.kotlinlogging.KotlinLogging
import org.reactivestreams.Publisher
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.http.server.reactive.ServerHttpResponseDecorator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.io.ByteArrayOutputStream
import java.nio.channels.Channels

private val logger = KotlinLogging.logger { }

class LoggingResponseDecorator internal constructor(
    private val request: ServerHttpRequest,
    private val response: ServerHttpResponse
) :
    ServerHttpResponseDecorator(response) {

    override fun writeWith(body: Publisher<out DataBuffer>): Mono<Void> {
        val statusCode = response.statusCode
        val method = request.method
        val requestURI = request.uri.path
        val queryString = request.uri.query
        val formattedQueryString = queryString?.takeIf { it.isNotEmpty() }?.let { "?$it" } ?: ""
        val logMessage = "Response | $method $requestURI$formattedQueryString | $statusCode"

        return super.writeWith(
            Flux.from(body).publishOn(Schedulers.boundedElastic()).doOnNext { buffer: DataBuffer ->
                val bodyStream = ByteArrayOutputStream()
                val channel = Channels.newChannel(bodyStream)
                buffer.readableByteBuffers().forEach { channel.write(it) }
                logger.info { "$logMessage | body=${String(bodyStream.toByteArray())}" }
            }
        )
    }
}
