package com.br.biometric_tool.domain.entity

import com.br.biometric_tool.domain.vo.Email
import com.br.biometric_tool.domain.vo.Name
import com.br.biometric_tool.domain.vo.Password
import java.util.UUID

class Account(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    biometricsEnabled: Boolean
) {
    val accountId: String = UUID.randomUUID().toString()
    private var name: Name
    private var email: Email
    private var password: Password
    private var biometricsEnabled: Boolean = false
    private var biometricsUrls: MutableMap<String, String>

    init {
        this.name = Name(firstName, lastName)
        this.email = Email(email)
        this.password = Password.create(password)
        this.biometricsEnabled = biometricsEnabled
        this.biometricsUrls = mutableMapOf()
    }

    fun getBiometrics(): MutableMap<String, String> {
        return this.biometricsUrls;
    }

    fun addBiometric(key: String, url: String) {
        this.biometricsUrls[key] = url
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