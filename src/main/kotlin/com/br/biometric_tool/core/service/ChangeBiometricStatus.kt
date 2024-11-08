package com.br.biometric_tool.core.service

import com.br.biometric_tool.core.dto.ChangeBiometricStatusInput
import com.br.biometric_tool.core.dto.ChangeBiometricStatusOutput
import com.br.biometric_tool.core.repository.AccountRepository
import java.io.BufferedReader
import java.io.InputStreamReader

class ChangeBiometricStatus(private val accountRepository: AccountRepository) {

    fun execute(input: ChangeBiometricStatusInput): ChangeBiometricStatusOutput {
        val reader = BufferedReader(InputStreamReader(System.`in`))
        val account = accountRepository.findByEmail(input.email) ?: throw IllegalArgumentException("Account not found for email: ${input.email}")
        if(!account.isBiometricsEnabled()) {
            println("Activating biometrics. Please provide URLs for each finger:")
            val fingerprintUrls = account.getBiometrics()
            listOf("thumb", "index", "middle", "ring", "little").forEach { finger ->
                if (!fingerprintUrls.containsKey(finger)) {
                    println("Enter the URL for $finger:")
                    val url = reader.readLine().trim()
                    fingerprintUrls[finger] = url
                }
            }
            account.biometricsEnabled()
            return ChangeBiometricStatusOutput("Biometrics status updated: ${account.isBiometricsEnabled()}")
        } else {
            account.biometricsDisabled()
            return ChangeBiometricStatusOutput("Biometrics status updated: ${account.isBiometricsEnabled()}")
        }
    }
}