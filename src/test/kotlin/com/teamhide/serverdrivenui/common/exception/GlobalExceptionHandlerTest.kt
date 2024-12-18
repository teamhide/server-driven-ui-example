package com.teamhide.serverdrivenui.common.exception

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.springframework.core.MethodParameter
import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.reactive.resource.NoResourceFoundException
import org.springframework.web.server.ServerWebInputException

internal class GlobalExceptionHandlerTest : BehaviorSpec({
    Given("CustomException이 발생하는 경우") {
        class TestException : CustomException(statusCode = HttpStatus.NOT_FOUND, errorCode = "NOT_FOUND", message = "message")
        val exc = TestException()
        val handler = GlobalExceptionHandler()

        When("예외 핸들러가 동작하면") {
            val sut = handler.handleCustomException(e = exc)

            Then("FailBody로 감싸서 반환된다") {
                sut.body?.errorCode shouldBe exc.errorCode
                sut.body?.message shouldBe exc.message
                sut.statusCode shouldBe exc.statusCode
            }
        }
    }

    Given("HttpMessageNotReadableException이 발생하는 경우") {
        val mockHttpInputMessage = mockk<HttpInputMessage>()
        val exc = HttpMessageNotReadableException("message", mockHttpInputMessage)
        val handler = GlobalExceptionHandler()
        val errorConst = CommonErrorConst.HTTP_MESSAGE_NOT_READABLE

        When("예외 핸들러가 동작하면") {
            val sut = handler.handleHttpMessageNotReadableException(e = exc)

            Then("FailBody로 감싸서 반환된다") {
                sut.body?.errorCode shouldBe errorConst.errorCode
                sut.body?.message shouldBe errorConst.message
                sut.statusCode shouldBe errorConst.statusCode
            }
        }
    }

    Given("MethodArgumentNotValidException 발생하는 경우") {
        val mockMethodParameter = mockk<MethodParameter>()
        val mockBindingResult = mockk<BindingResult>()
        val exc = MethodArgumentNotValidException(mockMethodParameter, mockBindingResult)
        val handler = GlobalExceptionHandler()
        val errorConst = CommonErrorConst.METHOD_ARGUMENT_NOT_VALID

        When("예외 핸들러가 동작하면") {
            val sut = handler.handleMethodArgumentNotValidException(e = exc)

            Then("FailBody로 감싸서 반환된다") {
                sut.body?.errorCode shouldBe errorConst.errorCode
                sut.body?.message shouldBe errorConst.message
                sut.statusCode shouldBe errorConst.statusCode
            }
        }
    }

    Given("ServerWebInputException 발생하는 경우") {
        val exc = ServerWebInputException("error")
        val handler = GlobalExceptionHandler()
        val errorConst = CommonErrorConst.SERVER_WEB_INPUT_ERROR

        When("예외 핸들러가 동작하면") {
            val sut = handler.handleServerWebInputException(e = exc)

            Then("FailBody로 감싸서 반환된다") {
                sut.body?.errorCode shouldBe errorConst.errorCode
                sut.body?.message shouldBe errorConst.message
                sut.statusCode shouldBe errorConst.statusCode
            }
        }
    }

    Given("NoResourceFoundException 발생하는 경우") {
        val exc = NoResourceFoundException("error")
        val handler = GlobalExceptionHandler()
        val errorConst = CommonErrorConst.NO_RESOURCE_FOUND_ERROR

        When("예외 핸들러가 동작하면") {
            val sut = handler.handleNoResourceFoundException(e = exc)

            Then("FailBody로 감싸서 반환된다") {
                sut.body?.errorCode shouldBe errorConst.errorCode
                sut.body?.message shouldBe errorConst.message
                sut.statusCode shouldBe errorConst.statusCode
            }
        }
    }

    Given("Exception 발생하는 경우") {
        val exc = Exception()
        val handler = GlobalExceptionHandler()
        val errorConst = CommonErrorConst.UNKNOWN

        When("예외 핸들러가 동작하면") {
            val sut = handler.handleException(e = exc)

            Then("FailBody로 감싸서 반환된다") {
                sut.body?.errorCode shouldBe errorConst.errorCode
                sut.body?.message shouldBe errorConst.message
                sut.statusCode shouldBe errorConst.statusCode
            }
        }
    }
})
