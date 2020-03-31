package com.explore.playground.filepicker

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import androidx.core.content.FileProvider
import com.explore.playground.R
import com.explore.playground.base.BaseActivity
import com.explore.playground.utils.*
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import kotlinx.android.synthetic.main.activity_file_picker.*
import kotlinx.android.synthetic.main.activity_menu.btPicker
import java.io.File


class FilePickerActivity : BaseActivity(), PickiTCallbacks {
    private val requestCode = 123
    private lateinit var ur: Uri
    private lateinit var current_path: String
    private lateinit var file: File
    private lateinit var pickit: PickiT
    val PICKFILE_RESULT_CODE = 111
    override fun setLayoutId(): Int {
        return R.layout.activity_file_picker
    }

    override fun setInit() {
        if (hasPermissions(*INITIAL_PERMS)) {
            btPicker.showView()
        } else {
            askPermission(INITIAL_PERMS, requestCode)
        }
        btPicker.setOnClickListener {
            var chooseFile = Intent(Intent.ACTION_GET_CONTENT)
//            val mimeTypes = arrayOf(
//                "application/msword",
//                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",  // .doc & .docx
//                "application/vnd.ms-powerpoint",
//                "application/vnd.openxmlformats-officedocument.presentationml.presentation",  // .ppt & .pptx
//                "application/vnd.ms-excel",
//                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",  // .xls & .xlsx
//                "text/plain",
//                "application/pdf",
//                "application/zip,image/*"
//            )
            chooseFile.type = "application/pdf"
            chooseFile = Intent.createChooser(chooseFile, "Choose a file")
            startActivityForResult(chooseFile, PICKFILE_RESULT_CODE)
            pickit = PickiT(ctx, this)
        }
        btOnline.setOnClickListener {
            val url = "http://www.pdf995.com/samples/pdf.pdf"
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
        btShow.setOnClickListener {
            val file = File(
                current_path
            )
            lateinit var photoURI: Uri
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                photoURI = FileProvider.getUriForFile(
                    ctx,
                    ctx.getApplicationContext().getPackageName().toString() + ".provider", file
                )
            } else {
                photoURI = Uri.fromFile(file)
            }
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(photoURI, "application/pdf")
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                val chooser = Intent.createChooser(intent, "CHOOSE")
                startActivity(chooser)
            } catch (e: Exception) {
                showMessage("No Applications to Open PDF File")
            }

//            val browserIntent = Intent(Intent.ACTION_VIEW)
//            browserIntent.setDataAndType(photoURI, "application/pdf")
//
//            val chooser =
//                Intent.createChooser(browserIntent, "CHOOSE PDF")
//            chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK // optional
//            chooser.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
//            startActivity(chooser)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICKFILE_RESULT_CODE) {

            data?.data?.let {
                val data = Uri.fromParts(it.scheme, it.schemeSpecificPart, it.fragment)
                val dataStr = data.path
                val cursor = contentResolver.query(it, null, null, null, null)
                cursor?.let { cursor ->
                    cursor.moveToFirst()
                    val str_name =
                        cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                    cursor.close()
                    showMessage(str_name)
                }
                pickit.getPath(it, Build.VERSION.SDK_INT)
            }
        }
    }

    override fun PickiTonProgressUpdate(progress: Int) {

    }

    override fun PickiTonStartListener() {

    }

    override fun PickiTonCompleteListener(
        path: String?,
        wasDriveFile: Boolean,
        wasUnknownProvider: Boolean,
        wasSuccessful: Boolean,
        Reason: String?
    ) {

        current_path = path.toString()
        btShow.showView()
    }

    override fun onDestroy() {
        super.onDestroy()
        pickit.deleteTemporaryFile()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        pickit.deleteTemporaryFile()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var granted = true
        if (requestCode == requestCode) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.isNotEmpty()) {
                for (i in INITIAL_PERMS.indices) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        granted = false
                    }
                }
            }
            if (!granted) {
                btPicker.hideView()
            } else {
                btPicker.showView()
            }
        }
    }
}
