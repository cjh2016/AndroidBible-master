package com.cjh.component_customview.analyze

import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * @author: caijianhui
 * @date: 2020/4/15 9:51
 * @description:
 */
object Utils {

    /**
     * 获得屏幕高度
     *
     * @param ctx 上下文
     * @param winSize 屏幕尺寸
     */
    fun loadWinSize(ctx: Context, winSize: Point) {
        val wm = ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm?.defaultDisplay?.getMetrics(outMetrics)
        winSize.x = outMetrics.widthPixels
        winSize.y = outMetrics.heightPixels
    }

}