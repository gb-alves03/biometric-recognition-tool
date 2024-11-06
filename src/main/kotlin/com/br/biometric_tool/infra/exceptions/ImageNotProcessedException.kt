package com.br.biometric_tool.infra.exceptions

class ImageNotProcessedException : RuntimeException {

    constructor(message : String) : super(message)
}