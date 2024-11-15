package com.br.biometric_tool.core.domain.entity

import com.br.biometric_tool.core.domain.vo.Email
import com.br.biometric_tool.core.domain.vo.Name
import com.br.biometric_tool.core.domain.vo.Password
import java.util.UUID

class Account(
    val accountId: String,
    private var name: Name,
    private var email: Email,
    private var password: Password,
    private var biometricsEnabled: Boolean,
    private var biometricsUrls: MutableMap<String, String>
) {
    constructor(firstName: String, lastName: String, email: String, password: String, biometricsEnabled: Boolean = false) : this(
        UUID.randomUUID().toString(),
        Name(firstName, lastName),
        Email(email),
        Password.create(password),
        biometricsEnabled,
        mutableMapOf())

    constructor(accountId: String, firstName: String, lastName: String, email: String, password: String, biometricsEnabled: Boolean) : this(
        accountId,
        Name(firstName, lastName),
        Email(email),
        Password.restore(password),
        biometricsEnabled,
        mutableMapOf()
    )

    fun getBiometrics(): MutableMap<String, String> {
        return this.biometricsUrls;
    }

    fun addBiometric(key: String, url: String) {
        this.biometricsUrls[key] = url
    }

    fun getFirstName(): String {
        return this.name.getFirstName()
    }

    fun getLastName(): String {
        return this.name.getLastName()
    }

    fun getName(): String {
        return this.name.getName()
    }

    fun setName(firstName: String, lastName: String) {
        this.name = Name(firstName, lastName)
    }

    fun getEmail(): String {
        return this.email.getEmail()
    }

    fun setEmail(email: String) {
        this.email = Email(email)
    }

    fun getPassword(): String {
        return this.password.getPassword()
    }

    fun passwordMatches(password: String): Boolean {
        return this.password.passwordMatches(password)
    }

    fun setPassword(password: String) {
        this.password = Password.create(password)
    }

    fun isBiometricsEnabled(): Boolean {
        return this.biometricsEnabled
    }

    fun biometricsEnabled() {
        this.biometricsEnabled = true
    }

    fun biometricsDisabled() {
        this.biometricsEnabled = false
    }
}