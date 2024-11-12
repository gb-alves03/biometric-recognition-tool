package com.br.biometric_tool.core.domain.vo

import com.br.biometric_tool.infra.exceptions.InvalidStatus

class Email(email: String) {
    private var email: String

    init {
        if(!email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))) throw InvalidStatus("Email inválido.")
        this.email = email
    }

    fun getEmail(): String {
        return this.email
    }
}