package com.explore.playground.base

import androidx.multidex.MultiDexApplication
import com.explore.playground.MenuActivity
import com.explore.playground.repository.Repository
import com.explore.playground.utils.LocalValue
import com.yariksoffice.lingver.Lingver
import dagger.Component
import java.util.*

@Component
interface AppComponent {
    fun inject(activity: MenuActivity)
}

class BaseApp : MultiDexApplication() {
    val appComponent = DaggerAppComponent.create()
    override fun onCreate() {
        super.onCreate()
        Lingver.init(this, Locale.getDefault())
        //Ganti bahasa
        LocalValue.init(this)
        Repository.init(this)
    }
}