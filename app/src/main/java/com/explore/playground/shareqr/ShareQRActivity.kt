package com.explore.playground.shareqr

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.explore.playground.BuildConfig
import com.explore.playground.R
import com.explore.playground.utils.load
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_share_q_r.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ShareQRActivity : AppCompatActivity() {
    lateinit var photoURI: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_q_r)

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
            photoURI = FileProvider.getUriForFile(
                this,
                "${BuildConfig.APPLICATION_ID}.fileprovider",
                file
            )
            ivImg.load(photoURI)

        } catch (e: Exception) {
            e.printStackTrace();
        }
        btShare.setOnClickListener {
            shareImage()
        }
        btWhatsapp.setOnClickListener {
            shareWhatsapp()
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

    fun shareImage() {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, photoURI)
            type = "image/jpeg"
        }
        startActivity(
            Intent.createChooser(
                shareIntent,
                "BAGI BAGI"
            )
        )
    }

    fun shareWhatsapp() {
        try {
            val whatsappIntent = Intent(Intent.ACTION_SEND)
            whatsappIntent.type = "text/plain"
            whatsappIntent.setPackage("com.whatsapp")
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share")
            whatsappIntent.putExtra(Intent.EXTRA_STREAM, photoURI)
            whatsappIntent.type = "image/jpg"
            whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(whatsappIntent)
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}