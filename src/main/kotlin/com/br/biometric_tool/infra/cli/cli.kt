package com.br.biometric_tool.infra.cli

import com.br.biometric_tool.domain.entity.Account
import java.io.BufferedReader
import java.io.InputStreamReader

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
                val account = Account(
                    firstName = input["firstName"]!!,
                    lastName = input["lastName"]!!,
                    email = input["email"]!!,
                    password = input["password"]!!,
                    biometricsUrl = input["biometricsUrl"]!!
                )

                println("Cadastro concluído! Detalhes da conta:")
                println("ID: ${account.accountId}")
                println("Nome: ${account.getName()}")
                println("Email: ${account.getEmail()}")
                println("URL de biometria: ${account.biometricsUrl ?: "Nenhuma"}")

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
