package com.telemedconnect.patient


import android.app.Application

import com.zegocloud.zimkit.services.ZIMKit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class App : Application() {



    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    // Provide a way to access this scope
    fun getApplicationScope(): CoroutineScope = applicationScope

    companion object {
        lateinit var sInstance: App
            private set

        const val APP_ID: Long = 1367648236 // The AppID you get from ZEGOCLOUD Admin Console.
        const val APP_SIGN: String = "79e83f6afd58ff3203cd9a829ba05e980da05bdcebb9fd40d07419639619c407"
    }

    override fun onCreate() {
        super.onCreate()

        sInstance = this

        // The App Sign you get from ZEGOCLOUD Admin Console.
        ZIMKit.initWith(this, APP_ID, APP_SIGN)
        // Online notification for the initialization (use the following code if this is needed).
        ZIMKit.initNotifications()
    }
}