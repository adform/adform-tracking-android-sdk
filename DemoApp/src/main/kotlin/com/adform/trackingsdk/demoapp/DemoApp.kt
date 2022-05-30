package com.adform.trackingsdk.demoapp

import android.app.Application
import com.adform.adformtrackingsdk.AdformTrackingSdk

class DemoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AdformTrackingSdk.startTracking(this, Constants.TRACK_POINT_ID)
        AdformTrackingSdk.setDebugModeEnabled(true)
    }
}