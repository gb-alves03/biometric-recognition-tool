package com.br.biometric_tool

import com.br.biometric_tool.core.dto.ChangeBiometricStatusInput
import com.br.biometric_tool.core.dto.GetBiometricsEnabledInput
import com.br.biometric_tool.core.dto.LoginInput
import com.br.biometric_tool.core.dto.SignupInput
import com.br.biometric_tool.core.service.ChangeBiometricStatus
import com.br.biometric_tool.core.service.GetBiometricsEnabled
import com.br.biometric_tool.core.service.Login
import com.br.biometric_tool.core.service.Signup
import com.br.biometric_tool.infra.database.DatabaseManager
import com.br.biometric_tool.infra.exceptions.InvalidStatusException
import com.br.biometric_tool.infra.exceptions.LoginFailedException
import com.br.biometric_tool.infra.exceptions.TogglingBiometricsException
import com.br.biometric_tool.infra.exceptions.UnexpectedException
import com.br.biometric_tool.infra.repository.AccountRepositoryDatabase
import org.opencv.core.Core
import java.io.BufferedReader
import java.io.InputStreamReader

fun main(args: Array<String>) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    val connection = DatabaseManager().getConnection()
    val accountRepository = AccountRepositoryDatabase(connection)
    val getBiometricsEnabled = GetBiometricsEnabled(accountRepository)
    val login = Login(accountRepository)
    val signup = Signup(accountRepository)
    val changeBiometricStatus = ChangeBiometricStatus(accountRepository)

    val reader = BufferedReader(InputStreamReader(System.`in`))
    var step = ""
    var emailLogged: String? = null

    println("Enter one of the options \n1. Sign up \n2. Log in \n3. Toggle Biometrics \n4. Exit")

    while (true) {
        try {
            val command = reader.readLine().trim()

            when (command) {
                "1" -> {
                    println("Enter your first name:")
                    step = "firstName"
                    val input = mutableMapOf<String, String>()
                    val fingerprintUrls = mutableMapOf<String, String>()

                    try {
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
                                            fingerprintUrls[finger] = reader.readLine().trim()
                                        }
                                    }
                                    step = ""
                                }
                            }
                        }
                        val response = signup.execute(
                            SignupInput(
                                input["firstName"]!!,
                                input["lastName"]!!,
                                input["email"]!!,
                                input["password"]!!,
                                input["biometricsEnabled"] == "yes",
                                fingerprintUrls
                            )
                        )
                        println(response.message)
                    } catch (e: NullPointerException) {
                        println("Missed required field")
                    } catch (e: InvalidStatusException) {
                        println("Invalid input. ${e.message}")
                    } catch (e: UnexpectedException) {
                        println("An unexpected error occurred during Sign Up: ${e.message}")
                    }
                }

                "2" -> {
                    println("Enter your email:")
                    val email = reader.readLine().trim()

                    try {
                        val isBiometricsEnabled = getBiometricsEnabled.execute(GetBiometricsEnabledInput(email))

                        if (isBiometricsEnabled.status) {
                            var attempts = 0
                            var loggedIn = false
                            while (attempts < 3 && !loggedIn) {
                                println("Enter the biometrics URL (attempt ${attempts + 1} of 3):")
                                val biometricsUrl = reader.readLine().trim()
                                val result = login.execute(LoginInput(email, null, biometricsUrl))
                                if (result.status) {
                                    emailLogged = result.email
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
                            val result = login.execute(LoginInput(email, password))
                            if (result.status) {
                                emailLogged = result.email
                                println("Password login successful!")
                            } else {
                                println("Incorrect password.")
                            }
                        }
                    } catch (e: LoginFailedException) {
                        println("Login failed: ${e.message}")
                    }
                }

                "3" -> {
                    try {
                        if (emailLogged != null) {
                            val response = changeBiometricStatus.execute(ChangeBiometricStatusInput(emailLogged))
                            println(response.status)
                        } else {
                            println("No account is currently logged in.")
                        }
                    } catch (e: TogglingBiometricsException) {
                        println("An error occurred while toggling biometrics: ${e.message}")
                    }
                }

                "4" -> {
                    println("Exiting the application.")
                    break
                }

                else -> {
                    println("Invalid option. Please select 1, 2, 3, or 4.")
                }
            }
        } catch (e: InvalidStatusException) {
            println("Error reading input: ${e.message}")
        } catch (e: UnexpectedException) {
            println("An unexpected error occurred: ${e.message}")
        }
        println("\nEnter one of the options \n1. Sign up \n2. Log in \n3. Toggle Biometrics \n4. Exit")
    }
}