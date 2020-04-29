package com.cjh.component_xfermode.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.cjh.component_xfermode.R

/**
 * @author: caijianhui
 * @date: 2020/4/29 14:03
 * @description:
 */
class PingPongView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = -1
) : View(context, attrs, defStyle) {

    private lateinit var mPaint: Paint
    private var mItemWaveLength = 0
    private var dx = 0

    private lateinit var mPingPongBmg: Bitmap
    private lateinit var mSrcBmg: Bitmap

    private lateinit var mXfermode: Xfermode
    private lateinit var mSrcCanvas: Canvas


    init {
        initView(context)
    }

    private fun initView(context: Context) {
        mPaint = Paint()
        mPaint.apply {
            color = Color.RED

        }

        val opt = BitmapFactory.Options()
        opt.inSampleSize = 1/3 //宽高各放大3倍

        mPingPongBmg = BitmapFactory.decodeResource(resources, R.drawable.ping_pong, opt)
        mSrcBmg =
            Bitmap.createBitmap(mPingPongBmg.width, mPingPongBmg.height, Bitmap.Config.ARGB_8888)
        mXfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        mSrcCanvas = Canvas(mSrcBmg)
        mItemWaveLength = mPingPongBmg.width

        startAnim()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        canvas.translate(width/2f, 0f)

        //清空bitmap
        mSrcCanvas.drawColor(Color.RED, PorterDuff.Mode.CLEAR)
        //画上矩形
        mSrcCanvas.drawRect(
            mPingPongBmg.width.toFloat() - dx,
            0f,
            mPingPongBmg.width.toFloat(),
            mPingPongBmg.height.toFloat(),
            mPaint
        )
        //模式合成
        val layerId =
            canvas.saveLayer(0f, 0f,
                width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)

        //1.绘制dst
        canvas.drawBitmap(mPingPongBmg, 0f, 0f, mPaint)
        //2.设置叠加模式
        mPaint.xfermode = mXfermode
        //3.绘制src
        canvas.drawBitmap(mSrcBmg, 0f, 0f, mPaint)

        mPaint.xfermode = null

        canvas.restoreToCount(layerId)

        canvas.restore()

    }

    private fun startAnim() {
        val animator = ValueAnimator.ofInt(0, mItemWaveLength)
        animator.duration = 4500
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            dx = it.animatedValue as Int
            postInvalidate()
        }
        animator.start()
    }



}