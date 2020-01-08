package com.cjh.component_textview.span

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.style.ReplacementSpan

/**
 * @author: caijianhui
 * @date: 2019/10/10 11:41
 * @description:
 */
class RectSpan : ReplacementSpan() {

    private val mPaint: Paint
    private var mWidth = 0

    init {
        mPaint = Paint()
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.BLUE
        mPaint.isAntiAlias = true
    }

    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        //return text with relative to the Paint
        mWidth = paint.measureText(text, start, end).toInt()
        return mWidth
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        //draw the frame with custom Paint
        canvas.drawRect(x, top.toFloat(), x + mWidth, bottom.toFloat(), mPaint)
        canvas.drawText(text!!, start, end, x, y.toFloat(), paint)
    }
}