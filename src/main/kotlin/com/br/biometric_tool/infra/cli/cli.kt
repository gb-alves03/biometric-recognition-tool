package com.br.biometric_tool.infra.cli

import com.br.biometric_tool.domain.entity.Account
import com.br.biometric_tool.domain.vo.Name
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.UUID

fun main() {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val input = mutableMapOf<String, String>()
    var step = ""

    println("Digite 'signup' para iniciar o cadastro de uma conta ou 'exit' para sair")

    while (true) {
        val command = reader.readLine().trim()

        if (command == "signup") {
            println("Digite o primeiro nome:")
            step = "firstName"
            continue
        }

        when (step) {
            "firstName" -> {
                input["firstName"] = command
                println("Digite o sobrenome:")
                step = "lastName"
            }
            "lastName" -> {
                input["lastName"] = command
                println("Digite o email:")
                step = "email"
            }
            "email" -> {
                input["email"] = command
                println("Digite a senha:")
                step = "password"
            }
            "password" -> {
                input["password"] = command
                println("Digite a URL para biometria (ou pressione Enter para deixar vazio):")
                step = "biometricsUrl"
            }
            "biometricsUrl" -> {
                input["biometricsUrl"] = command
                // Criação do objeto Account com base nos dados fornecidos
                val account = Account(
                    accountId = UUID.randomUUID().toString(),
                    name = Name(input["firstName"]!!, input["lastName"]!!),
                    email = Email(input["email"]!!),
                    password = Password.create(input["password"]!!),
                    biometricsUrl = if (input["biometricsUrl"].isNullOrEmpty()) null else input["biometricsUrl"]
                )

                println("Cadastro concluído! Detalhes da conta:")
                println("ID: ${account.accountId}")
                println("Nome: ${account.getName()}")
                println("Email: ${account.getEmail()}")
                println("URL de biometria: ${account.biometricsUrl ?: "Nenhuma"}")

                // Resetando para um novo cadastro
                input.clear()
                step = ""
                println("\nDigite 'signup' para iniciar um novo cadastro ou 'exit' para sair")
            }
            else -> {
                if (command == "exit") {
                    println("Saindo da aplicação.")
                    break
                } else {
                    println("Comando inválido. Digite 'signup' para iniciar o cadastro ou 'exit' para sair")
                }
            }
        }
    }
}
