package com.teamhide.serverdrivenui.common.config.database

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.r2dbc")
data class ConnectionFactoryProperties(
    val writer: ConnectionFactoryProperty,
    val reader: ConnectionFactoryProperty,
) {
    data class ConnectionFactoryProperty(
        val driver: String,
        val protocol: String,
        val host: String,
        val port: Int,
        val user: String,
        val password: String,
        val database: String,
    )
}
