package com.br.biometric_tool.core.service

import com.br.biometric_tool.core.dto.GetBiometricsEnabledInput
import com.br.biometric_tool.core.dto.GetBiometricsEnabledOutput
import com.br.biometric_tool.core.repository.AccountRepository
import com.br.biometric_tool.infra.exceptions.NotFoundException

class GetBiometricsEnabled(private val accountRepository: AccountRepository) {
    fun execute(input: GetBiometricsEnabledInput): GetBiometricsEnabledOutput {
        val account = accountRepository.findByEmail(input.email) ?: throw NotFoundException("Account not found for email: ${input.email}")
        return GetBiometricsEnabledOutput(account.isBiometricsEnabled())
    }
}