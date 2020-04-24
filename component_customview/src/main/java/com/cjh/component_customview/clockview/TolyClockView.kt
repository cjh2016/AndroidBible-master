package com.cjh.component_customview.clockview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.io.File
import kotlin.random.Random

/**
 * @author: caijianhui
 * @date: 2020/4/20 17:18
 * @description:
 */
class TolyClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = -1): View(context, attrs, -1) {

    //圆弧画笔
    lateinit var outerArcPaint: Paint

    //罗马数字画笔
    lateinit var romanNumeralPaint: Paint

    //时间刻度短线画笔
    lateinit var timeScaleShortPaint: Paint

    //时间刻度长线画笔
    lateinit var timeScaleLongPaint: Paint

    //时间刻度长线点画笔
    lateinit var timeScaleLongPointPaint: Paint

    //文字标识
    lateinit var logoPaint: Paint

    //时针
    lateinit var hourHandPaint: Paint

    //分针
    lateinit var minuteHandPaint: Paint

    //秒针
    lateinit var secondHandPaint: Paint

    //原点
    lateinit var center: PointF

    //半径
    var radius = 250f

    var secondHandAngle = 0f

    var timeScaleLongPaintColor = Color.RED

    var lastAnimatorValue = 0

    val radomColors = intArrayOf(
        Color.parseColor("#F60C0C"),  //红
        Color.parseColor("#F3B913"),  //橙
        Color.parseColor("#E7F716"),  //黄
        Color.parseColor("#3DF30B"),  //绿
        Color.parseColor("#0DF6EF"),  //青
        Color.parseColor("#0829FB"),  //蓝
        Color.parseColor("#B709F4")   //紫
    )


    init {
        init()
    }

