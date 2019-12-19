package com.adform.trackingsdk.demoapp;

import android.app.Application;
import android.util.Log;

import com.adform.sdk.AdformAdobeExtension;
import com.adobe.marketing.mobile.AdobeCallback;
import com.adobe.marketing.mobile.InvalidInitException;
import com.adobe.marketing.mobile.Lifecycle;
import com.adobe.marketing.mobile.LoggingMode;
import com.adobe.marketing.mobile.MobileCore;
import com.adobe.marketing.mobile.Signal;

public class DemoApp extends Application {

    public static final String TAG = "DemoApp";

    @Override
    public void onCreate() {
        super.onCreate();
        MobileCore.setApplication(this);
        MobileCore.setLogLevel(LoggingMode.VERBOSE);
        try {
            Log.d(TAG, "Starting app registration");
            Lifecycle.registerExtension();
            Signal.registerExtension();
            AdformAdobeExtension.registerExtension();
            MobileCore.start(new AdobeCallback() {
                @Override
                public void call(Object o) {
                    Log.d(TAG, "Configuring MobileCore");
                    MobileCore.configureWithAppID("<APP_ID>");
                }
            });
        } catch (InvalidInitException e) {
            Log.e(TAG, "Exception in init", e);
            e.printStackTrace();
        }
    }
}
