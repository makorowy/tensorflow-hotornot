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
        return Result("", 0.0f)
    }
}