package com.explore.playground.base

import androidx.multidex.MultiDexApplication
import com.explore.playground.utils.LocalValue
import com.yariksoffice.lingver.Lingver
import java.util.*

class BaseApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Lingver.init(this, Locale.getDefault())
        //Ganti bahasa
        LocalValue.init(this)
    }
}