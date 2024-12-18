package com.teamhide.serverdrivenui.common.config

import com.teamhide.serverdrivenui.support.test.RedisCleaner
import com.teamhide.serverdrivenui.support.test.RedisTest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.redis.core.ReactiveRedisTemplate

@RedisTest
class RedisConfigTest(
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, String>,
) : BehaviorSpec({
    listeners(RedisCleaner())

    Given("키/값을") {
        val key = "hide"
        val value = "test"
        val ops = reactiveRedisTemplate.opsForValue()

        When("레디스에 저장을 시도하면") {
            ops.set(key, value).awaitFirstOrNull()

            Then("정상적으로 저장된다") {
                ops.get(key).awaitSingleOrNull() shouldBe value
            }
        }
    }
})
