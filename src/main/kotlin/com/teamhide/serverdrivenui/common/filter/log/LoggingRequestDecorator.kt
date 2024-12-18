package com.teamhide.serverdrivenui.common.filter.log

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpRequestDecorator
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.io.ByteArrayOutputStream
import java.nio.channels.Channels

private val logger = KotlinLogging.logger { }

class LoggingRequestDecorator internal constructor(request: ServerHttpRequest) : ServerHttpRequestDecorator(request) {
    private val body: Flux<DataBuffer>?

    init {
        val method = request.method
        val requestURI = request.uri.path
        val queryString = request.uri.query
        val formattedQueryString = queryString?.takeIf { it.isNotEmpty() }?.let { "?$it" } ?: ""
        val logMessage = "Request | $method $requestURI$formattedQueryString"

        if (request.headers.contentLength < 0) {
            logger.info { logMessage }
        }

        body = request.body.publishOn(Schedulers.boundedElastic()).doOnNext { buffer: DataBuffer ->
            val bodyStream = ByteArrayOutputStream()
            val channel = Channels.newChannel(bodyStream)
            buffer.readableByteBuffers().forEach { channel.write(it) }
            logger.info { "$logMessage | body=${String(bodyStream.toByteArray())}" }
        }
    }

    override fun getBody(): Flux<DataBuffer> {
        return body!!
    }
}
