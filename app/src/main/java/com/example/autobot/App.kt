package com.example.autobot

import androidx.multidex.MultiDexApplication
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import timber.log.Timber


class App: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        AppCenter.start(
            this, "67d73bc8-f5dd-4224-9003-6b0f5fc816b4",
            Analytics::class.java, Crashes::class.java
        )
    }
}
