package com.br.biometric_tool.service

import com.br.biometric_tool.domain.entity.Account
import com.br.biometric_tool.dto.SignupInput
import com.br.biometric_tool.dto.SignupOutput
import com.br.biometric_tool.infra.cli.accounts

class Signup(/* passa as dependecias */) {

    fun execute(input: SignupInput): SignupOutput {
        val account = Account(
            firstName = input.firstName,
            lastName = input.lastName,
            email = input.email,
            password = input.password,
            biometricsEnabled = input.biometricsEnabled,
        )

        input.fingerprintUrls.forEach { (finger, url) ->
            account.addBiometric(finger, url)
        }

        // Persistir no banco
        println("Account ID: ${account.accountId}")
        println("Name: ${account.getName()}")
        println("Email: ${account.getEmail()}")
        println("BiometricsEnabled: ${account.isBiometricsEnabled()}")

        return SignupOutput("Registration completed!")
    }
}