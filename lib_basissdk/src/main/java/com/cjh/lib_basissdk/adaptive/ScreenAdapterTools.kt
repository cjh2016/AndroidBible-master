package com.boll.lib_coremodel.adaptation

import android.content.Context
import com.boll.lib_coremodel.adaptation.loadviewhelper.AbsLoadViewHelper
import com.boll.lib_coremodel.adaptation.loadviewhelper.BothLoadViewHelper

/**
 * @author: caijianhui
 * @date: 2019/7/5 13:56
 * @description: 适配对外提供的工具类
 */
object ScreenAdapterTools {

    var sLoadViewHelper: AbsLoadViewHelper? = null

    var isInit: Boolean = false

    fun getInstance(): AbsLoadViewHelper? {
        return sLoadViewHelper
    }

    @JvmOverloads
    fun init(context: Context, provider: IProvider = object : IProvider {
        override fun provide(context: Context, designWidth: Int, designHeight: Int, designDpi: Int, fontSize: Float, unit: String): AbsLoadViewHelper {
            return BothLoadViewHelper(context, designWidth, designHeight, designDpi, fontSize, unit)
        }
    }) {
        /*var applicationInfo: ApplicationInfo
        try {
            applicationInfo = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }*/

        val designwidth = 1920
        val designheight = 1200
        val designdpi = 240
        val fontsize = 1.5f
        val unit = "dp"
        sLoadViewHelper = provider.provide(context, designwidth, designheight, designdpi, fontsize, unit)
        isInit = true
    }

    interface IProvider {
        fun provide(context: Context, designWidth: Int, designHeight: Int, designDpi: Int,
                    fontSize: Float, unit: String): AbsLoadViewHelper
    }

    fun release() {
        sLoadViewHelper = null
    }
}