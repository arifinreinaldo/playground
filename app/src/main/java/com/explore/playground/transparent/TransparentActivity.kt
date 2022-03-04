package com.explore.playground.transparent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.explore.playground.R
import kotlinx.android.synthetic.main.activity_transparent.*

class TransparentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transparent)

        ArrayAdapter(
            applicationContext,
            R.layout.support_simple_spinner_dropdown_item,
            listOf("Boracai", "USA")
        ).apply {
            input.setAdapter(this)
        }
    }
}