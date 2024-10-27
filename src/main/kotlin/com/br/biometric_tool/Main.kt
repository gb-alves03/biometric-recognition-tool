package com.br.biometric_tool

import com.br.biometric_tool.infra.exceptions.ValueNotAttributedException
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.MatOfKeyPoint
import org.opencv.features2d.SIFT
import org.opencv.imgcodecs.Imgcodecs
import java.io.File


class Main {

    fun main() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

        val refImagePath = "src/main/resources/SOCOFing/Altered/1__M_Left_index_finger_CR.BMP"
        val refImage = Imgcodecs.imread(refImagePath)

        if (refImage.empty()) {
            throw ValueNotAttributedException("Error to record the reference image")
        }

        val authImagePaths =  File("src/main/resources/SOCOFing/Real").listFiles()?.map { it.path }?.take(1000) ?: listOf()
        if (authImagePaths.isEmpty()) {
            throw ValueNotAttributedException("Authentication images not found")
        }

        val sift = SIFT.create()

        val keypointsRef = MatOfKeyPoint()
        val descriptorsAuth = Mat()
        sift.detectAndCompute(refImage, Mat(), keypointsRef, descriptorsAuth)

        if (descriptorsAuth.empty()) {
            throw ValueNotAttributedException("It was not possible to extract descriptors from the reference image")
        }


        // Inicializar a parte do FLANN


    }
}