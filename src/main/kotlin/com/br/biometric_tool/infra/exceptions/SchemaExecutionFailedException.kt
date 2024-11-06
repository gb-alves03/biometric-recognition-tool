package com.br.biometric_tool.infra.exceptions

class SchemaExecutionFailedException : RuntimeException {

    constructor(message : String) : super(message)
}