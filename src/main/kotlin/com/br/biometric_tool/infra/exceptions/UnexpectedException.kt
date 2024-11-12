package com.br.biometric_tool.infra.exceptions

class UnexpectedException : RuntimeException {
    private val status: Int = 500
    constructor(message: String) : super(message)
}