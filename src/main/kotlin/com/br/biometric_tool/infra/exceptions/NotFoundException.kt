package com.br.biometric_tool.infra.exceptions

class NotFoundException : RuntimeException {
    private val status: Int = 404

    constructor(message: String): super(message)
}