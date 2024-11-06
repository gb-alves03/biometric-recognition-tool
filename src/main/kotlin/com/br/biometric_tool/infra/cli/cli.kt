package com.br.biometric_tool.infra.cli

import com.br.biometric_tool.domain.entity.Account
import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val input = mutableMapOf<String, String>()
    var step = ""

    println("Esolha uma das opções \n1. Cadastrar \n2. Logar \n3. Sair")

    while (true) {
        val command = reader.readLine().trim()

        if (command.lowercase() == "cadastrar") {
            println("Digite seu primeiro nome:")
            step = "firstName"
            continue
        }

        when (step) {
            "firstName" -> {
                input["firstName"] = command
                println("Digite seu sobrenome:")
                step = "lastName"
            }
            "lastName" -> {
                input["lastName"] = command
                println("Digite seu email:")
                step = "email"
            }
            "email" -> {
                input["email"] = command
                println("Digite sua senha:")
                step = "password"
            }
            "password" -> {
                input["password"] = command
                println(input["password"]!!)
                println("Deseja ativar a biometria ? (Sim ou Não)")
                step = "biometricsEnabled"
            }
            "biometricsEnabled" -> {
                input["biometricsEnabled"] = command.lowercase()
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
                    biometricsEnabled = input["biometricsEnabled"] == "sim",
                    biometricsUrl = input["biometricsUrl"]!!
                )

                println("Cadastro concluído! Detalhes da conta:")
                println("ID: ${account.accountId}")
                println("Nome: ${account.getName()}")
                println("Email: ${account.getEmail()}")
                println("BiometricsEnabled: ${account.isBiometricsEnabled()}")
                println("URL de biometria: ${account.biometricsUrl ?: "Nenhuma"}")

                input.clear()
                step = ""
                println("\nDigite uma das opções \n" +
                        "1. Cadastrar \n" +
                        "2. Logar \n" +
                        "3. Sair")
            }
            else -> {
                if (command.lowercase() == "sair") {
                    println("Saindo da aplicação.")
                    break
                } else {
                    println("\nDigite uma das opções \n" +
                            "1. Cadastrar \n" +
                            "2. Logar \n" +
                            "3. Sair")
                }
            }
        }
    }
}
