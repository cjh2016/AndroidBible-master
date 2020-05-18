package com.cjh.component_textview.span

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ReplacementSpan
import android.util.Log
import android.util.Property
import com.cjh.component_textview.R
import java.util.Random
import kotlin.math.sqrt

/**
 * @author: caijianhui
 * @date: 2019/10/10 12:29
 * @description:
 */
class SparkSpan(context: Context) : ReplacementSpan() {

    private var mWidth: Int = 0
    private val mBitmap: Bitmap
    private val mPaint: Paint
    private var mUpDistance: Float = 0f
    var percent: Float = 0f

    init {
        mPaint = Paint()
        mBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.sparkle)
    }

    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
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
        Log.e("test","the size is start $start end: $end x: $x top: $top y: $y bottom: $bottom")
        mUpDistance = bottom.toFloat()
        for (i in start until end) {
            drawSparkle(canvas, x, top + percent * bottom, mWidth.toFloat())
        }
        canvas.drawText(text!!, start, end, x, y.toFloat(), paint)
    }

    private fun drawSparkle(canvas: Canvas, offset: Float, startY: Float, width: Float) {
        val random = Random()
        for (i in 0 until 8) {
            canvas.drawBitmap(getRandomSpark(random), (offset + random.nextDouble() * width).toFloat(),
                (startY - random.nextGaussian() * sqrt(mUpDistance)).toFloat(), mPaint)
        }
    }

    private fun getRandomSpark(random: Random): Bitmap {
        val dstWidth = random.nextInt(12) + 1
        return Bitmap.createScaledBitmap(mBitmap, dstWidth, dstWidth, false)
    }

    companion object {

        val SPARK_SPAN_FLOAT_PROPERTY = object : Property<SparkSpan, Float>(Float::class.java, "SPARK_SPAN_FLOAT_PROPERTY") {

            override fun get(sparkSpan: SparkSpan?): Float? {
                return sparkSpan?.percent
            }

            override fun set(sparkSpan: SparkSpan?, value: Float) {
                sparkSpan?.percent = value
            }

        }
    }


}