    private fun init() {

        outerArcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        outerArcPaint.apply {
            color = Color.parseColor("#FFD6D6D4")
            strokeWidth = 5f
            style = Paint.Style.STROKE
        }

        romanNumeralPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        romanNumeralPaint.apply {
            color = Color.parseColor("#FF0407FA")
            strokeWidth = 5f
            style = Paint.Style.FILL
            textSize = 40f

        }

        timeScaleShortPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        timeScaleShortPaint.apply {
            color = Color.parseColor("#FF0407FA")
            strokeWidth = 3f
            style = Paint.Style.STROKE
        }

        timeScaleLongPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        timeScaleLongPaint.apply {
            strokeWidth = 5f
            style = Paint.Style.FILL
            color = timeScaleLongPaintColor

        }

        timeScaleLongPointPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        timeScaleLongPointPaint.apply {
            color = Color.BLACK
            strokeWidth = 2f
            style = Paint.Style.FILL
        }


        logoPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        logoPaint.apply {
            color = Color.parseColor("#FF0407FA")
            strokeWidth = 5f
            style = Paint.Style.FILL
            textSize = 70f

        }

        hourHandPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        hourHandPaint.apply {
            color = Color.parseColor("#FF87C051")
            strokeWidth = 5f
            style = Paint.Style.FILL
            strokeCap = Paint.Cap.ROUND

        }

        minuteHandPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        minuteHandPaint.apply {
            color = Color.parseColor("#FF8FC84E")
            strokeWidth = 5f
            style = Paint.Style.FILL
            strokeCap = Paint.Cap.ROUND

        }

        secondHandPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        secondHandPaint.apply {
            color = Color.parseColor("#FF202720")
            strokeWidth = 8f
            style = Paint.Style.STROKE
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        center = PointF(width / 2f, height / 2f)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        canvas.translate(center.x, center.y)

        //画外圆四个弧线
        drawOuterArc(canvas)

        //画外圆四个数字
        drawRomanNumerals(canvas)

        //画中间时间刻度短线
        drawTimeScaleShort(canvas)

        //画中间时间刻度会变色的长线
        drawTimeScaleLong(canvas)

        //画内部表的文字标识
        drawLogo(canvas)

        //画原点时针
        drawHourHand(canvas)

        //画原点分针
        drawMinuteHand(canvas)

        //画原点秒针
        drawSecondHand(canvas)

        canvas.restore()

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startClockView()
    }

    private fun startClockView() {
        val clockViewAnimator = ValueAnimator.ofInt(0, 20)
        clockViewAnimator.addUpdateListener {
            val value = it.animatedValue
            if (lastAnimatorValue != value) {
                secondHandAngle = 6f * (value as Int)
                invalidate()
            }
            lastAnimatorValue = value
        }
        clockViewAnimator.duration = 20000
        clockViewAnimator.start()
    }

    /**
     * 画秒针
     */
    private fun drawSecondHand(canvas: Canvas) {
        canvas.save()
        canvas.rotate(secondHandAngle)
        //画小圆
        secondHandPaint.style = Paint.Style.STROKE
        canvas.drawCircle(0f,0f, 12f, secondHandPaint)

        //画圆弧
        canvas.drawArc(RectF(-25f, -25f, 25f, 25f), -30f, 240f, false, secondHandPaint)

        //画一横
        secondHandPaint.strokeCap = Paint.Cap.ROUND
        canvas.drawLine(0f, 25f, 0f, 60f, secondHandPaint)

        //画秒针
        secondHandPaint.strokeWidth = 1f
        canvas.drawLine(0f, -12f, 0f, -radius + 20f, secondHandPaint)

        canvas.restore()
    }

    /**
     * 画分针
     */
    private fun drawMinuteHand(canvas: Canvas) {
        canvas.save()
        canvas.drawLine(0f, 0f, 0f, radius - 120f, minuteHandPaint)

        canvas.restore()
    }

    /**
     * 画时针
     */
    private fun drawHourHand(canvas: Canvas) {
        canvas.save()
        canvas.drawLine(0f, 0f, radius - 150, 0f, hourHandPaint)

        canvas.restore()
    }

    /**
     * 画中间logo
     */
    private fun drawLogo(canvas: Canvas) {
        canvas.save()
        canvas.drawText("CJH", -70f, -120f, logoPaint)
        canvas.restore()
    }

    /**
     * 画变色长刻度
     */
    private fun drawTimeScaleLong(canvas: Canvas) {
        canvas.save()
        for (i in 0..360 step 30) {
            timeScaleLongPaint.color = getRandomColor()
            canvas.drawLine(radius - 90f, 0f, radius - 30f, 0f, timeScaleLongPaint)
            canvas.drawPoint(radius - 90f, 0f, timeScaleLongPointPaint)
            canvas.rotate(30f)
        }
        canvas.restore()
    }

    /**
     * 获取随机颜色
     */
    private fun getRandomColor(): Int {
        return radomColors[Random.nextInt(radomColors.size)]
    }

    /**
     * 画短刻度
     */
    private fun drawTimeScaleShort(canvas: Canvas) {
        canvas.save()
        //canvas.translate(radius - 60, 0f)
        for (i in 0..360 step 6) {
            if (i == 0) {
                continue
            }
            canvas.rotate(6f)
            if (i % 30 == 0) {
                continue
            }
            canvas.drawLine(radius - 60f, 0f, radius - 30f, 0f, timeScaleShortPaint)
        }
        canvas.restore()
    }

    /**
     * 画四个罗马数字
     */
    private fun drawRomanNumerals(canvas: Canvas) {

        canvas.save()

        //上面罗马数字
        canvas.translate(0f, -radius)
        canvas.drawText("XII", -20f, 20f, romanNumeralPaint)

        //左边罗马数字
        canvas.translate(radius, radius)
        canvas.drawText("III", -20f, 20f, romanNumeralPaint)

        //下面罗马数字
        canvas.translate(-radius, radius)
        canvas.drawText("VI", -20f, 20f, romanNumeralPaint)

        //右边罗马数字
        canvas.translate(-radius, -radius)
        canvas.drawText("IX", -20f, 20f, romanNumeralPaint)

        canvas.restore()

    }

    /**
     * 画最外边的四个圆弧
     */
    private fun drawOuterArc(canvas: Canvas) {
        val rectF = RectF(-radius, -radius, radius, radius)

        canvas.save()

        //绘制左上圆弧
        canvas.rotate(-170f)
        canvas.drawArc(rectF, 0f, 70f, false, outerArcPaint)

        //绘制左下圆弧
        canvas.rotate(-90f)
        canvas.drawArc(rectF, 0f, 70f, false, outerArcPaint)

        //绘制右下圆弧
        canvas.rotate(-90f)
        canvas.drawArc(rectF, 0f, 70f, false, outerArcPaint)

        //绘制右上圆弧
        canvas.rotate(-90f)
        canvas.drawArc(rectF, 0f, 70f, false, outerArcPaint)


        canvas.restore()

    }




}