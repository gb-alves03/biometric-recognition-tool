package com.br.biometric_tool.core.service

import com.br.biometric_tool.core.dto.LoginInput
import com.br.biometric_tool.core.dto.LoginOutput
import com.br.biometric_tool.infra.sift.SiftAndFlannImpl
import com.br.biometric_tool.core.repository.AccountRepository

class Login(private val accountRepository: AccountRepository) {
    fun execute(input: LoginInput): LoginOutput {
        val account = accountRepository.findByEmail(input.email) ?: throw IllegalArgumentException("Account not found for email: ${input.email}")
        var result = false
        if(input.biometricsUrl != null) {
            result = SiftAndFlannImpl().authenticate(input.biometricsUrl, account.getBiometrics())
        } else {
            if(input.password != null) {
                result = account.passwordMatches(input.password)
            }
        }
        return LoginOutput(result, account.accountId)
    }
}