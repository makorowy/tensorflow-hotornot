package com.makor.hotornot.utils

import android.content.res.AssetManager
import com.makor.hotornot.classifier.ASSETS_PATH
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun getLabels(assetManager: AssetManager, labelFilePath: String): List<String> {
    val actualFilename = getLabelsFileName(labelFilePath)
    return getLabelsFromFile(assetManager, actualFilename)
}

private fun getLabelsFromFile(assetManager: AssetManager, actualFilename: String): ArrayList<String> {
    val labels = ArrayList<String>()
    BufferedReader(InputStreamReader(assetManager.open(actualFilename))).use {
        var line: String? = it.readLine()
        while (line != null) {
            labels.add(line)
            line = it.readLine()
        }
        it.close()
    }
    return labels
}

private fun getLabelsFileName(labelFilenamePath: String): String {
    return labelFilenamePath.split(ASSETS_PATH.toRegex())
            .dropLastWhile { it.isEmpty() }
            .toTypedArray()[1]
}