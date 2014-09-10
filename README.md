# Getting Started

When you run mobile campaigns, boost of new app installs are often one of the main goals. We are happy to announce that today we are launching app installs solution for iOS and Android devices. Easy to install SDK will enable campaign app installs tracking and reporting in Adform platform without need to deal with 3rd party SDKs and invest tons of time into that.

## 1. General Info

* Adform Tracking SDK runs on Android 2.3.* (API 10), so created project version should be 2.3.* (API 10) and above
* Also the instructions described here are done on IntelliJ 13.1. These instructions should be compatible with Android Studio also

![alt tag](http://37.157.0.44/mobilesdk/help/tracking/android/image_01.png)

## 2. Copy the contents of the libs folder directly the libs/ folder of your project.

* Download project library `AdformTrackingSdk-0.1.9.jar` latest version.
* Insert library into your project

![alt tag](http://37.157.0.44/mobilesdk/help/tracking/android/image_02.png)

* Right click it and hit `Add as Library...`

![alt tag](http://37.157.0.44/mobilesdk/help/tracking/android/image_03.png)

## 3. Set up Google Play and Adform Tracking SDK

* Project has 2 `build.gradle` files (one for top project and one for project module). Update module `build.gradle` file by inserting `Google Play` services and `Adform Tracking SDK`
* How to add Google Play Services to Your Project please follow these instructions: https://developer.android.com/google/play-services/setup.html#Setup

![alt tag](http://37.157.0.44/mobilesdk/help/tracking/android/image_04.png)

````
apply plugin: 'com.android.application'

android {
	compileSdkVersion 20
	buildToolsVersion "20.0.0"
	
	defaultConfig {
		applicationId "adform.com.adformtrackingdemo"
		minSdkVersion 10
		targetSdkVersion 20
	    versionCode 1
	    versionName "1.0"
	}
    buildTypes {
        release {
            runProguard false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:20.0.0'
    compile files('libs/AdformTrackingSdk-0.1.9.jar')
    compile 'com.google.android.gms:play-services:5.0.89'
}
````

## 4. Update AndroidManifest.xml

* Update AndroidManifest.xml with snippet shown below between <manifest></manifest> tags

![alt tag](http://37.157.0.44/mobilesdk/help/tracking/android/image_05.png)

````
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
````

* Update AndroidManifest.xml with snipped shown below between <application></application> tags

````
<service
	android:enabled="true"
    	android:name="com.adform.adformtrackingsdk.services.TrackingService"
	android:process=":TrackingService" />

<provider
	android:name="com.adform.adformtrackingsdk.database.AdformContentProvider"
	android:authorities="@string/adform_content_provider_authorities"
	android:process=":TrackingService" />

<receiver
	android:name="com.adform.adformtrackingsdk.services.ReferrerReceiver"
	android:exported="true">
    	<intent-filter>
		<action android:name="com.android.vending.INSTALL_REFERRER" />
	</intent-filter>
</receiver>

<meta-data android:name="com.google.android.gms.version" 
android:value="@integer/google_play_services_version" />
````

* Add Content Provider autorities in strings.xml parameter should be unique per appstore so add your app name at the end

![alt tag](http://37.157.0.44/mobilesdk/help/tracking/android/image_06.png)

````
<string name="adform_content_provider_authorities">com.adform.AdformContentProvider.<appname></string>
````

## 5. Basic Adform Tracking SDK implementation

![alt tag](http://37.157.0.44/mobilesdk/help/tracking/android/image_07.png)

* In your main activity add the following lines of code

````
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	Tracking.startTracking(getApplicationContext(), 123456);
	setContentView(R.layout.activity_my);
}

@Override
protected void onStop() {
	Tracking.onStop();
	super.onStop();
}
````

Thats it! You are ready to go.

## 5. Custom Adform Tracking SDK implementations

* Sending tracking events manualy

![alt tag](http://37.157.0.44/mobilesdk/help/tracking/android/image_08.png)

````
public void onClick(View v) {
	TrackingPoint trackingPoint = new TrackingPoint(123456);

	trackingPoint.setSectionName("Custom Section Name");

	Map<String, String> map = new HashMap();

	map.put("var1", "Custom Variable 1");
	map.put("var2", "Custom Variable 2");
	map.put("var3", "Custom Variable 3");

	trackingPoint.setCustomParameters(map);

	Tracking.sendTrackingEvent(trackingPoint);
}
````

* Setting custom app name for the app

![alt tag](http://37.157.0.44/mobilesdk/help/tracking/android/image_09.png)

````
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	Tracking.startTracking(getApplicationContext(), 123456);

	setContentView(R.layout.activity_my);
}
````
