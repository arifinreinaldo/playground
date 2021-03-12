package com.explore.playground.arrow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import arrow.core.Either
import arrow.core.flatMap
import com.explore.playground.R

class ArrowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arrow)

        val right: Either<String, Int> = Either.Right(5)
        val value = right.flatMap { f -> Either.Right(f + 1) }
        val values = right.flatMap { Either.Right(it + 1) }
        
//        val value = right.flatMap { Either.Right(it + 1) }
    }
}