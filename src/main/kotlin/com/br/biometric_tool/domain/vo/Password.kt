package com.br.biometric_tool.domain.vo

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class Password private constructor(private val password: String) {

    companion object {
        private fun hashPassword(password: String): String {
            return try {
                val digest = MessageDigest.getInstance("SHA-256")
                val hash = digest.digest(password.toByteArray())
                val hexString = StringBuilder(2 * hash.size)
                for (b in hash) {
                    hexString.append(String.format("%02x", b))
                }
                hexString.toString()
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException("SHA-256 algorithm not found", e)
            }
        }

        fun create(password: String): Password {
            if (!password.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,12}$"))) {
                throw IllegalArgumentException("Invalid password")
            }
            val encryptedPassword = hashPassword(password)
            return Password(encryptedPassword)
        }

        fun restore(password: String): Password {
            return Password(password)
        }
    }

    fun passwordMatches(password: String): Boolean {
        return this.password == hashPassword(password)
    }

    override fun toString(): String {
        return this.password
    }
}
