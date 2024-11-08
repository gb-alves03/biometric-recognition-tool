package com.br.biometric_tool.infra.repository

import com.br.biometric_tool.core.domain.entity.Account
import com.br.biometric_tool.core.repository.AccountRepository

class AccountRepositoryMemory : AccountRepository{
    private val accounts = mutableListOf<Account>()

    override fun save(account: Account) {
        accounts.add(account)
    }

    override fun findByEmail(email: String): Account? {
        return accounts.find { it.getEmail() == email }
    }
}