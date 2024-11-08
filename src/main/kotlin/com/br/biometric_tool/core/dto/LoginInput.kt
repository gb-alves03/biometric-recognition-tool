package com.br.biometric_tool.core.dto

data class LoginInput(val email: String, val password: String? = null, val biometricsUrl: String? = null)
