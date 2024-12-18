package com.teamhide.serverdrivenui.common.exception

import org.springframework.http.HttpStatus
import java.lang.RuntimeException

open class CustomException(
    val statusCode: HttpStatus,
    val errorCode: String,
    override val message: String,
) : RuntimeException()
