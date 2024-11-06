package com.br.biometric_tool.infra.exceptions

class ImageNotLoadedException: RuntimeException {

    constructor(message : String) : super(message)
}