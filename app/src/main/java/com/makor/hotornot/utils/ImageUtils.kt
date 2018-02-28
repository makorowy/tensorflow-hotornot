package com.makor.hotornot.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import com.makor.hotornot.classifier.IMAGE_SIZE


fun getCroppedBitmap(bitmap: Bitmap): Bitmap {
    val croppedBitmap = Bitmap.createBitmap(IMAGE_SIZE, IMAGE_SIZE, Bitmap.Config.ARGB_8888)
    val transformationMatrix = getPhotoBitmapTransformationMatrix(bitmap)
    val canvas = Canvas(croppedBitmap)
    canvas.drawBitmap(bitmap, transformationMatrix, null)
    return croppedBitmap
}

private fun getPhotoBitmapTransformationMatrix(bitmap: Bitmap): Matrix {
    val frameToCropTransformationMatrix = getTransformationMatrix(
            bitmap.width, bitmap.height,
            IMAGE_SIZE, IMAGE_SIZE,
            0, true)

    val cropToFrameTransformationMatrix = Matrix()
    frameToCropTransformationMatrix.invert(cropToFrameTransformationMatrix)
    return frameToCropTransformationMatrix
}

private fun getTransformationMatrix(
        srcWidth: Int,
        srcHeight: Int,
        dstWidth: Int,
        dstHeight: Int,
        applyRotation: Int,
        maintainAspectRatio: Boolean): Matrix {
    val matrix = Matrix()

    // Translate so center of image is at origin.
    matrix.postTranslate(-srcWidth / 2.0f, -srcHeight / 2.0f)

    // Rotate around origin.
    matrix.postRotate(applyRotation.toFloat())

    // Account for the already applied rotation, if any, and then determine how
    // much scaling is needed for each axis.
    val transpose = (Math.abs(applyRotation) + 90) % 180 == 0

    val inWidth = if (transpose) srcHeight else srcWidth
    val inHeight = if (transpose) srcWidth else srcHeight

    // Apply scaling if necessary.
    if (inWidth != dstWidth || inHeight != dstHeight) {
        val scaleFactorX = dstWidth / inWidth.toFloat()
        val scaleFactorY = dstHeight / inHeight.toFloat()

        if (maintainAspectRatio) { // Scale by minimum factor so that dst is filled completely while
            // maintaining the aspect ratio. Some image may fall off the edge.
            val scaleFactor = Math.max(scaleFactorX, scaleFactorY)
            matrix.postScale(scaleFactor, scaleFactor)
        } else { // Scale exactly to fill dst from src.
            matrix.postScale(scaleFactorX, scaleFactorY)
        }
    }

    // Translate back from origin centered reference to destination frame.
    matrix.postTranslate(dstWidth / 2.0f, dstHeight / 2.0f)

    return matrix
}