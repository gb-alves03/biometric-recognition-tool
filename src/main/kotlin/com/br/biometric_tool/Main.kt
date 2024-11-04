package com.br.biometric_tool

import com.br.biometric_tool.impl.SiftAndFlannImpl
import org.opencv.core.Core

import java.io.File

fun main(args: Array<String>) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

    val siftAndFlann = SiftAndFlannImpl()

    val refImagePath = "src/main/resources/SOCOFing/Altered/1__M_Left_index_finger_CR.BMP"

    val authImagePaths =  File("src/main/resources/SOCOFing/Real").listFiles()
        ?.filter { it.isFile && isImageFile(it) }
        ?.map { it.absolutePath }
        ?.take(1000)
        ?: listOf()

    if (authImagePaths.isNotEmpty()) {
        val results = siftAndFlann.authenticate(refImagePath, authImagePaths)

        results.forEach {(path, authenticated) ->
            println("Image: $path - Authenticated: $authenticated")
        }
    } else {
        println("Authentication images not found")
    }
}

fun isImageFile(file: File): Boolean {
    val imageExtensions = listOf("jpg", "jpeg", "png", "bmp")

    return imageExtensions.any { file.name.endsWith(it, ignoreCase = true) }
}