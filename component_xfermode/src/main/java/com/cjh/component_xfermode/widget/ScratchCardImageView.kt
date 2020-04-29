package com.cjh.component_xfermode.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView

/**
 * @author: caijianhui
 * @date: 2020/4/29 15:28
 * @description:
 */
class ScratchCardImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = -1

) : AppCompatImageView(context, attrs, defStyle) {


    companion object {
        const val CARD_COLOR = "#A9A9A9"
        const val PERCENT = 0.2f
    }

    private lateinit var mPaint: Paint



    init {
        initView(context)
    }

    private fun initView(context: Context) {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.apply {
            color = Color.parseColor(CARD_COLOR)
            style = Paint.Style.STROKE
            strokeWidth = 10f

        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}