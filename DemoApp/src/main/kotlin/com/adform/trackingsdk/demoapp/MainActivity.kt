package com.adform.trackingsdk.demoapp

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.adform.adformtrackingsdk.AdformTrackingSdk
import com.adform.adformtrackingsdk.MultipleTrackPointsBuilder
import com.adform.adformtrackingsdk.TrackPoint
import com.adform.adformtrackingsdk.entities.Order
import com.adform.adformtrackingsdk.entities.ProductItem
import com.adform.trackingsdk.simpleapp.R

class MainActivity : Activity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // To send prepared track point, just use sendTrackPoint
        findViewById<View>(R.id.view_button0).setOnClickListener {
            val order = Order()
            order.currency = "currency"
            val trackPoint = TrackPoint(Constants.TRACK_POINT_ID.toLong())
            trackPoint.sectionName = "valid_section_name"
            trackPoint.order = order
            AdformTrackingSdk.sendTrackPoint(trackPoint)
        }
        findViewById<View>(R.id.view_button1).setOnClickListener {
            val order = Order()
            order.currency = "currency"
            order.orderStatus = "order status"
            order.email = "email"
            order.orderId = "order id"

            // You can also set other custom variables.
            // Set index (1-10) of custom variable and value
            order.addCustomValue(1, "var1 value")
            // Set index (1-89) of system variable and value
            order.addSystemValue(5, "sv5 value")
            // Set index (1 or 2) of numeric system variable and value (numeric format)
            order.addNumericValue(
                3,
                45.4
            )
            val trackPoint = TrackPoint(Constants.TRACK_POINT_ID.toLong())
            trackPoint.sectionName = "valid_section_name"
            trackPoint.order = order
            // To send prepared track point, just use sendTrackPoint
            AdformTrackingSdk.sendTrackPoint(trackPoint)
        }
        findViewById<View>(R.id.view_button2).setOnClickListener {
            val order = Order()
            order.currency = "currency"
            order.orderStatus = "order status"
            order.email = "email"
            order.firstName = "name"
            order.lastName = "last name"
            order.address1 = "address 1"
            order.address2 = "address 2"
            order.phone = "phone"
            order.basketSize = 7 // numeric format
            order.zip = "zip"
            order.country = "country"
            order.ageGroup = "age group"
            order.gender = "gender"
            order.orderId = "order id"
            order.sale = 44.54 // numeric format

            // You can also set other custom variables.
            // Set index (1-10) of custom variable and value
            order.addCustomValue(1, "var1 value")
            // Set index (1-89) of system variable and value
            order.addSystemValue(5, "sv5 value")

            // Set index (1 or 2) of numeric system variable and value (numeric format)
            order.addNumericValue(
                3,
                45.4
            )

            // Set created order for the trackpoint
            val trackPoint = TrackPoint(Constants.TRACK_POINT_ID.toLong())
            trackPoint.sectionName = "valid_section_name" // Setting custom tracking point name:
            trackPoint.appName = "custom application name"
            trackPoint.order = order
            AdformTrackingSdk.sendTrackPoint(trackPoint) // To send prepared track point, just use sendTrackPoint
        }
        findViewById<View>(R.id.view_button3).setOnClickListener {
            val productItem = ProductItem()
            productItem.productId = "Product ID"
            productItem.productName = "Product name"
            productItem.categoryId = "Category ID"
            productItem.categoryName = "Category name"
            productItem.productCount = 1
            productItem.productSales = 1.0
            productItem.weight = 1
            productItem.step = Byte.MIN_VALUE
            productItem.custom = "Custom information"
            val trackPoint = TrackPoint(Constants.TRACK_POINT_ID.toLong())
            // Setting custom tracking point name:
            trackPoint.sectionName = "valid_section_name"
            trackPoint.addProductItem(productItem)
            // To send prepared track point, just use sendTrackPoint
            AdformTrackingSdk.sendTrackPoint(trackPoint)
        }
        findViewById<View>(R.id.view_button4).setOnClickListener {
            val productItem1 = ProductItem()
            productItem1.productId = "Product ID"
            productItem1.productName = "Product name"
            productItem1.categoryId = "Category ID"
            productItem1.categoryName = "Category name"
            productItem1.productCount = 1
            productItem1.productSales = 1.0
            productItem1.weight = 1
            productItem1.step = Byte.MIN_VALUE
            productItem1.custom = "Custom information"
            val productItem2 = ProductItem()
            productItem2.productId = "Product ID"
            productItem2.productName = "Product name"
            productItem2.categoryId = "Category ID"
            productItem2.categoryName = "Category name"
            productItem2.productCount = 1
            productItem2.productSales = 1.0
            productItem2.weight = 1
            productItem2.step = Byte.MIN_VALUE
            productItem2.custom = "Custom information"
            val trackPoint = TrackPoint(Constants.TRACK_POINT_ID.toLong())
            // Setting custom tracking point name:
            trackPoint.sectionName = "valid_section_name"
            trackPoint.addProductItem(productItem1)
            trackPoint.addProductItem(productItem2)
            AdformTrackingSdk.sendTrackPoints(trackPoint)
        }
        findViewById<View>(R.id.view_button5).setOnClickListener {
            val Tracking_ID1 = 1
            val Tracking_ID2 = 2
            val Tracking_ID3 = 3
            val multipleTrackPointsBuilder = MultipleTrackPointsBuilder()
            val order = Order()
            order.currency = "currency"
            order.orderStatus = "order status"
            order.email = "email"
            order.firstName = "name"
            order.lastName = "last name"
            order.address1 = "address 1"
            order.address2 = "address 2"
            order.phone = "phone"
            order.basketSize = 7 // numeric format
            order.zip = "zip"
            order.country = "country"
            order.ageGroup = "age group"
            order.gender = "gender"
            order.orderId = "order id"
            order.sale = 44.54 // numeric format
            multipleTrackPointsBuilder
                .setAppName("App name")
                .setSectionName("Section name")
                .setOrder(order)
            AdformTrackingSdk.startTracking(
                applicationContext,
                Tracking_ID1,
                Tracking_ID2,
                Tracking_ID3
            )
            val trackPoints = multipleTrackPointsBuilder.generateTrackPoints(
                Tracking_ID1.toLong(),
                Tracking_ID2.toLong(),
                Tracking_ID3.toLong()
            )
            AdformTrackingSdk.sendTrackPoints(*trackPoints)
        }
    }

    override fun onResume() {
        super.onResume()
        AdformTrackingSdk.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        AdformTrackingSdk.onPause()
    }
}
