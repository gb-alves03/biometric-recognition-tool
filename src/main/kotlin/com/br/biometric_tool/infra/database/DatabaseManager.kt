package com.br.biometric_tool.infra.database

import com.br.biometric_tool.infra.exceptions.DatabaseNotConnectedException
import com.br.biometric_tool.infra.exceptions.SchemaExecutionFailedException
import com.br.biometric_tool.infra.exceptions.SchemaFileNotFoundException
import java.io.File
import java.io.FileInputStream
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import java.util.*

class DatabaseManager {

    private val url: String
    private val username: String
    private val password: String
    private val driver: String
    private lateinit var connection: Connection

    init {
        val properties = Properties()
        val propertiesPath = "src/main/resources/application.properties"
        FileInputStream(propertiesPath).use { input ->
            properties.load(input)
        }

        url = properties.getProperty("database.url")
        username = properties.getProperty("database.username")
        password = properties.getProperty("database.password")
        driver = properties.getProperty("database.driver")

        initializeConnection()
    }

    private fun initializeConnection() {
        try {
            Class.forName(driver)

            connection = DriverManager.getConnection(url, username, password)
            println("Database connected")

            executeSchemaSQL(connection)
        } catch (e : RuntimeException) {
            throw DatabaseNotConnectedException("Database connection failed: ${e.message}")
        }
    }


    fun getConnection() : Connection {
        if (!::connection.isInitialized || connection.isClosed) {
            initializeConnection()
        }

        return connection
    }

    private fun executeSchemaSQL(connection: Connection) {

        val schemaFilePath = "src/main/resources/schema.sql"
        val schemaFile = File(schemaFilePath)

        if (schemaFile.exists()) {
            val sql = schemaFile.readText()

            val statement: Statement = connection.createStatement()

            try {
                statement.executeUpdate(sql)
                println("Tables created with success")
            } catch (e : Exception) {
                throw SchemaExecutionFailedException("Error to execute the schema: ${e.message}")
            } finally {
                statement.close()
            }
        } else {
            throw SchemaFileNotFoundException("schema.sql file not found")
        }
    }
}