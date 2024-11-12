package com.br.biometric_tool.infra.exceptions

class InvalidStatus : RuntimeException {
    private val status: Int = 400

    constructor(message: String) : super(message)
}