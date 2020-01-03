package com.explore.playground.exoplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.explore.playground.R
import com.google.android.exoplayer2.SimpleExoPlayer

class ExoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo)
        val player = SimpleExoPlayer.Builder(this).build()
    }
}
