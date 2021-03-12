package com.explore.playground.draggable

import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.explore.playground.R
import com.explore.playground.utils.fadeIn
import com.explore.playground.utils.fadeOut
import com.explore.playground.utils.load
import kotlinx.android.synthetic.main.activity_dragable.*


class DraggableActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dragable)

        ivImage.load("https://static.wikia.nocookie.net/pokemon-fano/images/6/6f/Poke_Ball.png")
//        ivImage.setDraggable()
        ivImage.setOnClickListener {
            Toast.makeText(this, "thi", Toast.LENGTH_SHORT).show()
        }

        animeView.setDraggable()
        animeView.setOnClickListener {
            Toast.makeText(this, "hiyahiya", Toast.LENGTH_SHORT).show()
        }

        svView.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    animeView.fadeOut()
                    true
                }
                MotionEvent.ACTION_UP -> {
                    animeView.fadeIn()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}
