package com.br.biometric_tool.infra.database

import com.br.biometric_tool.infra.exceptions.DatabaseNotConnectedException
import java.io.FileInputStream
import java.sql.Connection
import java.sql.DriverManager
import java.util.Properties

fun connectToDatabase() {
    val properties = Properties()

    val propertiesPath = "src/main/resources/application.properties"
    FileInputStream(propertiesPath).use { input ->
        properties.load(input)
    }

    val url = properties.getProperty("database.url")
    val username = properties.getProperty("database.username")
    val password = properties.getProperty("database.password")
    val driver = properties.getProperty("database.driver")

    Class.forName(driver)

    try {
        val connection: Connection = DriverManager.getConnection(url, username, password)
        println("Database connected")

        executeSchemaSQL(connection)

        connection.close()
    } catch (e : Exception) {
        throw DatabaseNotConnectedException("Database connection failed: ${e.message}")
    }
}