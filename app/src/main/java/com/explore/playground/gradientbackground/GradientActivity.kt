package com.explore.playground.gradientbackground

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.explore.playground.R
import com.explore.playground.utils.load
import kotlinx.android.synthetic.main.activity_gradient.*

class GradientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gradient)
        ivIcon.load("https://upload.wikimedia.org/wikipedia/id/thumb/d/d4/Trans_Studio_logo_2011.svg/1200px-Trans_Studio_logo_2011.svg.png")
        llBG.background =
            setBackground(intArrayOf(Color.parseColor("#FFFFFF"), Color.parseColor("#000000")))
    }

    fun setBackground(blah: IntArray): GradientDrawable {
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            blah
        );
        gradientDrawable.cornerRadius = 20f;
        return gradientDrawable
    }
}