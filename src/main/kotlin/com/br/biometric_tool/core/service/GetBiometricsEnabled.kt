package com.br.biometric_tool.core.service

import com.br.biometric_tool.core.dto.GetBiometricsEnabledInput
import com.br.biometric_tool.core.dto.GetBiometricsEnabledOutput
import com.br.biometric_tool.core.repository.AccountRepository

class GetBiometricsEnabled(private val accountRepository: AccountRepository) {
    fun execute(input: GetBiometricsEnabledInput): GetBiometricsEnabledOutput {
        val account = accountRepository.findByEmail(input.email) ?: throw Error()
        return GetBiometricsEnabledOutput(account.isBiometricsEnabled())
    }
}