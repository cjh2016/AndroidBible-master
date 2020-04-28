package com.cjh.component_xfermode.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.cjh.component_xfermode.R
import kotlin.math.min

/**
 * @author: caijianhui
 * @date: 2020/4/28 17:55
 * @description:
 */
class ItemXFerModeView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = -1

) : View(context, attributeSet, defStyle) {

    private var mWidth = 0f
    private var isInit = false

    private lateinit var dstBitmap: Bitmap
    private lateinit var srcBitmap: Bitmap

    // 背景
    private lateinit var itemBackground: Shader

    private lateinit var mBitmapPaint: Paint

    private lateinit var mBgPaint: Paint

    private var offsetX = 0f
    private var offsetY = 0f

    private lateinit var rectF: RectF

    var mCurXfermode: Xfermode? = null
        set(value) {
            field = value
            invalidate()
        }

    fun setBitmap(
        dstBitmap: Bitmap,
        srcBitmap: Bitmap
    ) {
        this.dstBitmap = dstBitmap
        this.srcBitmap = srcBitmap
        invalidate()
    }

    init {
        init(context)
    }

    private fun init(context: Context) {

        val bm = Bitmap.createBitmap(
            intArrayOf(-0x1, -0x333334, -0x333334, -0x1),
            2, 2, Bitmap.Config.RGB_565
        )

        itemBackground = BitmapShader(bm, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        mBgPaint = Paint()
        mBitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        setBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.dst),
            BitmapFactory.decodeResource(resources, R.drawable.src)
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!isInit) {
            isInit = true
            mWidth = min(measuredWidth, measuredHeight).toFloat()

            offsetX = (measuredWidth - mWidth) / 2
            offsetY = (measuredHeight - mWidth) / 2

            rectF = RectF(0f, 0f, mWidth, mWidth)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if(dstBitmap == null || srcBitmap == null){
            return
        }

        if (mCurXfermode == null) {
            return
        }

        canvas.translate(offsetX, offsetY)

        mBgPaint.shader = itemBackground
        mBgPaint.style = Paint.Style.FILL
        canvas.drawRect(0f, 0f, mWidth, mWidth, mBgPaint)

        val layer = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(),
            null, Canvas.ALL_SAVE_FLAG)

        mBitmapPaint.xfermode = null
        canvas.drawBitmap(dstBitmap, null, rectF, mBitmapPaint)
        mBitmapPaint.xfermode = mCurXfermode
        canvas.drawBitmap(srcBitmap, null, rectF, mBitmapPaint)

        canvas.restoreToCount(layer)


    }

}