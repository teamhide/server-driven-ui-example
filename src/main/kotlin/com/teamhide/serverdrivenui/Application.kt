package com.teamhide.serverdrivenui

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import reactor.core.publisher.Hooks

@SpringBootApplication
@ConfigurationPropertiesScan
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
    Hooks.enableAutomaticContextPropagation()
}
