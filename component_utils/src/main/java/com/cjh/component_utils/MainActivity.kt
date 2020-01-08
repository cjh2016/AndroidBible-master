package com.cjh.component_utils

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.cjh.lib_basissdk.util.BatteryUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val batteryInfo = BatteryUtils.getBatteryInfo(this)
    }
}
