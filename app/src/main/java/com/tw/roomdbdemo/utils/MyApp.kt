package com.tw.roomdbdemo.utils

import android.content.res.Configuration
import androidx.multidex.MultiDexApplication


class MyApp: MultiDexApplication() {

    companion object {
        lateinit var instance: MyApp private set
    }

    override fun onCreate() { super.onCreate()
        instance = this
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

}