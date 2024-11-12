package com.br.biometric_tool.infra.exceptions

class TogglingBiometricsException : RuntimeException {

    constructor(message: String) : super(message)
}