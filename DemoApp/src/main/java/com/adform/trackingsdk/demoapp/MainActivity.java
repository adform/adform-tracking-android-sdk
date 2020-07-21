package com.adform.trackingsdk.demoapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.adform.adformtrackingsdk.AdformTrackingSdk;
import com.adform.adformtrackingsdk.MultipleTrackPointsBuilder;
import com.adform.adformtrackingsdk.TrackPoint;
import com.adform.adformtrackingsdk.entities.Order;
import com.adform.adformtrackingsdk.entities.ProductItem;
import com.adform.trackingsdk.simpleapp.R;

import java.util.Arrays;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.view_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = new Order();
                order.setCurrency("currency");
                order.setOrderStatus("order status");
                order.setEmail("email");
                order.setOrderId("order id");

                // You can also set other custom variables.
                order.addCustomValue(1, "var1 value"); //Set index (1-10) of custom variable and value
                order.addSystemValue(5, "sv5 value"); //Set index (1-89) of system variable and value
                order.addNumericValue(3, 45.4); //Set index (1 or 2) of numeric system variable and value (numeric format)

                final TrackPoint trackPoint = new TrackPoint(Constants.TRACK_POINT_ID);
                trackPoint.setSectionName("valid_section_name");
                trackPoint.setOrder(order);

                AdformTrackingSdk.sendTrackPoint(trackPoint);   // To send prepared track point, just use sendTrackPoint
            }
        });

        findViewById(R.id.view_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order();
                order.setCurrency("currency");
                order.setOrderStatus("order status");
                order.setEmail("email");
                order.setFirstName("name");
                order.setLastName("last name");
                order.setAddress1("address 1");
                order.setAddress2("address 2");
                order.setPhone("phone");
                order.setBasketSize(7); //numeric format
                order.setZip("zip");
                order.setCountry("country");
                order.setAgeGroup("age group");
                order.setGender("gender");
                order.setOrderId("order id");
                order.setSale(44.54); //numeric format

                // You can also set other custom variables.
                order.addCustomValue(1, "var1 value"); //Set index (1-10) of custom variable and value
                order.addSystemValue(5, "sv5 value"); //Set index (1-89) of system variable and value
                order.addNumericValue(3, 45.4); //Set index (1 or 2) of numeric system variable and value (numeric format)

                //Set created order for the trackpoint
                final TrackPoint trackPoint = new TrackPoint(Constants.TRACK_POINT_ID);
                trackPoint.setSectionName("valid_section_name");    // Setting custom tracking point name:
                trackPoint.setAppName("custom application name");
                trackPoint.setOrder(order);

                AdformTrackingSdk.sendTrackPoint(trackPoint);   // To send prepared track point, just use sendTrackPoint
            }
        });

        findViewById(R.id.view_button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductItem productItem = new ProductItem();
                productItem.setProductId("Product ID");
                productItem.setProductName("Product name");
                productItem.setCategoryId("Category ID");
                productItem.setCategoryName("Category name");
                productItem.setProductCount(1);
                productItem.setProductSales(1D);
                productItem.setWeight(1);
                productItem.setStep(Byte.MIN_VALUE);
                productItem.setCustom("Custom information");

                TrackPoint trackPoint = new TrackPoint(Constants.TRACK_POINT_ID);
                trackPoint.setSectionName("valid_section_name");    // Setting custom tracking point name:
                trackPoint.addProductItem(productItem);
                AdformTrackingSdk.sendTrackPoint(trackPoint);   // To send prepared track point, just use sendTrackPoint
            }
        });

        findViewById(R.id.view_button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductItem productItem1 = new ProductItem();
                productItem1.setProductId("Product ID");
                productItem1.setProductName("Product name");
                productItem1.setCategoryId("Category ID");
                productItem1.setCategoryName("Category name");
                productItem1.setProductCount(1);
                productItem1.setProductSales(1D);
                productItem1.setWeight(1);
                productItem1.setStep(Byte.MIN_VALUE);
                productItem1.setCustom("Custom information");

                ProductItem productItem2 = new ProductItem();
                productItem2.setProductId("Product ID");
                productItem2.setProductName("Product name");
                productItem2.setCategoryId("Category ID");
                productItem2.setCategoryName("Category name");
                productItem2.setProductCount(1);
                productItem2.setProductSales(1D);
                productItem2.setWeight(1);
                productItem2.setStep(Byte.MIN_VALUE);
                productItem2.setCustom("Custom information");

                TrackPoint trackPoint = new TrackPoint(Constants.TRACK_POINT_ID);
                trackPoint.setSectionName("valid_section_name");    // Setting custom tracking point name:
                trackPoint.addProductItem(productItem1);
                trackPoint.addProductItem(productItem2);

                AdformTrackingSdk.sendTrackPoints(trackPoint);
            }
        });

        findViewById(R.id.view_button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Tracking_ID1 = 1;
                int Tracking_ID2 = 2;
                int Tracking_ID3 = 3;

                MultipleTrackPointsBuilder multipleTrackPointsBuilder = new MultipleTrackPointsBuilder();

                Order order = new Order();
                order.setCurrency("currency");
                order.setOrderStatus("order status");
                order.setEmail("email");
                order.setFirstName("name");
                order.setLastName("last name");
                order.setAddress1("address 1");
                order.setAddress2("address 2");
                order.setPhone("phone");
                order.setBasketSize(7); //numeric format
                order.setZip("zip");
                order.setCountry("country");
                order.setAgeGroup("age group");
                order.setGender("gender");
                order.setOrderId("order id");
                order.setSale(44.54); //numeric format

                multipleTrackPointsBuilder
                        .setAppName("App name")
                        .setSectionName("Section name")
                        .setOrder(order);

                AdformTrackingSdk.startTracking(getApplicationContext(), Tracking_ID1, Tracking_ID2, Tracking_ID3);
                TrackPoint[] trackPoints = multipleTrackPointsBuilder.generateTrackPoints(Tracking_ID1, Tracking_ID2, Tracking_ID3);

                AdformTrackingSdk.sendTrackPoints(trackPoints);
            }
        });
    }

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
}
