package com.cjh.lib_basissdk.util

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import java.lang.UnsupportedOperationException

/**
 * @author: caijianhui
 * @date: 2019/8/7 15:15
 * @description:
 */
class BatteryUtils private constructor() {

    init {
        throw UnsupportedOperationException("you can't instantiate me...")
    }

    class BatteryInfo(val level: Int,            //电池剩余电量
                      val scale: Int,            //获取电池满电量数值
                      val technology: String,    //获取电池技术支持
                      val status: Int,           //获取电池状态
                      val plugged: Int,          //获取电源信息
                      val health: Int,           //获取电池健康度
                      val voltage: Int,          //获取电池电压
                      val temperature: Int       //获取电池温度
    ) {

        val percent: Int = level * 100 / scale

    }

    companion object {
        fun getBatteryInfo(context: Context?): BatteryInfo? {
            context?.run {
                val batteryInfoIntent = applicationContext.registerReceiver(null,
                    IntentFilter(Intent.ACTION_BATTERY_CHANGED))
                val level = batteryInfoIntent.getIntExtra("level",0)
                val scale = batteryInfoIntent.getIntExtra("scale", 100)
                val technology = batteryInfoIntent.getStringExtra("technology")
                val status = batteryInfoIntent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN)
                val plugged = batteryInfoIntent.getIntExtra("plugged", 0)
                val health = batteryInfoIntent.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN)
                val voltage = batteryInfoIntent.getIntExtra("voltage", 0)
                val temperature = batteryInfoIntent.getIntExtra("temperature", 0)
                return BatteryInfo(level, scale, technology, status, plugged, health, voltage, temperature)
            }
            return null
        }
    }

}