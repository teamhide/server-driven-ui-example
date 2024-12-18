package com.teamhide.serverdrivenui.common.exception

import com.teamhide.serverdrivenui.common.response.ApiResponse
import com.teamhide.serverdrivenui.common.response.FailBody
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.resource.NoResourceFoundException
import org.springframework.web.server.ServerWebInputException

private val logger = KotlinLogging.logger { }
@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(CustomException::class)
    suspend fun handleCustomException(e: CustomException): ApiResponse<FailBody> {
        val body = FailBody(errorCode = e.errorCode, message = e.message)
        return ApiResponse.fail(body = body, statusCode = e.statusCode)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    suspend fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ApiResponse<FailBody> {
        val errorConst = CommonErrorConst.HTTP_MESSAGE_NOT_READABLE
        val body = FailBody(errorCode = errorConst.errorCode, message = errorConst.message)
        return ApiResponse.fail(body, errorConst.statusCode)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    suspend fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ApiResponse<FailBody> {
        val errorConst = CommonErrorConst.METHOD_ARGUMENT_NOT_VALID
        val body = FailBody(errorCode = errorConst.errorCode, message = errorConst.message)
        return ApiResponse.fail(body, errorConst.statusCode)
    }

    @ExceptionHandler(ServerWebInputException::class)
    suspend fun handleServerWebInputException(e: ServerWebInputException): ApiResponse<FailBody> {
        val errorConst = CommonErrorConst.SERVER_WEB_INPUT_ERROR
        val body = FailBody(errorCode = errorConst.errorCode, message = errorConst.message)
        return ApiResponse.fail(body, errorConst.statusCode)
    }

    @ExceptionHandler(NoResourceFoundException::class)
    suspend fun handleNoResourceFoundException(e: NoResourceFoundException): ApiResponse<FailBody> {
        val errorConst = CommonErrorConst.NO_RESOURCE_FOUND_ERROR
        val body = FailBody(errorCode = errorConst.errorCode, message = errorConst.message)
        return ApiResponse.fail(body, errorConst.statusCode)
    }

    @ExceptionHandler(Exception::class)
    suspend fun handleException(e: Exception): ApiResponse<FailBody> {
        val errorConst = CommonErrorConst.UNKNOWN
        val body = FailBody(errorCode = errorConst.errorCode, message = errorConst.message)
        logger.error { "GlobalExceptionHandler | Unhandled exception $e" }
        return ApiResponse.fail(body, errorConst.statusCode)
    }
}
