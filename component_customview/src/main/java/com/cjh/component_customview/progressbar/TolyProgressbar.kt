package com.cjh.component_customview.progressbar

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * @author: caijianhui
 * @date: 2020/4/22 10:42
 * @description:
 */
class TolyProgressbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = -1)
    :View(context, attrs, defStyle) {

    //外部圆环画笔
    lateinit var outerCirclePaint: Paint
    //内部圆环画笔
    lateinit var innerCirclePaint: Paint
    //中间数字画笔
    lateinit var middleNumberPaint: Paint

    lateinit var mCoo: PointF

    var percent: Int = 0
        set(value) {
            field = value
            invalidate()
        }


    init {
        outerCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        outerCirclePaint.apply {
            style = Paint.Style.STROKE
        }

        innerCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        innerCirclePaint.apply {
            style = Paint.Style.STROKE
        }

        middleNumberPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        middleNumberPaint.apply {
            style = Paint.Style.FILL
            textSize = 30f
            color = Color.parseColor("#605A58")
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()

        canvas.translate(mCoo.x, mCoo.y)

        //绘制外部圆环
        drawOuterCircle(canvas)

        //绘制内部圆环
        drawInnerCircle(canvas)

        //绘制中间数字
        drawPercentageText(canvas)

        canvas.restore()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCoo = PointF(width / 2f, height / 2f)
    }

    private fun drawPercentageText(canvas: Canvas) {
        canvas.save()
        canvas.drawText("${percent}%",-10f, 10f, middleNumberPaint)
        canvas.restore()
    }

    /**
     * 绘制内部的点圆环
     */
    private fun drawInnerCircle(canvas: Canvas) {

        for (i in 0..40) {
            //绘制底下点圆环
            innerCirclePaint.apply {
                color = Color.parseColor("#D2D2D4")
                strokeWidth = 8f
                strokeCap = Paint.Cap.ROUND
            }

            canvas.save()
            canvas.rotate(9f * i)
            canvas.drawLine(200f, 0f, 220f, 0f, innerCirclePaint)
            canvas.restore()
        }

        for (i in 0..percent*40/100) {
            //绘制上面点圆环
            innerCirclePaint.apply {
                color = Color.parseColor("#56F643")
                strokeWidth = 8f
                strokeCap = Paint.Cap.ROUND
            }

            if (0 == i) {
                continue
            }
            canvas.save()
            canvas.rotate(90f + 9f * i)
            canvas.drawLine(200f, 0f, 220f, 0f, innerCirclePaint)
            canvas.restore()
        }

    }

    /**
     * 绘制外部圆环
     */
    private fun drawOuterCircle(canvas: Canvas) {
        canvas.save()

        //绘制底下圆环
        outerCirclePaint.apply {
            color = Color.parseColor("#CACACC")
            strokeWidth = 10f
        }

        canvas.drawCircle(0f, 0f, 250f, outerCirclePaint)

        //绘制上面圆弧
        outerCirclePaint.apply {
            color = Color.parseColor("#52F23C")
            strokeWidth = 15f
            strokeCap = Paint.Cap.ROUND

        }

        canvas.drawArc(RectF(-250f, -250f, 250f, 250f),
            -90f, percent*360/100f, false, outerCirclePaint)

        canvas.restore()
    }


}