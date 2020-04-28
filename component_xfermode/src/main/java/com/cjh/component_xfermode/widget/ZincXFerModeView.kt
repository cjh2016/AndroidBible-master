package com.cjh.component_xfermode.widget

import android.R.attr.textSize
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.cjh.component_xfermode.R
import kotlin.math.min


/**
 * @author: caijianhui
 * @date: 2020/4/28 13:30
 * @description:
 */
class ZincXFerModeView @JvmOverloads constructor(  //在有默认参数值的方法中使用@JvmOverloads注解，则Kotlin就会暴露多个重载方法。
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = -1

) : View(context, attrs, defStyle) {

    companion object {
        val sModes = arrayOf(
            PorterDuffXfermode(PorterDuff.Mode.CLEAR),
            PorterDuffXfermode(PorterDuff.Mode.SRC),
            PorterDuffXfermode(PorterDuff.Mode.DST),
            PorterDuffXfermode(PorterDuff.Mode.XOR),

            PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
            PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
            PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
            PorterDuffXfermode(PorterDuff.Mode.DST_IN),

            PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
            PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
            PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
            PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),

            PorterDuffXfermode(PorterDuff.Mode.DARKEN),
            PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
            PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
            PorterDuffXfermode(PorterDuff.Mode.SCREEN)
        )

        val sLabels = arrayOf(
            "Clear", "Src", "Dst", "Xor",
            "SrcOver", "DstOver", "SrcIn", "DstIn",
            "SrcOut", "DstOut", "SrcATop", "DstATop",
            "Darken", "Lighten", "Multiply", "Screen"
        )
    }

    //边框画笔
    private lateinit var borderPaint: Paint

    //字体画笔
    private lateinit var textPaint: Paint

    //每个框的宽度
    private var itemWidth = 0f

    //横向边界
    private var horizontalOffset = 10f

    //纵向边界
    private var verticalOffset = 10f

    //边框的宽
    private var itemBorderWidth = 2

    //字体大小
    private var kTextSize = 25

    private var dst = 0
    private var src = 0

    private lateinit var dstBitmap: Bitmap
    private lateinit var srcBitmap: Bitmap


    private lateinit var rectF: RectF

    private lateinit var bitmapPaint: Paint

    private lateinit var mRangeRectF: RectF

    //背景
    private lateinit var itemBackground: Shader

    private var mDownSelIndex = -1


    init {
        init()
    }

    private fun init() {

        //边框画笔
        borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        borderPaint.apply {
            color = Color.BLACK
            strokeWidth = itemBorderWidth.toFloat()
        }

        //文字画笔
        textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint.apply {
            color = Color.BLACK
            textSize = kTextSize.toFloat()
            // 字体居中[即画的点为字体中间]
            textAlign = Paint.Align.CENTER
        }


        // 图的绘制笔
        bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        //创建四个像素点的位图，并为其指定颜色值
        val bm = Bitmap.createBitmap(
            intArrayOf(-0x1, -0x223334, -0x553334, -0x1),
            2, 2, Bitmap.Config.RGB_565
        )

        itemBackground = BitmapShader(bm, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)

        val m = Matrix()
        m.setScale(6f, 6f)  //将像素x和y方向各放大6倍，也就是36倍，便于观察
        itemBackground.setLocalMatrix(m)

        mRangeRectF = RectF()

        setBitmap(
            0,
            0,
            BitmapFactory.decodeResource(resources, R.drawable.dst),
            BitmapFactory.decodeResource(resources, R.drawable.src))

    }

    fun setBitmap(dst: Int, src: Int, dstBitmap: Bitmap, srcBitmap: Bitmap) {
        this.dst = dst
        this.src = src
        this.dstBitmap = dstBitmap
        this.srcBitmap = srcBitmap
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val width = min(w, h).toFloat()

        var isHorMin = w < h
        // 计算每个宽的大小
        itemWidth = if (isHorMin) {
            // 横向较短
            (width - 4 * horizontalOffset) / 4
        } else {
            // 纵向较短
            (width - 4 * horizontalOffset - 4 * kTextSize) / 4
        }

        val borderWidth = itemBorderWidth * 2
        rectF = RectF(
            borderWidth.toFloat(), borderWidth.toFloat(),
            (itemWidth - borderWidth), (itemWidth - borderWidth)
        )

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (null == srcBitmap || null == dstBitmap) {
            return
        }
        for (row in 0 until 4) {
            for (col in 0 until 4) {
                val translateX = horizontalOffset / 2 + (horizontalOffset + itemWidth) * col
                val translateY = (verticalOffset + itemWidth + kTextSize) * row

                val translateYItem = kTextSize + verticalOffset

                // 绘制边框
                borderPaint.apply {
                    shader = null
                    style = Paint.Style.STROKE
                }
                canvas.drawRect(
                    translateX,
                    translateY + translateYItem,
                    translateX + itemWidth,
                    translateY + itemWidth + translateYItem,
                    borderPaint
                )

                //绘制背景
                borderPaint.apply {
                    shader = itemBackground
                    style = Paint.Style.FILL
                }
                canvas.drawRect(
                    translateX,
                    translateY + translateYItem,
                    translateX + itemWidth,
                    translateY + itemWidth + translateYItem,
                    borderPaint
                )

                // 存储图层
                val layer = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(),
                    null, Canvas.ALL_SAVE_FLAG)

                canvas.translate(translateX, translateY)

                //绘制文字
                canvas.drawText(
                    sLabels[row * 4 + col],
                    itemWidth / 2,
                    (kTextSize + verticalOffset) / 2f + 10,
                    textPaint
                )

                canvas.translate(0f, translateYItem)

                bitmapPaint.xfermode = null

                //1.画目标图(记住先画dst)
                canvas.drawBitmap(dstBitmap, null, rectF, bitmapPaint)
                //2.设置叠加模式
                bitmapPaint.xfermode = sModes[row * 4 + col]
                //3.画源图(在画src)
                canvas.drawBitmap(srcBitmap, null, rectF, bitmapPaint)

                canvas.restoreToCount(layer)
            }
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (null == srcBitmap || null == dstBitmap) {
            return true
        }

        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mDownSelIndex = getTouchIndex(x, y)
            }
            MotionEvent.ACTION_UP -> {
                if (-1 == mDownSelIndex) {
                    return true
                }
            }
        }

        return true
    }

    private fun getTouchIndex(x: Float, y: Float): Int {
        for (row in 0 until 4) {
            for (col in 0 until 4) {
                val translateX = horizontalOffset / 2 + (horizontalOffset + itemWidth) * col
                val translateY = (verticalOffset + itemWidth + textSize) * row
                val translateYItem = textSize + verticalOffset
                mRangeRectF.set(translateX,
                    translateY + translateYItem,
                    translateX + itemWidth,
                    translateY + itemWidth + translateYItem)
                if (mRangeRectF.contains(x, y)) {
                    return row * 4 + col
                }
            }
        }
        return -1
    }

}