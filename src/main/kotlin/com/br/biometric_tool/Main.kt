package com.br.biometric_tool

import com.br.biometric_tool.impl.SiftAndFlannImpl
import com.br.biometric_tool.infra.database.connectToDatabase
import org.opencv.core.Core

import java.io.File

fun main(args: Array<String>) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

    val siftAndFlann = SiftAndFlannImpl()

    val refImagePath = "src/main/resources/SOCOFing/Real/1__M_Left_thumb_finger.BMP"

    val authImagePathList = mutableListOf("src/main/resources/SOCOFing/Real/1__M_Left_index_finger.BMP",
        "src/main/resources/SOCOFing/Real/1__M_Left_little_finger.BMP",
        "src/main/resources/SOCOFing/Real/1__M_Left_middle_finger.BMP",
        "src/main/resources/SOCOFing/Real/1__M_Left_ring_finger.BMP",
        "src/main/resources/SOCOFing/Real/1__M_Left_thumb_finger.BMP"
        )

    if (authImagePathList.isNotEmpty()) {
        val result = siftAndFlann.authenticate(refImagePath, authImagePathList)
        println(result);
    } else {
        println("Authentication images not found")
    }

    connectToDatabase()
}

fun isImageFile(file: File): Boolean {
    val imageExtensions = listOf("jpg", "jpeg", "png", "bmp")

    return imageExtensions.any { file.name.endsWith(it, ignoreCase = true) }
}