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
    private lateinit var adapter: ImageAdapter
    private val PHOTOS_KEY = "easy_image_photos_list"
    val requestCode = 110
    private val CHOOSER_PERMISSIONS_REQUEST_CODE = 7459
    private val CAMERA_REQUEST_CODE = 7500
    private val CAMERA_VIDEO_REQUEST_CODE = 7501
    private val GALLERY_REQUEST_CODE = 7502
    private val DOCUMENTS_REQUEST_CODE = 7503
    private lateinit var easyImage: EasyImage
    override fun setLayoutId(): Int {
        return R.layout.activity_camera
    }

    override fun setInit() {
        if (!hasPermissions(*INITIAL_PERMS)) {
            askPermission(INITIAL_PERMS, requestCode)

        }

        rvGallery.init(ctx)
        adapter = ImageAdapter(ctx)
        adapter.listen = object : ImageAdapter.MediaFileListener {
            override fun onItemClicked(item: MediaFile) {

            }

        }
        rvGallery.adapter = adapter


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
            data,
            this,
            object : DefaultCallback() {
                override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                    onImageResult(imageFiles)
                }

            })
    }

    fun onImageResult(result: Array<MediaFile>) {
        adapter.addAllItem(result.toMutableList())
    }
}
