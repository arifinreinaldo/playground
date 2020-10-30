package com.explore.playground.easycamera

import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


const val CALL_SIMPLE_CAMERA = 21892

class SimpleCamera(val activity: FragmentActivity, val provider: String) {
    private var currentPhotoPath: String? = null
    fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(activity.applicationContext.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    ex.printStackTrace()
                    null
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    try {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            activity.applicationContext,
                            provider,
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        activity.startActivityForResult(takePictureIntent, CALL_SIMPLE_CAMERA)
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? =
            activity.applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    fun returnFile(requestCode: Int, resultCode: Int, data: Intent?): File? {
        if (requestCode == CALL_SIMPLE_CAMERA && resultCode == AppCompatActivity.RESULT_OK) {
            currentPhotoPath?.let {
                return if (it.isNotEmpty()) {
                    File(currentPhotoPath)
                } else {
                    Toast.makeText(
                        activity.applicationContext,
                        "Empty Photopath",
                        Toast.LENGTH_SHORT
                    ).show()
                    null
                }
            } ?: kotlin.run {
                Toast.makeText(
                    activity.applicationContext,
                    "Empty Photopath",
                    Toast.LENGTH_SHORT
                ).show()
                return null
            }
        } else {
            return null
        }
    }

    fun restorePath(path: String) {
        currentPhotoPath = path
    }

    fun getPath(): String {
        return currentPhotoPath ?: ""
    }
}