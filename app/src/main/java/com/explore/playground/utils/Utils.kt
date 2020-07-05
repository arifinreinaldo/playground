package com.explore.playground.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.explore.playground.R
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


fun hideKeyboard(context: Context?, view: View?) {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun showKeyboard(context: Context?, view: View?) {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, 0)
}

fun View.hideView() {
    this.visibility = View.GONE
}

fun View.showView() {
    this.visibility = View.VISIBLE
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


fun ImageView.load(value: Any, ctx: Context) {
    val options = RequestOptions().placeholder(CircularProgressDrawable(ctx).apply {
        strokeWidth = 8f
        centerRadius = 2f
        start()
    }).error(R.drawable.bg_round)
    Glide.with(this).setDefaultRequestOptions(options).load(value).into(this)
}

fun RecyclerView.init(ctx: Context, type: String = "linear", horizontal: Boolean = false) {
    if (type.equals("linear", true)) {
        if (horizontal) {
            this.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
        } else {
            this.layoutManager = LinearLayoutManager(ctx)
        }
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

val INITIAL_PERMS = arrayOf(
    Manifest.permission.INTERNET,
    Manifest.permission.ACCESS_NETWORK_STATE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.VIBRATE,
    Manifest.permission.WAKE_LOCK,
    Manifest.permission.CALL_PHONE,
    Manifest.permission.CAMERA
)

fun Activity.hasPermissions(vararg permissions: String): Boolean {
    var all_approved = true
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        for (permission in permissions) {
            if (!hasPermission(permission)) {
                all_approved = false
            }
        }
    }
    return all_approved
}

fun Activity.hasPermission(permission: String): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (ActivityCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
    }
    return true
}

fun Activity.askPermission(
    permission: String = "",
    request: Int
) {
    if (!permission.isNullOrEmpty()) {
        ActivityCompat.requestPermissions(this, arrayOf(permission), request)
    }
}

fun Activity.askPermission(
    permissions: Array<String>,
    request: Int
) {
    if (permissions.isNotEmpty()) {
        ActivityCompat.requestPermissions(this, permissions, request)
    }
}


fun getMoshi(): Moshi {
    return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
}

