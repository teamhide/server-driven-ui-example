package com.teamhide.serverdrivenui.common.security

import org.springframework.security.core.AuthenticationException

class JwtAuthenticationFailException : AuthenticationException("AUTHENTICATION_FAIL")
