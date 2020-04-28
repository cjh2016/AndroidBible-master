package com.cjh.component_xfermode.bean

import android.graphics.Bitmap

/**
 * @author: caijianhui
 * @date: 2020/4/28 16:45
 * @description:
 */
data class XFerModeBean(
    val name: String,
    val isSelect: Boolean,
    val dstBitmap: Bitmap,
    val srcBitmap: Bitmap,
    val dst: Int,
    val src: Int
) {

    fun recycle() {
        this.dstBitmap.recycle()
        this.srcBitmap.recycle()
    }
}