package com.adform.trackingsdk.demoapp;

import android.app.Application;

import com.adform.adformtrackingsdk.AdformTrackingSdk;

public class DemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AdformTrackingSdk.startTracking(this, Constants.TRACK_POINT_ID);
        AdformTrackingSdk.setDebugModeEnabled(true);
    }
}
