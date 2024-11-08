package com.br.biometric_tool.impl

import com.br.biometric_tool.infra.exceptions.DescriptorsExtractionFailedException
import com.br.biometric_tool.infra.exceptions.ImageNotLoadedException
import com.br.biometric_tool.infra.exceptions.ImageNotProcessedException
import org.opencv.core.DMatch
import org.opencv.core.Mat
import org.opencv.core.MatOfDMatch
import org.opencv.core.MatOfKeyPoint
import org.opencv.features2d.FlannBasedMatcher
import org.opencv.features2d.SIFT
import org.opencv.imgcodecs.Imgcodecs

class SiftAndFlannImpl {

    private val sift = SIFT.create()

    fun loadImage(path: String): Mat {
        val image = Imgcodecs.imread(path, Imgcodecs.IMREAD_GRAYSCALE)
        if (image.empty()) {
            throw ImageNotLoadedException("Error to load the image: $path")
        }
        return image
    }

    fun extractDescriptors(image: Mat): Pair<MatOfKeyPoint, Mat> {
        val keypoints = MatOfKeyPoint()
        val descriptors = Mat()
        sift.detectAndCompute(image, Mat(), keypoints, descriptors)

        if (descriptors.empty()) {
            throw DescriptorsExtractionFailedException("Fail to extract descriptors")
        }

        return Pair(keypoints, descriptors)
    }

    fun matchDescriptors(descriptorsRef: Mat, descriptorsAuth: Mat, threshold: Double = 0.7): List<DMatch> {
        val flannMatcher = FlannBasedMatcher.create()

        val matches = MatOfDMatch()
        flannMatcher.match(descriptorsRef, descriptorsAuth, matches)

        return matches.toArray()
            .filter { it.distance < threshold }
    }

    //Compara a authImagePath com cada umas das imagens cadastradas anteriormente pelo usuario.
    fun authenticate(refImagePath: String, authImagePath: MutableList<String>, matchThreshold: Int = 10): Boolean {
        for(url in authImagePath) {
            val refImage = loadImage((refImagePath))
            val (_, descriptorsRef) = extractDescriptors(refImage)

            try {
                val authImage = loadImage(url)
                val (_, descriptorsAuth) = extractDescriptors(authImage)
                val goodMatches = matchDescriptors(descriptorsRef, descriptorsAuth)
                //println("Image: $authImagePath - Good matches: ${goodMathces.size} - Authenticated: $isAuthenticated")
                return if(goodMatches.size >= matchThreshold) true else continue
            } catch (e: ImageNotProcessedException) {
                println("Error to process the image $authImagePath: ${e.message}")
                return false
            }
        }
        return false
    }
}

