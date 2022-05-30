package com.adform.trackingsdk.demoapp

import android.app.Application
import android.util.Log
import com.adobe.marketing.mobile.MobileCore
import com.adobe.marketing.mobile.LoggingMode
import com.adform.trackingsdk.demoapp.DemoApp
import com.adform.sdk.AdformAdobeExtension
import com.adobe.marketing.mobile.AdobeCallback
import com.adobe.marketing.mobile.InvalidInitException
import com.adobe.marketing.mobile.Lifecycle
import com.adobe.marketing.mobile.Signal

class DemoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MobileCore.setApplication(this)
        MobileCore.setLogLevel(LoggingMode.VERBOSE)
        try {
            Log.d(TAG, "Starting app registration")
            Lifecycle.registerExtension()
            Signal.registerExtension()
            AdformAdobeExtension.registerExtension()
            MobileCore.start {
                Log.d(TAG, "Configuring MobileCore")
                MobileCore.configureWithAppID("d27f69626000/f57a0f4d3bb7/launch-900eb005ba72-development")
            }
        } catch (e: InvalidInitException) {
            Log.e(TAG, "Exception in init", e)
            e.printStackTrace()
        }
    }

    companion object {
        const val TAG = "DemoApp"
    }
}