package com.br.biometric_tool.infra.exceptions

class InvalidStatusException : RuntimeException {
    private val status: Int = 400

    constructor(message: String) : super(message)
}