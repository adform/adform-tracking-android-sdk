# Getting Started

When you run mobile campaigns, boost of new app installs are often one of the main goals. We are happy to announce that today we are launching app installs solution for iOS and Android devices. Easy to install SDK will enable campaign app installs tracking and reporting in Adform platform without need to deal with 3rd party SDKs and invest tons of time into that.

## 1. General Info

* Adform Tracking SDK runs on Android 2.3.* (API 10), so created project version should be 2.3.* (API 10) and above
* Also the instructions described here are done on IntelliJ 13.1. These instructions should be compatible with Android Studio also

![alt tag](screenshots/Screenshot 2014-10-03 12.24.14.png)

## 2. Setting up library dependencies

* To add a library to the dependencies, first we need to specify repository location. This can be done by editing `build.gradle` file and by inserting snippet (specified below) right above the `android` configuration group. 

	    ...
		repositories {
    		maven { url "https://github.com/adform/adform-tracking-android-sdk/raw/master/releases/" }
		}
        ...


* Then in the dependency group we need to specify that we will be using `AdformTrackingSdk`, and also add `Google Play Ads` service.
		
	    ...
        dependencies {
		    compile 'com.google.android.gms:play-services-ads:8.1.0'
    		compile 'com.adform.tracking.sdk:adform-tracking-sdk:0.2.5'
        }
        ...
        
* How to add Google Play Services to Your Project please follow these instructions: https://developer.android.com/google/play-services/setup.html#Setup

![alt tag](screenshots/Screenshot 2014-12-29 13.49.27.png)

## 3. Update AndroidManifest.xml


* Add internet reachability permissions. Update `AndroidManifest.xml` file with snippet shown below between `<manifest></manifest>` tags.

		<uses-permission android:name="android.permission.INTERNET" />
		<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
		    
* Also additional android services are needed to work properly. To add them, update `AndroidManifest.xml` with snipped shown below between `<application></application>` tags.

        <receiver
            android:name="com.adform.adformtrackingsdk.services.ReferrerReceiver"
            android:exported="true">
            <intent-filter>
                <action android:name="com.android.vending.INSTALL_REFERRER" />
            </intent-filter>
        </receiver>
        <meta-data
            android:name="com.google.android.gms.version"
            android:value="@integer/google_play_services_version"/>
            
![alt tag](screenshots/Screenshot 2014-12-19 17.45.33.png)

Now you are set to use sdk. 

# Basic integration
## Start tracking
		
To start tracking, you need to 	run `startTracking` method. Note that `[track id]` should be replaced with your tracking id.

	AdformTrackingSdk.startTracking(this, [track id]);
		
A good place to put it is Activity/Fragment onCreate() method. Alternatively this can also be done in Application class, as this method should be started only once and will not take any affect when running multiple times. 

Also, AdformTrackingSdk needs methods that would indicate of application activity, such as `onResume` and `onPause`. 

*Note that an old method* ***onStop was deprecated*** *and will not be used in the future, so it should be deleted if it was used before.*

    @Override
    protected void onResume() {
        super.onResume();
        AdformTrackingSdk.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AdformTrackingSdk.onPause();
    }
    

![alt tag](screenshots/Screenshot 2014-10-10 13.35.08.png)
    		
## Sending events    		
To create an event, first you need to create a TrackPoint with `[track id]`. Note that `startTracking` should occur before event sending.

	TrackPoint trackPoint = new TrackPoint([track id]);
	
Also some advanced integrations are available, like custom parameter or using custom application name setting. 

* Setting custom application name: 

		trackPoint.setAppName("custom application name");
		
* Adding custom parameters:
	
		Map<String, String> map = new HashMap();
		map.put("key", "value");
		trackPoint.setParameters(map);

* Adding section name:
	
		trackPoint.setSectionName("section name");
		
To send prepared track point, just use `sendTrackPoint`.

	AdformTrackingSdk.sendTrackPoint(trackPoint);

![alt tag](screenshots/Screenshot 2014-10-03 13.19.17.png)

# Custom Adform Tracking SDK implementations

## Setting custom application name
To set custom application name, use 

	AdformTrackingSdk.setAppName("CustomApp");
	
To set custom application name for track point, use 

	TrackPoint trackPoint = new TrackPoint(12345);
	trackPoint.setAppName("CustomAppName");
## Setting section name
To set section name for track point, use 
	
	TrackPoint trackPoint = new TrackPoint(12345);
	trackPoint.setSectionName("CustomSection");


## Setting custom events
To send custom parameters first you need to create them. 

    Map<String, String> map = new HashMap();
    map.put("var1", "Custom Value 1");
    map.put("var2", "Custom Value 2");
    map.put("var3", "Custom Value 3");
    
To send them instantly after starting track, use 

	AdformTrackingSdk.setParameters(map);
	
To send custom parameters with custom track point, use 

	TrackPoint trackPoint = new TrackPoint(12345);
	trackPoint.setParameters(map);
	
## Setting product variables
To send product variables you need to create ProductItem object and set your product values. Then add that object to the trackpoint.

	ProductItem productItem = new ProductItem();
    productItem.setProductName("your_product_name");
    productItem.setProductId("74");
    productItem.setCustom("custom_variable");
    ...
    
    TrackPoint trackPoint = new TrackPoint(12345);
    trackPoint.addProductItem(productItem);
    
## Add SIM card state stracking

To track SIM card state, please add the following code:

	AdformTrackingSdk.setSendSimCardStateEnabled(true);
    
## Enable/Disable tracking
You can enable/disable tracking by calling `setEnabled(boolean)` method.

	AdformTrackingSdk.setEnabled(true);


