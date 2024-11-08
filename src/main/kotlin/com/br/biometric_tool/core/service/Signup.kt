package com.br.biometric_tool.core.service

import com.br.biometric_tool.core.domain.entity.Account
import com.br.biometric_tool.core.dto.SignupInput
import com.br.biometric_tool.core.dto.SignupOutput
import com.br.biometric_tool.core.repository.AccountRepository

class Signup(private val accountRepository: AccountRepository) {

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

        accountRepository.save(account)
        println("Account ID: ${account.accountId}")
        println("Name: ${account.getName()}")
        println("Email: ${account.getEmail()}")
        println("BiometricsEnabled: ${account.isBiometricsEnabled()}")

        return SignupOutput("Registration completed!")
    }
}