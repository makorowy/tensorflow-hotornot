package com.makor.hotornot.classifier.tensorflow

import android.content.res.AssetManager
import com.makor.hotornot.classifier.Classifier
import com.makor.hotornot.utils.FileUtils.getLabels
import org.tensorflow.contrib.android.TensorFlowInferenceInterface

object ImageClassifierFactory {

    fun create(
            assetManager: AssetManager,
            graphFilePath: String,
            labelsFilePath: String,
            imageSize: Int,
            inputName: String,
            outputName: String
    ): Classifier {

        val labels = getLabels(assetManager, labelsFilePath)

        return ImageClassifier(
                inputName,
                outputName,
                imageSize.toLong(),
                labels,
                IntArray(imageSize * imageSize),
                FloatArray(imageSize * imageSize * 3),
                FloatArray(labels.size),
                TensorFlowInferenceInterface(assetManager, graphFilePath)
        )
    }
}