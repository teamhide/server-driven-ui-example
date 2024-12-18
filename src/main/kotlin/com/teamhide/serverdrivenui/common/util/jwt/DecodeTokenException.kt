package com.teamhide.serverdrivenui.common.util.jwt

import com.teamhide.serverdrivenui.common.exception.CustomException
import org.springframework.http.HttpStatus

class DecodeTokenException : CustomException(
    statusCode = HttpStatus.UNAUTHORIZED,
    errorCode = "AUTH__INVALID_TOKEN",
    message = ""
)
