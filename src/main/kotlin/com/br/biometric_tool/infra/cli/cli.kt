package com.br.biometric_tool.infra.cli

import com.br.biometric_tool.domain.entity.Account
import java.io.BufferedReader
import java.io.InputStreamReader

val accounts = mutableMapOf<String, Account>()

fun main() {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    var step = ""
    var currentAccount: Account? = null

    println("Enter one of the options \n1. Sign up \n2. Log in \n3. Toggle Biometrics \n4. Exit")

    while (true) {
        val command = reader.readLine().trim()

        if (command == "1") {
            println("Enter your first name:")
            step = "firstName"
            val input = mutableMapOf<String, String>()
            val fingerprintUrls = mutableMapOf<String, String>()

            while (step.isNotEmpty()) {
                val inputCommand = reader.readLine().trim()
                when (step) {
                    "firstName" -> {
                        input["firstName"] = inputCommand
                        println("Enter your last name:")
                        step = "lastName"
                    }
                    "lastName" -> {
                        input["lastName"] = inputCommand
                        println("Enter your email:")
                        step = "email"
                    }
                    "email" -> {
                        input["email"] = inputCommand
                        println("Enter your password:")
                        step = "password"
                    }
                    "password" -> {
                        input["password"] = inputCommand
                        println("Do you want to activate biometrics? (Yes or No)")
                        step = "biometricsEnabled"
                    }
                    "biometricsEnabled" -> {
                        input["biometricsEnabled"] = inputCommand.lowercase()
                        if (input["biometricsEnabled"] == "yes") {
                            println("Please provide URLs for each finger:")
                            listOf("thumb", "index", "middle", "ring", "little").forEach { finger ->
                                println("Enter the URL for $finger:")
                                val url = reader.readLine().trim()
                                fingerprintUrls[finger] = url
                            }
                        }

                        val account = Account(
                            firstName = input["firstName"]!!,
                            lastName = input["lastName"]!!,
                            email = input["email"]!!,
                            password = input["password"]!!,
                            biometricsEnabled = input["biometricsEnabled"] == "yes",
                        )

                        fingerprintUrls.forEach { (finger, url) ->
                            account.addBiometric(finger, url)
                        }

                        accounts[account.getEmail()] = account
                        println("Registration completed! Account details:")
                        println("Account ID: ${account.accountId}")
                        println("Name: ${account.getName()}")
                        println("Email: ${account.getEmail()}")
                        println("BiometricsEnabled: ${account.isBiometricsEnabled()}")
                        step = ""
                    }
                }
            }

        } else if (command == "2") {
            println("Enter your email:")
            val email = reader.readLine().trim()

            //Verificação se existe no database
            if (!accounts.containsKey(email)) {
                println("Account not found.")
                continue
            }
            currentAccount = accounts[email]

            if (currentAccount!!.isBiometricsEnabled()) {
                var attempts = 0
                var loggedIn = false
                while (attempts < 3 && !loggedIn) {
                    println("Enter the biometrics URL (attempt ${attempts + 1} of 3):")
                    val biometricsUrl = reader.readLine().trim()
                    if (currentAccount.getBiometrics().containsValue(biometricsUrl)) {
                        println("Biometric login successful!")
                        loggedIn = true
                    } else {
                        attempts++
                        if (attempts < 3) {
                            println("Biometric URL incorrect. Try again.")
                        } else {
                            println("Biometric login failed after 3 attempts.")
                        }
                    }
                }
            } else {
                println("Enter your password:")
                val password = reader.readLine().trim()
                if (currentAccount.passwordMatches(password)) {
                    println("Password login successful!")
                } else {
                    println("Incorrect password.")
                }
            }

        } else if (command == "3") {
            if (currentAccount != null) {
                if (!currentAccount.isBiometricsEnabled()) {
                    println("Activating biometrics. Please provide URLs for each finger:")
                    val fingerprintUrls = currentAccount.getBiometrics()
                    listOf("thumb", "index", "middle", "ring", "little").forEach { finger ->
                        if (!fingerprintUrls.containsKey(finger)) {
                            println("Enter the URL for $finger:")
                            val url = reader.readLine().trim()
                            fingerprintUrls[finger] = url
                        }
                    }
                    currentAccount.biometricsEnabled()
                } else {
                    currentAccount.biometricsDisabled()
                }
                println("Biometrics status updated: ${currentAccount.isBiometricsEnabled()}")
            } else {
                println("No account is currently logged in.")
            }

        } else if (command == "4") {
            println("Exiting the application.")
            break
        } else {
            println("Invalid option. Please select 1, 2, 3, or 4.")
        }

        println("\nEnter one of the options \n1. Sign up \n2. Log in \n3. Toggle Biometrics \n4. Exit")
    }
}
