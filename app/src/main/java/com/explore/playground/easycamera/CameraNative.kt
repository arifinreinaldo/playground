package com.explore.playground.easycamera

import android.content.ActivityNotFoundException
import android.content.Intent
import android.provider.MediaStore
import com.explore.playground.R
import com.explore.playground.base.BaseActivity
import com.explore.playground.utils.load
import kotlinx.android.synthetic.main.activity_camera_native.*

const val REQUEST_IMAGE_CAPTURE_BITMAP = 999
const val REQUEST_IMAGE_CAPTURE = 9998

class CameraNative : BaseActivity() {
    lateinit var simpleCamera: SimpleCamera
    override fun setLayoutId(): Int {
        return R.layout.activity_camera_native
    }

    override fun setInit() {
        simpleCamera = SimpleCamera(this, "com.explore.playground.fileprovider")
        takePict.setOnClickListener {
            simpleCamera.openCamera()
        }
    }

    private fun dispatchTakePictureIntentBitmap() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_BITMAP)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        simpleCamera.returnFile(requestCode, resultCode, data)?.let {
            imagePict.load(it)
        }
    }

//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
////        simpleCamera.restorePath(savedInstanceState.getString("PathX", ""))
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
////        outState.putString("PathX", simpleCamera.getPath())
//        super.onSaveInstanceState(outState)
//    }
}
