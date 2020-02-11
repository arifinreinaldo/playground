package com.explore.playground.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    companion object {
        var lang = ""
    }

    protected lateinit var ctx: Context
    protected lateinit var act: Activity
    abstract fun setLayoutId(): Int
    abstract fun setInit()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutId())
        setDefautSetting()
        setInit()
    }

    fun setDefautSetting() {
        //specified default setting for your apps
        act = this
        ctx = this
    }

    fun showMessage(message: String) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
    }

    fun showMessage(@StringRes message: Int) {
        Toast.makeText(ctx, getString(message), Toast.LENGTH_SHORT).show()
    }


//    override fun attachBaseContext(newBase: Context) {
//        super.attachBaseContext(updateContext(newBase))
//    }
//
//    fun updateContext(ctx: Context): Context {
//        val locale = Locale(lang.toLowerCase())
//        Locale.setDefault(locale)
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
//            return setLocale16(ctx, locale)
//        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            return setLocale17(ctx, locale)
//        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
//            return setLocale25(ctx, locale)
//        }
//        return setLocale29(ctx, locale)
//    }
//
//    fun setLocale16(ctx: Context, locale: Locale): Context {
//        val resource = ctx.resources
//        val conf = resource.configuration
//        conf.locale = locale
//        resource.updateConfiguration(conf, resource.displayMetrics)
//        return ctx
//    }
//
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//    fun setLocale17(ctx: Context, locale: Locale): Context {
//        val resource = ctx.resources
//        val conf = Configuration(resource.configuration)
//        conf.setLocale(locale)
//        resource.updateConfiguration(conf, resource.displayMetrics)
//        return ctx
//    }
//
//    @TargetApi(Build.VERSION_CODES.N_MR1)
//    fun setLocale25(ctx: Context, locale: Locale): Context {
//        val resource = ctx.resources
//        val conf = Configuration(resource.configuration)
//        conf.setLocale(locale)
//        resource.updateConfiguration(conf, resource.displayMetrics)
//        return ctx
//    }
//
//    @TargetApi(Build.VERSION_CODES.Q)
//    fun setLocale29(ctx: Context, locale: Locale): Context {
//        val conf = ctx.resources.configuration
//        conf.setLocale(locale)
//        return ctx.createConfigurationContext(conf)
//    }
}