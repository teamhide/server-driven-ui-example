package com.teamhide.serverdrivenui.support.test

import io.kotest.core.listeners.TestListener
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.test.context.TestContextManager

@SpringBootTest
@TestEnvironment
class RedisCleaner : TestListener {
    lateinit var reactiveRedisTemplate: ReactiveRedisTemplate<String, String>

    @Suppress("UNCHECKED_CAST")
    override suspend fun afterContainer(testCase: TestCase, result: TestResult) {
        val testContextManager = TestContextManager(this::class.java)
        testContextManager.prepareTestInstance(this)
        reactiveRedisTemplate = testContextManager
            .testContext
            .applicationContext
            .getBean("reactiveRedisTemplate", ReactiveRedisTemplate::class.java) as ReactiveRedisTemplate<String, String>
        reactiveRedisTemplate.execute { connection
            ->
            connection.serverCommands().flushAll()
        }.subscribe()
    }
}
