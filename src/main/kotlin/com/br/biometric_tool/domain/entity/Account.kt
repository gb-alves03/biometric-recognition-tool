package com.br.biometric_tool.domain.entity

import com.br.biometric_tool.domain.vo.Name
import java.util.UUID

class Account(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    biometricsEnabled: Boolean,
    biometricsUrl: String = null.toString()
) {
    val accountId: String
    private var name: Name
    private var email: Email
    private var password: Password
    private var biometricsEnabled: Boolean = false
    var biometricsUrl: String? = null

    init {
        this.accountId = UUID.randomUUID().toString()
        this.name = Name(firstName, lastName)
        this.email = Email(email)
        this.password = Password.create(password)
    }
}