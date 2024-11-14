package com.br.biometric_tool.infra.repository

import com.br.biometric_tool.core.domain.entity.Account
import com.br.biometric_tool.core.repository.AccountRepository
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class AccountRepositoryDatabase(private val connection: Connection) : AccountRepository {

    override fun save(account: Account) {
        var accountStatement: PreparedStatement? = null
        var urlStatement: PreparedStatement? = null
        try {
            accountStatement  = connection.prepareStatement("INSERT INTO accounts (account_id, first_name, last_name, email, password, biometric_enabled) VALUES (?, ?, ?, ?, ?, ?)")
            accountStatement.setString(1, account.accountId)
            accountStatement.setString(2, account.getFirstName())
            accountStatement.setString(3, account.getLastName())
            accountStatement.setString(4, account.getEmail())
            accountStatement.setString(5, account.getPassword())
            accountStatement.setBoolean(6, account.isBiometricsEnabled())

            val rowsInserted = accountStatement.executeUpdate()
            if (rowsInserted > 0) {
                println("Dados inseridos com sucesso!")
            }

            urlStatement = connection.prepareStatement(
                "INSERT INTO biometric_urls (account_id, url) VALUES (?, ?)"
            )

            for ((_, url) in account.getBiometrics()) {
                urlStatement.setString(1, account.accountId)
                urlStatement.setString(2, url)
                urlStatement.addBatch()
            }

            val urlInsertCounts = urlStatement.executeBatch()
            println("${urlInsertCounts.size} URLs de biometrias inseridas com sucesso.")
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            accountStatement?.close()
            urlStatement?.close()
        }
    }

    override fun findByEmail(email: String): Account? {
        val fingerNames = listOf("thumb", "index", "middle", "ring", "little")
        var accountStatement: PreparedStatement? = null
        var urlStatement: PreparedStatement? = null
        var resultSet: ResultSet? = null
        var urlResultSet: ResultSet? = null

        try {
            accountStatement = connection.prepareStatement(
                "SELECT account_id, first_name, last_name, email, password, biometric_enabled FROM accounts WHERE email = ?"
            )
            accountStatement.setString(1, email)
            resultSet = accountStatement.executeQuery()

            if (resultSet.next()) {
                val accountId = resultSet.getString("account_id")
                val firstName = resultSet.getString("first_name")
                val lastName = resultSet.getString("last_name")
                val password = resultSet.getString("password")
                val biometricEnabled = resultSet.getBoolean("biometric_enabled")

                val account = Account(accountId, firstName, lastName, email, password, biometricEnabled)

                urlStatement = connection.prepareStatement(
                    "SELECT url FROM biometric_urls WHERE account_id = ?"
                )
                urlStatement.setString(1, accountId)
                urlResultSet = urlStatement.executeQuery()

                var index = 0
                while (urlResultSet.next() && index < fingerNames.size) {
                    val url = urlResultSet.getString("url")
                    val finger = fingerNames[index]
                    account.addBiometric(finger, url)
                    index++
                }

                return account
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            resultSet?.close()
            urlResultSet?.close()
            accountStatement?.close()
            urlStatement?.close()
        }
        return null
    }
}
