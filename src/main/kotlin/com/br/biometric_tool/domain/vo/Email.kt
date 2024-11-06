package com.br.biometric_tool.domain.vo

class Email(email: String) {
    private var email: String

    init {
        val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        if(!regex.matches(email)) throw IllegalArgumentException("Email inválido.")
        this.email = email
    }

    fun getEmail(): String = this.email
}