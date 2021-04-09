package com.example.autobot

import android.app.Application
import androidx.multidex.MultiDexApplication
import timber.log.Timber

class App: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}