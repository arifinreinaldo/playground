package com.explore.playground.easycamera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


const val CALL_SIMPLE_CAMERA = 21892
const val PREF_SIMPLE_CAMERA = "SIMPLE_ABSOLUTE_PATH_21892"
const val PATH_SIMPLE_CAMERA = "PATH_ABSOLUTE_PATH_21892"

class SimpleCamera {
    private var call: ActivityCaller
    private var fileProvider: String
    private var context: Context
    private val sharedPreferences: SharedPreferences

    constructor(activity: Activity, provider: String) {
        this.call = ActivityCaller(activity = activity)
        this.fileProvider = provider
        this.context = call.context
        sharedPreferences = context.getSharedPreferences(PREF_SIMPLE_CAMERA, Context.MODE_PRIVATE)
    }

    constructor(fragment: Fragment, provider: String) {
        this.call = ActivityCaller(fragment = fragment)
        this.fileProvider = provider
        this.context = call.context
        sharedPreferences = context.getSharedPreferences(PREF_SIMPLE_CAMERA, Context.MODE_PRIVATE)
    }

    constructor(fragment: android.app.Fragment, provider: String) {
        this.call = ActivityCaller(deprecatedFragment = fragment)
        this.fileProvider = provider
        this.context = call.context
        sharedPreferences = context.getSharedPreferences(PREF_SIMPLE_CAMERA, Context.MODE_PRIVATE)
    }

    private class ActivityCaller(
        val fragment: Fragment? = null,
        val activity: Activity? = null,
        val deprecatedFragment: android.app.Fragment? = null
    ) {
        val context: Context
            get() = (activity ?: fragment?.activity ?: deprecatedFragment?.activity)!!

        fun startActivityForResult(intent: Intent, chooser: Int) {
            activity?.startActivityForResult(intent, chooser)
                ?: fragment?.startActivityForResult(intent, chooser)
                ?: deprecatedFragment?.startActivityForResult(intent, chooser)
        }
    }

    private var currentPhotoPath: String? = null
    fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(context.packageManager)?.also {
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
                            context,
                            fileProvider,
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        call?.startActivityForResult(takePictureIntent, CALL_SIMPLE_CAMERA)
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? =
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
            setPath(currentPhotoPath ?: "")
        }
    }

    fun returnFile(requestCode: Int, resultCode: Int, data: Intent?): File? {
        return if (requestCode == CALL_SIMPLE_CAMERA && resultCode == AppCompatActivity.RESULT_OK) {
            val path = getPath()
            if (path.isNotEmpty()) {
                File(path)
            } else {
                Toast.makeText(
                    context,
                    "Empty Photopath",
                    Toast.LENGTH_SHORT
                ).show()
                null
            }
        } else {
            null
        }
    }

    private fun setPath(value: String = "") {
        sharedPreferences.edit {
            putString(PATH_SIMPLE_CAMERA, value)
        }
    }

    private fun getPath(): String {
        return currentPhotoPath?.let {
            it
        } ?: kotlin.run {
            sharedPreferences.getString(PATH_SIMPLE_CAMERA, "") ?: ""
        }
    }
}