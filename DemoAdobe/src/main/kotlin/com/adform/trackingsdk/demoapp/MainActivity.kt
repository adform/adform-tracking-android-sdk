package com.adform.trackingsdk.demoapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.adform.adformtrackingsdk.TrackPoint
import com.adform.adformtrackingsdk.TrackPointBuilder
import com.adform.sdk.AdformAdobeBridge
import com.adform.sdk.AdformAdobeEventConverter
import com.adform.sdk.AdformAdobeExtension
import com.adform.trackingsdk.adobe.R
import com.adobe.marketing.mobile.MobileCore
import java.util.HashMap

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Launching already created TrackPoint through Adobe experience sdk
        findViewById<View>(R.id.view_button1).setOnClickListener {
            val tp = TrackPointBuilder
                .aTrackPoint()
                .withTrackPointId(AdformAdobeExtension.getTrackingId())
                .withAppName("DemoAdobe")
                .withSectionName("section1")
                .withParametersName("test1")
                .build()
            val eventMap = AdformAdobeEventConverter
                .toEventMap(tp)
            MobileCore.trackAction(tp.sectionName, eventMap)
            Log.d(DemoApp.TAG, "Button press " + Thread.currentThread())
        }
        // Launching new event through Adobe experience sdk
        findViewById<View>(R.id.view_button2).setOnClickListener {
            // Forming basic TrackPoint
            val trackPointId = AdformAdobeExtension.getTrackingId()
            val trackPointSection = "sectionName"
            val eventMap: MutableMap<String, String> = HashMap()
            eventMap[AdformAdobeBridge.TP_KEY_ID] = AdformAdobeExtension.getTrackingId()
            eventMap[AdformAdobeBridge.TP_KEY_APP_NAME] = "appName"
            eventMap[AdformAdobeBridge.TP_KEY_PARAMETERS_NAME] = "parametersName"

            // Adding order
            eventMap[AdformAdobeBridge.TP_ORDER_KEY_ORDER_ID] = "111"
            eventMap[AdformAdobeBridge.TP_ORDER_KEY_SALE] = "2.0"
            eventMap[AdformAdobeBridge.TP_ORDER_KEY_ADDRESS1] = "address1"
            eventMap[AdformAdobeBridge.TP_ORDER_KEY_ADDRESS2] = "address2"
            eventMap[AdformAdobeBridge.TP_ORDER_KEY_PHONE] = "phone_number"
            eventMap[AdformAdobeBridge.TP_ORDER_KEY_COUNTRY] = "en-gb"
            eventMap[AdformAdobeBridge.TP_ORDER_KEY_AGE_GROUP] = "12-24"
            eventMap[AdformAdobeBridge.TP_ORDER_KEY_GENDER] = "female"
            eventMap[AdformAdobeBridge.TP_ORDER_KEY_CURRENCY] = "eur"
            eventMap[AdformAdobeBridge.TP_ORDER_KEY_STATUS] = "status"
            eventMap[AdformAdobeBridge.TP_ORDER_KEY_BASKET_SIZE] = "5"

            // Adding product item
            eventMap[AdformAdobeBridge.TP_PRODUCT_KEY_CATEGORY_NAME] = "cat_name"
            eventMap[AdformAdobeBridge.TP_PRODUCT_KEY_CATEGORY_ID] = "111"
            eventMap[AdformAdobeBridge.TP_PRODUCT_KEY_PRODUCT_NAME] = "prod_name"
            eventMap[AdformAdobeBridge.TP_PRODUCT_KEY_PRODUCT_ID] = "111"
            eventMap[AdformAdobeBridge.TP_PRODUCT_KEY_WEIGHT] = "5"
            eventMap[AdformAdobeBridge.TP_PRODUCT_KEY_STEP] = "0"
            eventMap[AdformAdobeBridge.TP_PRODUCT_KEY_PRODUCT_SALES] = "prod_sale"
            eventMap[AdformAdobeBridge.TP_PRODUCT_KEY_PRODUCT_COUNT] = "5"
            eventMap[AdformAdobeBridge.TP_PRODUCT_KEY_CUSTOM] = "custom"
            MobileCore.trackAction(trackPointSection, eventMap)
        }
        findViewById<View>(R.id.view_button3).setOnClickListener {
            val tp = TrackPoint(java.lang.Long.valueOf(AdformAdobeExtension.getTrackingId()))
            tp.sectionName = "Tracking point name" // mandatory
            tp.appName = "custom application name" // optional
            val eventMap = AdformAdobeEventConverter
                .toEventMap(tp)

            // Sending events through Adobe extension
            MobileCore.trackAction(tp.sectionName, eventMap)
        }
        findViewById<View>(R.id.view_button4).setOnClickListener {
            val tp = TrackPoint(java.lang.Long.valueOf(AdformAdobeExtension.getTrackingId()))
            tp.sectionName = "Tracking point name" // mandatory
            tp.appName = "custom application name" // optional
            val eventMap = AdformAdobeEventConverter
                .toEventMap(tp)

            // Sending events through Adobe extension
            MobileCore.trackState(tp.sectionName, eventMap)
        }
    }

    override fun onResume() {
        super.onResume()
        MobileCore.setApplication(application)
        MobileCore.lifecycleStart(null)
    }

    override fun onPause() {
        super.onPause()
        MobileCore.lifecyclePause()
    }
}
