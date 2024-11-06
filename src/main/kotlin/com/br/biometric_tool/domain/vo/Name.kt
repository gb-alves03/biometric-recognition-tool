package com.br.biometric_tool.domain.vo

class Name(firstName: String, lastName: String) {
    private var firstName: String
    private var lastName: String

    init {
        val regex = Regex("^[A-Za-z]{2,50}$")
        if(!regex.matches(firstName)) throw IllegalArgumentException("O primeiro nome deve conter apenas letras e ter entre 2 e 50 caracteres.")
        if(!regex.matches(lastName)) throw IllegalArgumentException("O ultimo nome deve conter apenas letras e ter entre 2 e 50 caracteres.")
        this.firstName = firstName
        this.lastName = lastName
    }

    fun getFirstName(): String = this.firstName
    fun getLastName(): String = this.lastName
    fun getName(): String  = "$firstName $lastName"
}