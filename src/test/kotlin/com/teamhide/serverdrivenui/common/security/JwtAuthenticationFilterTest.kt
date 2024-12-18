package com.teamhide.serverdrivenui.common.security

import com.teamhide.serverdrivenui.support.test.IntegrationTest
import com.teamhide.serverdrivenui.user.EXPIRED_TOKEN
import com.teamhide.serverdrivenui.user.USER_ID_1_TOKEN
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JwtAuthenticationTestController {
    @GetMapping("/test-auth-required")
    suspend fun authRequired() {}

    @GetMapping("/test-auth-not-required")
    suspend fun authNotRequired() {}
}

@IntegrationTest
class JwtAuthenticationFilterTest(
    private val webTestClient: WebTestClient,
) : BehaviorSpec({
    val errorResponse = AuthenticationErrorResponse()

    Given("인증되지 않은 유저가") {
        When("인증이 필요한 URL을 요청하면") {
            val response = webTestClient
                .get()
                .uri("/test-auth-required")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()

            Then("401이 반환된다") {
                response.expectStatus().isUnauthorized
                response.expectBody()
                    .jsonPath("errorCode").isEqualTo(errorResponse.errorCode)
                    .jsonPath("message").isEqualTo(errorResponse.message)
            }
        }

        When("인증이 필요하지 않은 URL을 요청하면") {
            val response = webTestClient
                .get()
                .uri("/test-auth-not-required")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()

            Then("200이 반환된다") {
                response.expectStatus().isOk
            }
        }
    }

    Given("인증된 유저가") {
        When("인증이 필요한 URL을 요청하면") {
            val response = webTestClient
                .get()
                .uri("/test-auth-required")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer $USER_ID_1_TOKEN")
                .exchange()

            Then("200이 반환된다") {
                response.expectStatus().isOk
            }
        }

        When("인증이 필요하지 않은 URL을 요청하면") {
            val response = webTestClient
                .get()
                .uri("/test-auth-not-required")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer $USER_ID_1_TOKEN")
                .exchange()

            Then("200이 반환된다") {
                response.expectStatus().isOk
            }
        }
    }

    Given("인증이 만료된 유저가") {
        When("인증이 필요한 URL을 요청하면") {
            val response = webTestClient
                .get()
                .uri("/test-auth-required")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer $EXPIRED_TOKEN")
                .exchange()

            Then("401 반환된다") {
                response.expectStatus().isUnauthorized
            }
        }

        When("인증이 필요하지 않은 URL을 요청하면") {
            val response = webTestClient
                .get()
                .uri("/test-auth-not-required")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer $USER_ID_1_TOKEN")
                .exchange()

            Then("200이 반환된다") {
                response.expectStatus().isOk
            }
        }
    }
})
