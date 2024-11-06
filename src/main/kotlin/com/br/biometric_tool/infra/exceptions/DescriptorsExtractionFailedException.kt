package com.br.biometric_tool.infra.exceptions

class DescriptorsExtractionFailedException : RuntimeException {

    constructor(message : String) : super(message)
}