package com.br.biometric_tool.infra.database

import com.br.biometric_tool.infra.exceptions.SchemaExecutionFailedException
import com.br.biometric_tool.infra.exceptions.SchemaFileNotFoundException
import java.io.File
import java.sql.Connection
import java.sql.Statement

fun executeSchemaSQL(connection: Connection) {

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