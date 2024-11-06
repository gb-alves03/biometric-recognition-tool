package com.br.biometric_tool.domain.vo

class Email(email: String) {
    private var email: String

    init {
        if(!email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))) throw IllegalArgumentException("Email inválido.")
        this.email = email
    }

    fun getEmail(): String {
        return this.email
    }
}