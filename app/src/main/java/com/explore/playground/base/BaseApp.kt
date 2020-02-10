package com.explore.playground.base

import android.app.Application

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
//        Lingver.init(this, Locale.getDefault())
        //Ganti bahasa
    }
}