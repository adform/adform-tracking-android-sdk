# Adform extension for Adobe experience SDK

Track marketing events for Adform platform using Adobe experience SDK. Tracking events are sent to Adobe and Adform platforms using this SDK.


## TLDR;

-   Steps how to integrate Adobe + Adform tracking libraries into the project
-   Sample project can be found here [Sample project](https://github.com/adform/adform-tracking-android-sdk/tree/master/sample-adobe-ext)


## Features

-   All events sent to Adobe platform will be routed to Adform as well
-   Easily convert Adform&rsquo;s `TrackPoint` to Adobe events


## Requirements

-   Adobe core libraries
-   Adform tracking sdk


## Set-up Adobe experience platform

-   Set-up Adobe experience patform
-   Install `Adform mobile extension` in Adobe experience cloud platform


## Set-up Android platform

-   Setup Adobe experience platform integration first by the rules provided on their platform. Or follow these instructions:
-   Add Adobe dependencies to `build.gradle`

```java
dependencies {
    implementation 'com.adobe.marketing.mobile:sdk-core:1.+'
}
```

-   Add `AdformTrackingSdk` and `AdformAdobeExtension` libraries to dependencies
-   You&rsquo;ll also need to provide repository link for the libraries to be reachable

```java
repositories {
    maven { url "https://github.com/adform/adform-tracking-android-sdk/raw/master/releases/" }
}
dependencies {
    implementation "com.adform.tracking.sdk:adform-tracking-sdk:2.0.2"
    implementation "com.adform.tracking.sdk:adform-tracking-adobe-ext:2.0.2"
}
```

-   Initialise Adobe and their extensions in main app `Application` class

```java
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

-   To send events you&rsquo;ll need to provide lifecycle events to Adobe experience SDK

```java
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

-   There are number of ways to send events through Adobe. If you already have Adform tracking SDK integrated, there are several convenient methods to form events and send them through Adobe.

```java
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

-   The easiest way to send existing `TrackPoint` is to use this method:

```java
final Map<String, String> eventMap = AdformAdobeEventConverter.toEventMap(trackPoint);
MobileCore.trackAction("action", eventMap);
```

-   If you don&rsquo;t have created events for Adform, there is more generic way of sending those events

```java
findViewById(R.id.view_button2).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // Launching new event through Adobe experience sdk
        // Forming basic TrackPoint
        final Map<String, String> eventMap = new HashMap<>();
        eventMap.put(AdformAdobeBridge.TP_KEY_ID, AdformAdobeExtension.getTrackingId());
        // Optionally you can define custom app name
        eventMap.put(AdformAdobeBridge.TP_KEY_APP_NAME, "appName");
        eventMap.put(AdformAdobeBridge.TP_KEY_PARAMETERS_NAME, "parametersName");

        // Optionally your can add order
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

        // Optionally your can add product item
        eventMap.put(AdformAdobeBridge.TP_PRODUCT_KEY_CATEGORY_NAME, "cat_name");
        eventMap.put(AdformAdobeBridge.TP_PRODUCT_KEY_CATEGORY_ID, "111");
        eventMap.put(AdformAdobeBridge.TP_PRODUCT_KEY_PRODUCT_NAME, "prod_name");
        eventMap.put(AdformAdobeBridge.TP_PRODUCT_KEY_PRODUCT_ID, "111");
        eventMap.put(AdformAdobeBridge.TP_PRODUCT_KEY_WEIGHT, "5");
        eventMap.put(AdformAdobeBridge.TP_PRODUCT_KEY_STEP, "0");
        eventMap.put(AdformAdobeBridge.TP_PRODUCT_KEY_PRODUCT_SALES, "prod_sale");
        eventMap.put(AdformAdobeBridge.TP_PRODUCT_KEY_PRODUCT_COUNT, "5");
        eventMap.put(AdformAdobeBridge.TP_PRODUCT_KEY_CUSTOM, "custom");

        // Sending the event with custom tracking point name
        MobileCore.trackAction("action", eventMap);
    }
});
```

-   You can always use Adobe&rsquo;s `.trackState()` as well, though in Adform tracking SDK it works exactly the same, as you would use `.trackAction()`.

```java
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
```


## Verifying

Once implementation is done successfully, tracked actions should be sent to Adform when triggered by using the App.

You can log in to Adform, go to Site Tracking of the account you have added the tracking ID of and see tracking points created based on the event names sent from the SDK. Adform tracking points can be found here [Track points in Adform](https://www.adform.com/TrackingSetup/#/trackingPoints)
