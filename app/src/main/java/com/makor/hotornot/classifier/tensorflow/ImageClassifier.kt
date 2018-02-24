package com.makor.hotornot.classifier.tensorflow

import android.graphics.Bitmap
import com.makor.hotornot.classifier.Classifier
import com.makor.hotornot.classifier.Result
import org.tensorflow.contrib.android.TensorFlowInferenceInterface

class ImageClassifier (
        private val inputName: String,
        private val outputName: String,
        private val imageSize: Long,
        private val labels: List<String>,
        private val imageBitmapPixels: IntArray,
        private val imageNormalizedPixels: FloatArray,
        private val results: FloatArray,
        private val tensorFlowInference: TensorFlowInferenceInterface
) : Classifier {

    override fun recognizeImage(bitmap: Bitmap): Result {
        preprocessImageToNormalizedFloats(bitmap)
        return Result("", 0.0f)
    }

    private fun preprocessImageToNormalizedFloats(bitmap: Bitmap) {
        // Preprocess the image data from 0-255 int to normalized float based
        // on the provided parameters.
        val imageMean = 128
        val imageStd = 128.0f
        bitmap.getPixels(imageBitmapPixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        for (i in imageBitmapPixels.indices) {
            val `val` = imageBitmapPixels[i]
            imageNormalizedPixels[i * 3 + 0] = ((`val` shr 16 and 0xFF) - imageMean) / imageStd
            imageNormalizedPixels[i * 3 + 1] = ((`val` shr 8 and 0xFF) - imageMean) / imageStd
            imageNormalizedPixels[i * 3 + 2] = ((`val` and 0xFF) - imageMean) / imageStd
        }
    }
}