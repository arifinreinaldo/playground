package com.explore.playground.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.explore.playground.R
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

val isoFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" //"2020-11-12T08:49:30.000Z"
val time = "hh:mm a" //with am pM

fun hideKeyboard(context: Context?, view: View?) {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun showKeyboard(context: Context?, view: View?) {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, 0)
}

fun View.fadeIn() {
    this.visibility = View.VISIBLE
    this.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.fade_in))
}

fun View.fadeOut() {
    this.visibility = View.GONE
    this.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.fade_out))
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


fun ImageView.load(value: Any) {
    Glide.with(this).load(value).into(this)
}

fun ImageView.load(value: Any, ctx: Context) {
    val options = RequestOptions().placeholder(CircularProgressDrawable(ctx).apply {
        strokeWidth = 8f
        centerRadius = 2f
        start()
    }).error(R.drawable.bg_round)
    Glide.with(this).setDefaultRequestOptions(options).load(value).into(this)
}

fun RecyclerView.init(
    ctx: Context,
    type: String = "linear",
    horizontal: Boolean = false,
    reverseLayout: Boolean = false,
    column: Int = 2
) {
    if (type.equals("linear", true)) {
        if (horizontal) {
            this.layoutManager =
                LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, reverseLayout)
        } else {
            this.layoutManager =
                LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, reverseLayout)
        }
    } else if (type.equals("grid", true)) {
        this.layoutManager = GridLayoutManager(context, column)
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

val EditText.value
    get() = text?.toString() ?: ""

fun getOption(context: Context): RequestOptions {
    return RequestOptions()
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .placeholder(
            CircularProgressDrawable(context).apply {
                strokeWidth = 8f
                centerRadius = 20f
                start()
            }
        )
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun getErrorImage(imageId: Int?, context: Context): Drawable? {
    return imageId?.let {
        context.getDrawable(imageId)
    } ?: run {
        null
    }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun ImageView.loadCircle(
    uri: Any?,
    imageId: Int? = null
) {

    Glide.with(this.context)
        .setDefaultRequestOptions(getOption(this.context))
        .load(uri)
        .circleCrop()
        .error(getErrorImage(imageId, this.context))
        .into(this)
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun ImageView.loadCurve(
    value: Any,
    imageId: Int? = null
) {
    Glide.with(this.context)
        .setDefaultRequestOptions(getOption(this.context))
        .load(value)
        .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
        .error(getErrorImage(imageId, this.context))
        .into(this);
}

fun Boolean.isTrue(block: Boolean.() -> Unit) {
    if (this) {
        block()
    }
}

fun Activity.color(@ColorRes color: Int) = ContextCompat.getColor(this, color)
fun Fragment.color(@ColorRes color: Int) = this.requireActivity().color(color)

fun Activity.drawable(@DrawableRes drawable: Int) = ContextCompat.getDrawable(this, drawable)
fun Fragment.drawable(@DrawableRes drawable: Int) = this.requireActivity().drawable(drawable)

fun Activity.copyClipboard(value: String, label: String = "") {
    (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).apply {
        setPrimaryClip(ClipData.newPlainText(label, value))
    }
}

fun Fragment.copyClipboard(value: String, label: String = "") = requireActivity().copyClipboard(value, label)


//fun solutions(A: IntArray): Int {
//    val N: Int = A.size
////    val set: Set<Int> = hashMapOf<Int,Int>()
//    val set = hashMapOf<Int, Int>()
//    for (a in A) {
//        if (a > 0) {
//            set[a] = a
//        }
//    }
//    for (i in 1..N + 1) {
//        if (!set.contains(i)) {
//            return i
//        }
//    }
//    return 0
//}
//
//
//fun solution(A: IntArray): Int {
//    var array = hashMapOf<Int, Int>()
//    A.forEach {
//        if (it > 0) {
//            array[it] = it
//        }
//    }
//    var idx = 0
//    var found: Boolean
//    do {
//        idx++
//        found = array.contains(idx)
//    } while (!found)
//    return idx
//}
//
//fun solution(S: String): Int {
//    var total = 0
//    if (S.isNotEmpty()) {
//        var a = S.replace("b", "").length
//        var b = S.replace("a", "").length
//        if (a % 3 == 0) {
//            if (a == 0) {
//                if (b > 2) {
//                    for (i in 1..b - 2) {
//                        total += i
//                    }
//                    return total
//                } else {
//                    return 0
//                }
//            } else {
//                if (b == 0) {
//                    return 0
//                } else {
//                    var asplit = a / 3
//                    var split = ""
//                    for (i in 1..asplit) {
//                        split += "a"
//                    }
//                    val splits = S.split(split)
//
//                    return (splits[1].length + 1) * (splits[2].length + 1)
//                }
//            }
//        } else if (a % 3 != 0) {
//            return 0
//        }
//    } else {
//        return 0
//    }
//    return 0
//}
//
//fun solus(S: String, K: Int) {
//    var map = hashMapOf<String, Int>()
//    var arr = mutableListOf<String>()
//    S.forEach { char ->
//        map.get(char)?.let { count ->
//            map.put(char.toString(), count + 1)
//        } ?: run {
//            map.put(char.toString(), 1)
//        }
//        arr.add(char)
//        var print = ""
//        arr.distinct()
//        map.forEach { pointer ->
//            print += pointer.component1()
//        }
//    }
//
//}