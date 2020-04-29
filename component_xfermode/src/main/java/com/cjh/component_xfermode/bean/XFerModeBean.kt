package com.cjh.component_xfermode.bean

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * @author: caijianhui
 * @date: 2020/4/28 16:45
 * @description:
 */
data class XFerModeBean(
    var context: Context,
    var name: String,
    var isSelect: Boolean,
    var dst: Int,
    var src: Int,
    var dstBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, dst),
    var srcBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, src)
) {

    fun recycle() {
        this.dstBitmap.recycle()
        this.srcBitmap.recycle()
    }
}