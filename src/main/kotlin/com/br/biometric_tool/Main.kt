package com.br.biometric_tool

import com.br.biometric_tool.impl.SiftAndFlannImpl
import org.opencv.core.Core

import java.io.File

fun main(args: Array<String>) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

    val siftAndFlann = SiftAndFlannImpl()

    val refImagePath = "src/main/resources/SOCOFing/Altered/1__M_Left_index_finger_CR.BMP"

    val authImagePath = "src/main/resources/SOCOFing/Real/2__F_Left_index_finger.BMP"

    if (authImagePath.isNotEmpty()) {
        val result = siftAndFlann.authenticate(refImagePath, authImagePath)
        println(result);
    } else {
        println("Authentication images not found")
    }
}

fun isImageFile(file: File): Boolean {
    val imageExtensions = listOf("jpg", "jpeg", "png", "bmp")

    return imageExtensions.any { file.name.endsWith(it, ignoreCase = true) }
}