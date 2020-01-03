package com.explore.playground.recorder

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.explore.playground.R
import kotlinx.android.synthetic.main.activity_recording.*
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timer

private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
private const val LOG_TAG = "RECORD"

class RecordingActivity : AppCompatActivity() {
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var permissions: Array<String> = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    private var permissionToRecordAccepted = false
    private var fileSource: String = ""
    private var isRecording: Boolean = false
    private var isPlaying: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recording)


        fileSource = "${externalCacheDir?.absolutePath}/audiorecordtest2.aac"

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        btRecord.setOnClickListener {
            onRecord()
        }
        btPlay.setOnClickListener {
            onPlay()
        }
        sbPlayer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    private fun onRecord() {
        if (isRecording) {
            stopRecord()
            btRecord.text = "Record"
        } else {
            startRecord()
            btRecord.text = "Stop"
        }
    }

    private fun stopRecord() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
        isRecording = false
    }

    private fun startRecord() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
            setOutputFile(fileSource)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e(LOG_TAG, "prepare() failed")
            }

            start()
        }
        isRecording = true
    }

    private fun onPlay() {
        if (isPlaying) {
            stopPlaying()
        } else {
            startPlaying()
        }
    }

    fun startPlaying() {
        player = MediaPlayer().apply {
            try {
                setDataSource(fileSource)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e(LOG_TAG, "prepare() failed")
            }
        }
        tvDuration.text = getDuration(player?.duration)
        btPlay.text = "Playing"
        sbPlayer.setMax(TimeUnit.MILLISECONDS.toSeconds(player?.duration.toString().toLong()).toInt())
        timer(
            name = "TEHEE",
            daemon = false,
            initialDelay = 0,
            period = 1000,
            action = {
                if (player?.currentPosition == player?.duration) {
                    this.cancel()
                    this@RecordingActivity.runOnUiThread {
                        setSeekBar()
                        stopPlaying()
                    }

                } else {
                    this@RecordingActivity.runOnUiThread {
                        setSeekBar()
                    }
                }
            })
    }

    fun setSeekBar() {
        val current = player?.currentPosition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sbPlayer.setProgress(
                TimeUnit.MILLISECONDS.toSeconds(current.toString().toLong()).toInt(),
                true
            )
        } else {
            sbPlayer.setProgress(TimeUnit.MILLISECONDS.toSeconds(current.toString().toLong() + 1000).toInt())
        }
        tvCurrent.text = getDuration(current)
    }

    fun stopPlaying() {
        player?.release()
        player = null
        btPlay.text = "PLAY"
    }


    fun pausePlaying() {
        player?.pause()
        player = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if (!permissionToRecordAccepted) finish()
    }

    override fun onStop() {
        super.onStop()
        stopRecord()
        stopPlaying()
    }

    fun getDuration(value: Int?): String {
        value.let {
            val duration = value.toString().toLong()
            val minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
            val seconds =
                TimeUnit.MILLISECONDS.toSeconds(duration - TimeUnit.MINUTES.toSeconds(minutes))
            return String.format(
                "%02d:%02d",
                minutes, seconds
            )
        }
        return ""
    }
}
