package com.makor.hotornot.classifier.tensorflow

import android.graphics.Bitmap
import com.makor.hotornot.classifier.COLOR_CHANNELS
import com.makor.hotornot.classifier.Classifier
import com.makor.hotornot.classifier.Result
import org.tensorflow.contrib.android.TensorFlowInferenceInterface
import java.lang.Float
import java.util.*

private const val ENABLE_LOG_STATS = false

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
        classifyImageToOutputs()
        val outputQueue = getResults()
        return outputQueue.poll()
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

    private fun classifyImageToOutputs() {
        tensorFlowInference.feed(inputName, imageNormalizedPixels,
                1L, imageSize, imageSize, COLOR_CHANNELS.toLong())
        tensorFlowInference.run(arrayOf(outputName), ENABLE_LOG_STATS)
        tensorFlowInference.fetch(outputName, results)
    }

    private fun getResults(): PriorityQueue<Result> {
        val outputQueue = createOutputQueue()
        results.indices.mapTo(outputQueue) { Result(labels[it], results[it]) }
        return outputQueue
    }

    private fun createOutputQueue(): PriorityQueue<Result> {
        return PriorityQueue(
                2,
                Comparator { (_, rConfidence), (_, lConfidence) ->
                    Float.compare(lConfidence, rConfidence) })
    }
}