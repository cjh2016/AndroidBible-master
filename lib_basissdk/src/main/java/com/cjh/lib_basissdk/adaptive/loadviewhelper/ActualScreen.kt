package com.boll.lib_coremodel.adaptation.loadviewhelper

import android.content.Context
import android.content.res.Configuration
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import com.cjh.lib_basissdk.util.BarUtils

/**
 * @author: caijianhui
 * @date: 2019/7/5 11:06
 * @description: 获取实际的屏幕参数，考虑到
 */
object ActualScreen {

    fun screenInfo(context: Context?): FloatArray {
        val displayMetrics = DisplayMetrics()
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return when(context.resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE, Configuration.ORIENTATION_UNDEFINED -> {
                Log.i("cjhhhh", "横屏")

                floatArrayOf(displayMetrics.widthPixels.toFloat(), (displayMetrics.heightPixels + BarUtils.getNavBarHeight()).toFloat(),
                        displayMetrics.density, displayMetrics.densityDpi.toFloat())
            }
            Configuration.ORIENTATION_PORTRAIT -> {
                Log.i("cjhhhh", "竖屏")

                floatArrayOf((displayMetrics.heightPixels + BarUtils.getNavBarHeight()).toFloat(), displayMetrics.widthPixels.toFloat(),
                        displayMetrics.density, displayMetrics.densityDpi.toFloat())
            }
            else -> {
                Log.i("cjhhhh", "默认")

                floatArrayOf(displayMetrics.widthPixels.toFloat(), (displayMetrics.heightPixels + BarUtils.getNavBarHeight()).toFloat(),
                        displayMetrics.density, displayMetrics.densityDpi.toFloat())
            }
        }
    }
}