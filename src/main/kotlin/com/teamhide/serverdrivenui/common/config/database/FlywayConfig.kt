package com.teamhide.serverdrivenui.common.config.database

import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.flyway.FlywayProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(value = [FlywayProperties::class])
class FlywayConfig(
    private val connectionFactoryProperties: ConnectionFactoryProperties,
) {
    @Bean(initMethod = "migrate")
    fun flyway(flywayProperties: FlywayProperties): Flyway {
        val writerProperties = connectionFactoryProperties.writer
        return Flyway.configure()
            .dataSource(
                flywayProperties.url,
                writerProperties.user,
                writerProperties.password,
            )
            .locations(*flywayProperties.locations.toTypedArray())
            .baselineOnMigrate(true)
            .load()
    }
}
