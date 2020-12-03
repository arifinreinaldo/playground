package com.explore.playground

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import com.explore.playground.adapterAndRecyclerview.DummyRecyclerActivity
import com.explore.playground.base.BaseActivity
import com.explore.playground.base.BaseApp
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
import com.explore.playground.utils.load
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_menu.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class MenuActivity : BaseActivity() {
    override fun setLayoutId(): Int {
        return R.layout.activity_menu
    }

    override fun setInit() {
        (application as BaseApp).appComponent.inject(this)
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
        btShare.setOnClickListener {
            val text = "12321"
            val format = MultiFormatWriter()
            try {
                val bitMatrix = format.encode(text, BarcodeFormat.QR_CODE, 200, 200)
                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.createBitmap(bitMatrix)
                val file = createImageFile()
                val bos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
                val fos = FileOutputStream(file)
                fos.write(bos.toByteArray())
                fos.flush()
                fos.close()
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    "${BuildConfig.APPLICATION_ID}.fileprovider",
                    file
                )
                ivImg.load(photoURI)
                val whatsappIntent = Intent(Intent.ACTION_SEND)
                whatsappIntent.type = "text/plain"
                whatsappIntent.setPackage("com.whatsapp")
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share")
                whatsappIntent.putExtra(Intent.EXTRA_STREAM, photoURI)
                whatsappIntent.type = "image/jpg"
                whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivity(whatsappIntent)
            } catch (e: Exception) {
                e.printStackTrace();
            }
        }
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? =
            this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
        }
    }
}
