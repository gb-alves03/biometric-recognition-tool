package com.br.biometric_tool.infra.exceptions

class DatabaseNotConnectedException : RuntimeException {

    constructor(message : String) : super(message)
}