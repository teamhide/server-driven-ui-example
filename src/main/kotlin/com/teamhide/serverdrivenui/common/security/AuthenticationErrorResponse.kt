package com.teamhide.serverdrivenui.common.security

data class AuthenticationErrorResponse(
    val errorCode: String = "UNAUTHORIZED",
    val message: String = "Authentication error",
)
