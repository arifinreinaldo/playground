package com.explore.playground

import android.content.Intent
import android.util.Log
import com.explore.playground.adapterAndRecyclerview.DummyRecyclerActivity
import com.explore.playground.base.BaseActivity
import com.explore.playground.bottomsheetdialog.BottomSheetActivity
import com.explore.playground.dialogpicker.PickerActivity
import com.explore.playground.easycamera.CameraActivity
import com.explore.playground.easycamera.CameraNative
import com.explore.playground.exoplayer.ExoActivity
import com.explore.playground.fcm.FCMActivity
import com.explore.playground.filepicker.FilePickerActivity
import com.explore.playground.localization.LocalizationActivity
import com.explore.playground.mvvm.VMActivity
import com.explore.playground.mvvmnavfan.CompositeActivity
import com.explore.playground.navigator.NavigatorActivity
import com.explore.playground.otptemplate.OTPActivity
import com.explore.playground.recorder.RecordingActivity
import com.explore.playground.scrollAlpha.ScrollAlphaActivity
import com.explore.playground.sociallogin.SocialLoginActivity
import com.explore.playground.speechrecognition.SpeechRecogActivity
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : BaseActivity() {
    override fun setLayoutId(): Int {
        return R.layout.activity_menu
    }

    override fun setInit() {
        btGeneral.setOnClickListener {
            val intent = Intent(this, DummyRecyclerActivity::class.java)
            startActivity(intent)
        }

        btOTP.setOnClickListener {
            val intent = Intent(this, OTPActivity::class.java)
            startActivity(intent)
        }
        btRecord.setOnClickListener {
            startActivity(Intent(this, RecordingActivity::class.java))
            Log.d("Manifest", "")
        }
        btExo.setOnClickListener {
            startActivity(Intent(this, ExoActivity::class.java))
            Log.d("Manifest", "")
        }
        btRecog.setOnClickListener {
            startActivity(Intent(this, SpeechRecogActivity::class.java))
            Log.d("Manifest", "")
        }
        btNav.setOnClickListener {
            startActivity(Intent(this, NavigatorActivity::class.java))
            Log.d("Manifest", "")
        }
        btVM.setOnClickListener {
            startActivity(Intent(this, VMActivity::class.java))
        }
        btPicker.setOnClickListener {
            startActivity(Intent(this, PickerActivity::class.java))
        }
        btLocalization.setOnClickListener {
            startActivity(Intent(this, LocalizationActivity::class.java))
        }
        btFCM.setOnClickListener {
            startActivity(Intent(this, FCMActivity::class.java))
        }
        btCamera.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }
        btBottomSheet.setOnClickListener {
            startActivity(Intent(this, BottomSheetActivity::class.java))
        }
        btFilePicker.setOnClickListener {
            startActivity(Intent(this, FilePickerActivity::class.java))
        }
        btmvvmnavfan.setOnClickListener {
            startActivity(Intent(this, CompositeActivity::class.java))
        }
        btSocialLogin.setOnClickListener {
            startActivity(Intent(this, SocialLoginActivity::class.java))
        }
        btScrollAlpha.setOnClickListener {
            startActivity(Intent(this, ScrollAlphaActivity::class.java))
        }
        btCameraNative.setOnClickListener {
            startActivity(Intent(this, CameraNative::class.java))
        }
    }
}
