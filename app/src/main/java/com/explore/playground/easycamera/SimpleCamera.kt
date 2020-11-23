package com.explore.playground.easycamera

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.content.edit
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*


const val CALL_SIMPLE_CAMERA = 21892
const val CALL_SIMPLE_GALLERY = 21893
const val PREF_SIMPLE_CAMERA = "SIMPLE_ABSOLUTE_PATH_21892"
const val PATH_SIMPLE_CAMERA = "PATH_ABSOLUTE_PATH_21892"

class SimpleCamera {
    private val dialog by lazy {
        BottomSheetDialog(context)
    }
    val layout: LinearLayout by lazy {
        LinearLayout(context)
    }

    private var call: ActivityCaller
    private var fileProvider: String
    private var context: Context
    private val sharedPreferences: SharedPreferences
    private var allowMultiple: Boolean = false

    constructor(activity: Activity, provider: String, allow: Boolean = false) {
        this.call = ActivityCaller(activity = activity)
        this.fileProvider = provider
        this.context = call.context
        sharedPreferences = context.getSharedPreferences(PREF_SIMPLE_CAMERA, Context.MODE_PRIVATE)
        this.allowMultiple = allow
        initLayout(context)
    }

    constructor(fragment: Fragment, provider: String, allow: Boolean = false) {
        this.call = ActivityCaller(fragment = fragment)
        this.fileProvider = provider
        this.context = call.context
        sharedPreferences = context.getSharedPreferences(PREF_SIMPLE_CAMERA, Context.MODE_PRIVATE)
        this.allowMultiple = allow
        initLayout(context)
    }

    constructor(fragment: android.app.Fragment, provider: String, allow: Boolean = false) {
        this.call = ActivityCaller(deprecatedFragment = fragment)
        this.fileProvider = provider
        this.context = call.context
        sharedPreferences = context.getSharedPreferences(PREF_SIMPLE_CAMERA, Context.MODE_PRIVATE)
        this.allowMultiple = allow
        initLayout(context)
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

    fun openGallery(allowMultiple: Boolean = false) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (Build.VERSION.SDK_INT >= 18) intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, allowMultiple)
        call?.startActivityForResult(intent, CALL_SIMPLE_GALLERY)
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
        } else if (requestCode == CALL_SIMPLE_GALLERY) {
            val clipData = data?.clipData
            if (clipData != null) {
                for (i in 0 until clipData.itemCount) {
                    val uri = clipData.getItemAt(i).uri
                    return pickedExistingPicture(context, uri)
                }
            } else {
                data?.let {
                    return onPickedExistingPicturesFromLocalStorage(data!!, context)
                }
            }
            null
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

    @Throws(IOException::class)
    internal fun pickedExistingPicture(context: Context, photoUri: Uri): File {
        val pictureInputStream = context.contentResolver.openInputStream(photoUri)
        val directory = tempImageDirectory(context)
        val photoFile = File(
            directory, generateFileName() + "." + getMimeType(
                context,
                photoUri
            )
        )
        photoFile.createNewFile()
        writeToFile(pictureInputStream!!, photoFile)
        return photoFile
    }

    private fun tempImageDirectory(context: Context): File {
        val privateTempDir = File(context.cacheDir, "SimpleCamera")
        if (!privateTempDir.exists()) privateTempDir.mkdirs()
        return privateTempDir
    }

    private fun generateFileName(): String {
        return "sc_${System.currentTimeMillis()}"
    }

    private fun getMimeType(context: Context, uri: Uri): String? {
        val extension: String?

        //Check uri format to avoid null
        if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            //If scheme is a content
            val mime = MimeTypeMap.getSingleton()
            extension = mime.getExtensionFromMimeType(context.contentResolver.getType(uri))
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
        }

        return extension
    }

    private fun writeToFile(inputStream: InputStream, file: File) {
        try {
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var length: Int = inputStream.read(buffer)
            while (length > 0) {
                outputStream.write(buffer, 0, length)
                length = inputStream.read(buffer)
            }
            outputStream.close()
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun cleanup() {
        val privateTempDir = File(context.cacheDir, "SimpleCamera")
        if (privateTempDir.exists()) privateTempDir.deleteRecursively()
    }

    private fun onPickedExistingPicturesFromLocalStorage(
        resultIntent: Intent,
        context: Context
    ): File? {
        return try {
            val uri = resultIntent.data!!
            pickedExistingPicture(context, uri)
        } catch (error: Throwable) {
            error.printStackTrace()
            null
        }
    }

    private fun initLayout(ctx: Context) {
        //layout
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50)
        layout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        //title
        val textView = TextView(ctx)
        textView.text = "Chooser"
        textView.textSize = 20.0F
        textView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layout.addView(textView)
        val line = View(context)
        line.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3)
        line.setBackgroundColor(
            if (Build.VERSION.SDK_INT >= 23) {
                ctx.getColor(android.R.color.black)
            } else {
                ctx.resources.getColor(android.R.color.black)
            }
        )
        layout.addView(line)

        //content
        val contentLayout = LinearLayout(ctx)
        contentLayout.orientation = LinearLayout.HORIZONTAL
        contentLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        //option1
        val optionLinear = LinearLayout(ctx)
        optionLinear.orientation = LinearLayout.VERTICAL
        optionLinear.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val textViewOption = TextView(ctx)
        textViewOption.text = "Camera"
        optionLinear.setOnClickListener {
            openCamera()
            dialog.dismiss()
        }

        val ivIcon = ImageView(ctx)
        ivIcon.setImageResource(android.R.drawable.ic_menu_camera)
        optionLinear.addView(ivIcon)
        optionLinear.addView(textViewOption)
        contentLayout.addView(optionLinear)

        //option2
        val optionLinear2 = LinearLayout(ctx)
        optionLinear2.orientation = LinearLayout.VERTICAL
        optionLinear2.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val ivIcon2 = ImageView(ctx)
        ivIcon2.setImageResource(android.R.drawable.ic_menu_gallery)
        optionLinear2.addView(ivIcon2)
        val textViewOption2 = TextView(ctx)
        textViewOption2.text = "Gallery"
        optionLinear2.setOnClickListener {
            openGallery(allowMultiple)
            dialog.dismiss()
        }
        optionLinear2.addView(textViewOption2)

        contentLayout.addView(optionLinear2)
        layout.addView(contentLayout)
        dialog.setContentView(layout)
    }

    fun openChooser() {
        dialog.show()
    }
}
