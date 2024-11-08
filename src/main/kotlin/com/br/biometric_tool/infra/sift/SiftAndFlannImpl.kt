package com.br.biometric_tool.infra.sift

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

    private fun loadImage(path: String): Mat {
        val image = Imgcodecs.imread(path, Imgcodecs.IMREAD_GRAYSCALE)
        if (image.empty()) {
            throw ImageNotLoadedException("Error to load the image: $path")
        }
        return image
    }

    private fun extractDescriptors(image: Mat): Pair<MatOfKeyPoint, Mat> {
        val keypoints = MatOfKeyPoint()
        val descriptors = Mat()
        sift.detectAndCompute(image, Mat(), keypoints, descriptors)

        if (descriptors.empty()) {
            throw DescriptorsExtractionFailedException("Fail to extract descriptors")
        }

        return Pair(keypoints, descriptors)
    }

    private fun matchDescriptors(descriptorsRef: Mat, descriptorsAuth: Mat, threshold: Double = 0.7): List<DMatch> {
        val flannMatcher = FlannBasedMatcher.create()

        val matches = MatOfDMatch()
        flannMatcher.match(descriptorsRef, descriptorsAuth, matches)

        return matches.toArray()
            .filter { it.distance < threshold }
    }

    fun authenticate(refImagePath: String, authImagePaths: Map<String, String>, matchThreshold: Int = 10): Boolean {
        for ((_, path) in authImagePaths) {
            val refImage = loadImage(refImagePath)
            val (_, descriptorsRef) = extractDescriptors(refImage)

            try {
                val authImage = loadImage(path)
                val (_, descriptorsAuth) = extractDescriptors(authImage)
                val goodMatches = matchDescriptors(descriptorsRef, descriptorsAuth)

                if (goodMatches.size >= matchThreshold) {
                    return true
                }
            } catch (e: ImageNotProcessedException) {
                println("Error to process the image $path: ${e.message}")
                continue
            }
        }
        return false
    }
}

