package com.explore.playground

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.explore.playground.dialogpicker.PickerActivity
import com.explore.playground.exoplayer.ExoActivity
import com.explore.playground.generaladapter.DummyRecyclerActivity
import com.explore.playground.mvvm.VMActivity
import com.explore.playground.navigator.NavigatorActivity
import com.explore.playground.otptemplate.OTPActivity
import com.explore.playground.recorder.RecordingActivity
import com.explore.playground.room.RoomActivity
import com.explore.playground.speechrecognition.SpeechRecogActivity
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

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
        btRoom.setOnClickListener {
            startActivity(Intent(this, RoomActivity::class.java))
        }
        btVM.setOnClickListener {
            startActivity(Intent(this, VMActivity::class.java))
        }
        btPicker.setOnClickListener {
            startActivity(Intent(this, PickerActivity::class.java))
        }
    }
}
