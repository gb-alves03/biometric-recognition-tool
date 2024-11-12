package com.br.biometric_tool.infra.exceptions

class LoginFailedException : RuntimeException {
    private val status: Int = 403

    constructor(message: String) : super(message)
}