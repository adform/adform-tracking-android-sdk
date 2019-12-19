# Adform extension for Adobe experience SDK
Track marketing events for Adform platform using Adobe experience SDK

## TLDR;
- Steps how to integrate Adobe + Adform tracking libraries into the project
- Sample project can be found here https://github.com/adform/adform-tracking-android-sdk/tree/master/sample-adobe-ext

## Features
- All events sent to Adobe platform will be routed to Adform as well
- Easily convert Adform's `TrackPoint` to Adobe events

## Requirements
- Adobe core libraries
- Adform tracking sdk

## Set-up Adobe experience platform
- Set-up Adobe experience patform
- Install `Adform mobile extension` in Adobe experience cloud platform

## Set-up Android platform
- Adobe experience platform integration first by the rules provided on their platform. Or follow these directions: 

- Add Adobe dependencies to `build.gradle`

```
dependencies {
    implementation 'com.adobe.marketing.mobile:sdk-core:1.+'
}
```

- Add `AdformTrackingSdk` and `AdformAdobeExtension` libraries to dependencies
- You'll also need to provide repository link for the libraries to be reachable
  
```
repositories {
    maven { url "https://github.com/adform/adform-tracking-android-sdk/raw/master/releases/" }
}
dependencies {
    implementation "com.adform.tracking.sdk:adform-tracking-sdk:1.3.3"
    implementation "com.adform.tracking.sdk:adform-tracking-adobe-ext:1.3.3"
}
```

- Initialize Adobe and their extensions in main app `Application` class

```
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
                    MobileCore.configureWithAppID("<APP_ID>");
                }
            });
        } catch (InvalidInitException e) {
            Log.e(TAG, "Exception in init", e);
            e.printStackTrace();
        }
    }
}
```

- To send events you'll need to provide lifecycle events to Adobe experience SDK

```
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
```

- Number of ways to send events through Adobe. If you already have Adform integrated, there are convenience methods to form events to send them through Adobe

```
        findViewById(R.id.view_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
```

- Again, the easiest way to send existing `TrackPoint` is to use this convenience method: 

```
final Map<String, String> eventMap = AdformAdobeEventConverter.toEventMap(trackPoint);
MobileCore.trackAction("action", eventMap);
```

- If you don't have created events for Adform, there is more generic way of sending those events

```
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
    }
```

## Verifying
Once implementation is done successfully, tracked actions should be sent to Adform when triggered by using the App.

You can log in to Adform, go to Site Tracking of the account you have added the tracking ID of and see tracking points created based on the event names sent from the SDK. Adform tracking points can be found here https://www.adform.com/TrackingSetup/#/trackingPoints
