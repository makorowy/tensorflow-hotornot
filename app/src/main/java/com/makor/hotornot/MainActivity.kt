package com.makor.hotornot

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import com.makor.hotornot.classifier.*
import com.makor.hotornot.classifier.tensorflow.ImageClassifierFactory
import com.makor.hotornot.uri.UriRetriever.getUriFromFilePath
import com.makor.hotornot.utils.ImageUtils.getCroppedBitmap
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

private const val REQUEST_TAKE_PICTURE = 1

class MainActivity : AppCompatActivity() {

    private lateinit var classifier: Classifier
    private var currentPhotoUri: Uri = Uri.EMPTY
    private var filePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createClassifier()
        takePicture()
    }

    private fun createClassifier() {
        classifier = ImageClassifierFactory.create(
                assets,
                GRAPH_FILE_PATH,
                LABELS_FILE_PATH,
                IMAGE_SIZE,
                GRAPH_INPUT_NAME,
                GRAPH_OUTPUT_NAME
        )
    }

    private fun takePicture() {
        filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath + "/${System.currentTimeMillis()}.jpg"
        currentPhotoUri = getUriFromFilePath(this, filePath)

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri)
        takePictureIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PICTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val file = File(filePath)
        if (requestCode == REQUEST_TAKE_PICTURE) {
            if (file.exists()) {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                val croppedBitmap = getCroppedBitmap(bitmap)
                imagePhoto.setImageBitmap(croppedBitmap)
            }
        }
    }
}
