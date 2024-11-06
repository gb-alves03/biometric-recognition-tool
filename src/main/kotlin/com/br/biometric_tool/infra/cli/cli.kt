package com.br.biometric_tool.infra.cli

import com.br.biometric_tool.domain.entity.Account
import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val input = mutableMapOf<String, String>()
    var step = ""

    println("Enter one of the options \n1. Sign up \n2. Log in \n3. Exit")

    while (true) {
        val command = reader.readLine().trim()

        if (command.lowercase() == "sign up") {
            println("Enter your first name:")
            step = "firstName"
            continue
        }

        when (step) {
            "firstName" -> {
                input["firstName"] = command
                println("Enter your last name:")
                step = "lastName"
            }
            "lastName" -> {
                input["lastName"] = command
                println("Enter your email:")
                step = "email"
            }
            "email" -> {
                input["email"] = command
                println("Enter your password:")
                step = "password"
            }
            "password" -> {
                input["password"] = command
                println(input["password"]!!)
                println("Do you want to activate biometrics? (Yes or No)")
                step = "biometricsEnabled"
            }
            "biometricsEnabled" -> {
                input["biometricsEnabled"] = command.lowercase()
                println("Type in the URL for biometrics (or press Enter to leave it empty):")
                step = "biometricsUrl"
            }

            "biometricsUrl" -> {
                input["biometricsUrl"] = command
                val account = Account(
                    firstName = input["firstName"]!!,
                    lastName = input["lastName"]!!,
                    email = input["email"]!!,
                    password = input["password"]!!,
                    biometricsEnabled = input["biometricsEnabled"] == "yes"
                )

                println("Registration completed! Account details:")
                println("Account ID: ${account.accountId}")
                println("Name: ${account.getName()}")
                println("Email: ${account.getEmail()}")
                println("BiometricsEnabled: ${account.isBiometricsEnabled()}")

                input.clear()
                step = ""
                println("\nEnter one of the options \n" +
                        "1. Sign up \n" +
                        "2. Log in \n" +
                        "3. Exit")
            }
            else -> {
                if (command.lowercase() == "exit") {
                    println("Exiting the application.")
                    break
                } else {
                    println("\nEnter one of the options \n" +
                            "1. Sign up \n" +
                            "2. Log in \n" +
                            "3. Exit")
                }
            }
        }
    }
}
