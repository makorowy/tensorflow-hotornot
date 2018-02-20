package com.makor.hotornot

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import com.makor.hotornot.classifier.*
import com.makor.hotornot.classifier.tensorflow.ImageClassifierFactory
import kotlinx.android.synthetic.main.activity_main.*

private const val REQUEST_TAKE_PICTURE = 1
private const val EXTRAS_DATA = "data"

class MainActivity : AppCompatActivity() {

    private lateinit var classifier: Classifier

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
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PICTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val extras = data?.extras
        if (requestCode == REQUEST_TAKE_PICTURE && resultCode == Activity.RESULT_OK && extras != null) {
            imagePhoto.setImageBitmap(extras.get(EXTRAS_DATA) as Bitmap)
        }
    }
}
