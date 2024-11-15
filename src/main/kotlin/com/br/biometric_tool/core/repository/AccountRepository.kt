package com.br.biometric_tool.core.repository

import com.br.biometric_tool.core.domain.entity.Account

interface AccountRepository {
    fun save(account: Account)
    fun put(account: Account)
    fun findByEmail(email: String): Account?
}