package com.adform.trackingsdk.demoapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.adform.adformtrackingsdk.TrackPoint;
import com.adform.adformtrackingsdk.TrackPointBuilder;
import com.adform.sdk.AdformAdobeBridge;
import com.adform.sdk.AdformAdobeEventConverter;
import com.adform.sdk.AdformAdobeExtension;
import com.adform.trackingsdk.adobe.R;
import com.adobe.marketing.mobile.MobileCore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.view_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launching already created TrackPoint through Adobe experience sdk
                final TrackPoint tp = TrackPointBuilder
                        .aTrackPoint()
                        .withTrackPointId(AdformAdobeExtension.getTrackingId())
                        .withAppName("DemoAdobe")
                        .withSectionName("section1")
                        .withParametersName("test1")
                        .build();
                final Map<String, String> eventMap = AdformAdobeEventConverter
                        .toEventMap(tp);
                MobileCore.trackAction("action-tmp-3", eventMap);
                Log.d(DemoApp.TAG, "Button press " + Thread.currentThread());
            }
        });
        findViewById(R.id.view_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launching new event through Adobe experience sdk
                // Forming basic TrackPoint
                final Map<String, String> eventMap = new HashMap<>();
                eventMap.put(AdformAdobeBridge.TP_KEY_ID, AdformAdobeExtension.getTrackingId());
                eventMap.put(AdformAdobeBridge.TP_KEY_APP_NAME, "appName");
                eventMap.put(AdformAdobeBridge.TP_KEY_PARAMETERS_NAME, "parametersName");

                // Adding order
                eventMap.put(AdformAdobeBridge.TP_ORDER_KEY_ORDER_ID, "111");
                eventMap.put(AdformAdobeBridge.TP_ORDER_KEY_SALE, "2.0");
                eventMap.put(AdformAdobeBridge.TP_ORDER_KEY_ADDRESS1, "address1");
                eventMap.put(AdformAdobeBridge.TP_ORDER_KEY_ADDRESS2, "address2");
                eventMap.put(AdformAdobeBridge.TP_ORDER_KEY_PHONE, "phone_number");
                eventMap.put(AdformAdobeBridge.TP_ORDER_KEY_COUNTRY, "en-gb");
                eventMap.put(AdformAdobeBridge.TP_ORDER_KEY_AGE_GROUP, "12-24");
                eventMap.put(AdformAdobeBridge.TP_ORDER_KEY_GENDER, "female");
                eventMap.put(AdformAdobeBridge.TP_ORDER_KEY_CURRENCY, "eur");
                eventMap.put(AdformAdobeBridge.TP_ORDER_KEY_STATUS, "status");
                eventMap.put(AdformAdobeBridge.TP_ORDER_KEY_BASKET_SIZE, "5");

                // Adding product item
                eventMap.put(AdformAdobeBridge.TP_PRODUCT_KEY_CATEGORY_NAME, "cat_name");
                eventMap.put(AdformAdobeBridge.TP_PRODUCT_KEY_CATEGORY_ID, "111");
                eventMap.put(AdformAdobeBridge.TP_PRODUCT_KEY_PRODUCT_NAME, "prod_name");
                eventMap.put(AdformAdobeBridge.TP_PRODUCT_KEY_PRODUCT_ID, "111");
                eventMap.put(AdformAdobeBridge.TP_PRODUCT_KEY_WEIGHT, "5");
                eventMap.put(AdformAdobeBridge.TP_PRODUCT_KEY_STEP, "0");
                eventMap.put(AdformAdobeBridge.TP_PRODUCT_KEY_PRODUCT_SALES, "prod_sale");
                eventMap.put(AdformAdobeBridge.TP_PRODUCT_KEY_PRODUCT_COUNT, "5");
                eventMap.put(AdformAdobeBridge.TP_PRODUCT_KEY_CUSTOM, "custom");

                MobileCore.trackAction("action", eventMap);
            }
        });

        findViewById(R.id.view_button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mandatory
                TrackPoint tp = new TrackPoint(Long.valueOf(AdformAdobeExtension.getTrackingId()));
                tp.setSectionName("Tracking point name");   // mandatory
                tp.setAppName("custom application name");   // optional
                final Map<String, String> eventMap = AdformAdobeEventConverter
                        .toEventMap(tp);

                // Sending events through Adobe extension
                MobileCore.trackAction(tp.getSectionName(), eventMap);
            }
        });
        findViewById(R.id.view_button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mandatory
                TrackPoint tp = new TrackPoint(Long.valueOf(AdformAdobeExtension.getTrackingId()));
                tp.setSectionName("Tracking point name");   // mandatory
                tp.setAppName("custom application name");   // optional
                final Map<String, String> eventMap = AdformAdobeEventConverter
                        .toEventMap(tp);

                // Sending events through Adobe extension
                MobileCore.trackState(tp.getSectionName(), eventMap);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobileCore.setApplication(getApplication());
        MobileCore.lifecycleStart(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobileCore.lifecyclePause();
    }
}
