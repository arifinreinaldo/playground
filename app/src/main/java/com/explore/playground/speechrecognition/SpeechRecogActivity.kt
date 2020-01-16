package com.explore.playground.speechrecognition

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.MotionEvent
import androidx.core.app.ActivityCompat
import com.explore.playground.base.BaseActivity
import com.explore.playground.R
import kotlinx.android.synthetic.main.activity_speech_recog.*

private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

class SpeechRecogActivity : BaseActivity() {
    override fun setLayoutId(): Int {
        return R.layout.activity_speech_recog
    }

    override fun setInit() {

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        val speechRecog = SpeechRecognizer.createSpeechRecognizer(this)
        val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intentSpeech.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intentSpeech.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            "in_ID"
        )

        speechRecog.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {

            }

            override fun onRmsChanged(rmsdB: Float) {

            }

            override fun onBufferReceived(buffer: ByteArray?) {

            }

            override fun onPartialResults(partialResults: Bundle?) {

            }

            override fun onEvent(eventType: Int, params: Bundle?) {

            }

            override fun onBeginningOfSpeech() {

            }

            override fun onEndOfSpeech() {

            }

            override fun onError(error: Int) {

            }

            override fun onResults(results: Bundle?) {
                results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).let {
                    it?.get(0).let {
                        etRecognition.setText(it)
                    }
                }
            }
        })

        btRecord.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                speechRecog.stopListening()
                etRecognition.hint = "You Will See Input Here"
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                speechRecog.startListening(intentSpeech)
                etRecognition.hint = "Listening..."
                etRecognition.setText("")
            }
            return@setOnTouchListener false
        }
    }

    private var permissions: Array<String> = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    private var permissionToRecordAccepted = false

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted =
            if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            } else {
                false
            }
        if (!permissionToRecordAccepted) finish()
    }

}
