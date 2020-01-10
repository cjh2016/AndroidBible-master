package com.cjh.component_utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cjh.lib_basissdk.util.BatteryUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val batteryInfo = BatteryUtils.getBatteryInfo(this)
    }
}
