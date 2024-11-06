package com.br.biometric_tool.infra.exceptions

class SchemaFileNotFoundException : RuntimeException {

    constructor(message : String) : super(message)
}