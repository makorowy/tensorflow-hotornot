package com.makor.hotornot.uri

import android.content.Context
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import java.io.File


object UriHelper {

    fun getUriFromFilePath(context: Context, filePath: String): Uri {
        val file = File(filePath)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(
                    context,
                    context.applicationContext.packageName + ".uri",
                    file)
        } else {
            Uri.fromFile(file)
        }
    }
}