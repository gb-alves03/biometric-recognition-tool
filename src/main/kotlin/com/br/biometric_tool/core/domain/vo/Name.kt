package com.br.biometric_tool.core.domain.vo

import com.br.biometric_tool.infra.exceptions.InvalidStatus

class Name(firstName: String, lastName: String) {
    private var firstName: String
    private var lastName: String

    init {
        if(!firstName.matches(Regex("^[A-Za-z]{2,50}$"))) throw InvalidStatus("Invalid first name.")
        if(!lastName.matches(Regex("^[A-Za-z]{2,50}$"))) throw InvalidStatus("Invalid last name.")
        this.firstName = firstName
        this.lastName = lastName
    }

    fun getName(): String {
        return "$firstName $lastName"
    }
}