package com.explore.playground.easycamera

import android.content.Intent
import android.content.pm.PackageManager
import com.explore.playground.R
import com.explore.playground.base.BaseActivity
import com.explore.playground.utils.*
import kotlinx.android.synthetic.main.activity_camera.*
import pl.aprilapps.easyphotopicker.*
import pl.aprilapps.easyphotopicker.MediaFile


class CameraActivity : BaseActivity() {
    val requestCode = 110
    private lateinit var easyImage: EasyImage
    override fun setLayoutId(): Int {
        return R.layout.activity_camera
    }

    override fun setInit() {
        if (!hasPermissions(*INITIAL_PERMS)) {
            askPermission(INITIAL_PERMS, requestCode)

        }

        easyImage = EasyImage.Builder(ctx).setChooserTitle("Pick Mediazz")
            .setCopyImagesToPublicGalleryFolder(false)
            .setChooserType(ChooserType.CAMERA_AND_GALLERY).setFolderName("SAMPLES").build()

        takePict.setOnClickListener {
            if (hasPermission(android.Manifest.permission.CAMERA)) {
                easyImage.openChooser(act)
            } else {
                askPermission(
                    android.Manifest.permission.CAMERA,
                    requestCode
                )
            }
        }
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
                takePict.hideView()
            } else {
                takePict.showView()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        easyImage.handleActivityResult(
            requestCode,
            resultCode,
            fixDataIntentForEasyImage(data),
            this,
            object : DefaultCallback() {
                override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                    onImageResult(imageFiles)
                }

            })
    }

    private fun fixDataIntentForEasyImage(dataIntent: Intent?): Intent? {
        if (dataIntent == null) {
            return null
        }
        if (dataIntent.dataString.isNullOrEmpty()) {
            return null
        }

        return dataIntent
    }

    fun onImageResult(result: Array<MediaFile>) {
        if (result.isNotEmpty()) {
            imagePict.load(result.first().file)
        }
    }
}
