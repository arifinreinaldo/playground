package com.explore.playground.utils

import android.app.AlertDialog
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.explore.playground.R


fun hideKeyboard(context: Context?, view: View?) {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun showKeyboard(context: Context?, view: View?) {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, 0)
}

fun Button.disableFunction(ctx: Context) {
    this.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorPrimary))
    this.isEnabled = false
}

fun Button.enableFunction(ctx: Context) {
    this.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorPrimaryDark))
    this.isEnabled = true
}

fun write(message: String?) {
    message?.let {
        Log.d("Logging", message)
    }
}

fun simpleCountDown(
    seconds: Int,
    interval: Int,
    finish: () -> Unit,
    tick: () -> Unit
): CountDownTimer {
    return object : CountDownTimer(seconds.toMilis(), interval.toMilis()) {
        override fun onFinish() {
            this.cancel()
            finish()
        }

        override fun onTick(millisUntilFinished: Long) {
            tick()
        }

    }
}

fun Int.toMilis(): Long {
    return (this * 1000).toLong()
}


//fun ImageView.load(value: Any) {
//    Glide.with(this).load(value).into(this)
//}

fun RecyclerView.init(ctx: Context, type: String = "linear") {
    if (type.equals("linear", true)) {
        this.layoutManager = LinearLayoutManager(ctx)
    } else {
    }
}

fun confirmDialog(
    ctx: Context,
    title: String,
    positive: String,
    negative: String,
    posFun: () -> Unit
): AlertDialog {
    return AlertDialog.Builder(ctx).setTitle(title)
        .setPositiveButton(positive) { dialog, which ->
            posFun()
        }
        .setNegativeButton(negative) { dialog, which -> dialog.dismiss() }
        .create()
}
