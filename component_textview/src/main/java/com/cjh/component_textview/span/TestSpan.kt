package com.cjh.component_textview.span

import android.graphics.Color
import android.os.Parcel
import android.text.TextPaint
import android.text.style.ForegroundColorSpan

/**
 * @author: caijianhui
 * @date: 2019/10/10 10:56
 * @description:
 */
class TestSpan : ForegroundColorSpan {

    var alpha = 255
    private var mForegroundColor: Int = 0

    constructor(alpha: Int, color: Int) : super(color) {
        this.alpha = alpha
        mForegroundColor = color
    }

    constructor(src: Parcel) : super(src) {
        mForegroundColor = src.readInt()
        alpha = src.readInt()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)
        dest.writeInt(mForegroundColor)
        dest.writeInt(alpha)
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.color = foregroundColor
    }

    fun setForegroundColor(foregroundColor: Int) {
        mForegroundColor = foregroundColor
    }

    override fun getForegroundColor(): Int {
        return Color.argb(alpha, Color.red(mForegroundColor),
            Color.green(mForegroundColor), Color.blue(mForegroundColor))
    }

}