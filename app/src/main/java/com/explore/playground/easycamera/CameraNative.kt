package com.explore.playground.easycamera

import android.content.Intent
import com.explore.playground.BuildConfig
import com.explore.playground.R
import com.explore.playground.base.BaseActivity
import com.explore.playground.utils.load
import kotlinx.android.synthetic.main.activity_camera_native.*

class CameraNative : BaseActivity() {
    lateinit var simpleCamera: SimpleCamera

    override fun setLayoutId(): Int {
        return R.layout.activity_camera_native
    }

    override fun setInit() {
        simpleCamera = SimpleCamera(
            this,
            "${BuildConfig.APPLICATION_ID}.fileprovider",
            icon_camera = R.drawable.ic_camera,
            icon_gallery = R.drawable.ic_gallery
        )
        simpleCamera.cleanup()
        takePict.setOnClickListener {
            simpleCamera.openChooser()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val img = simpleCamera.returnFile(requestCode, resultCode, data)
        img?.let {
            imagePict.load(it)
        }
    }
}
