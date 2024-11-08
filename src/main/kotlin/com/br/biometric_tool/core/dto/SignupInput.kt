package com.br.biometric_tool.core.dto

data class SignupInput(val firstName: String, val lastName: String, val email: String, val password: String, val biometricsEnabled: Boolean,  val fingerprintUrls: MutableMap<String, String>)
