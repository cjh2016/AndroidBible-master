package com.cjh.component_customview.color

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.cjh.component_customview.analyze.HelpDraw

/**
 * @author: caijianhui
 * @date: 2020/4/27 9:52
 * @description:
 */
class ColorView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = -1)
    : View(context, attrs, defStyle) {

    private lateinit var mMainPaint: Paint
    private val origin: Point = Point(100, 50)

    private lateinit var src: Bitmap
    private lateinit var dst: Bitmap

    private lateinit var mLayerPaint: Paint

    private lateinit var mTextPaint: Paint

    private lateinit var mDashPaint: Paint

    private lateinit var mModes: ArrayList<PorterDuffXfermode>

    private lateinit var mModeText: ArrayList<String>


    init {
        init()
    }

    private fun init() {


        mMainPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mMainPaint.apply {
            style = Paint.Style.FILL
            strokeCap = Paint.Cap.ROUND
        }

        src = createSrcBitmap()
        dst = createDstBitmap()

        //背景图层的笔
        mLayerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mLayerPaint.apply {
            style = Paint.Style.FILL
            isFilterBitmap = false
        }

        //文字的笔
        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        val mTypeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
        mTextPaint.apply {
            textSize = 35f
            typeface = mTypeface
            color = Color.parseColor("#FFF98D1F")
        }

        //虚线画笔
        mDashPaint = Paint()
        mDashPaint.apply {
            strokeWidth = 3f
            color = Color.RED
            style = Paint.Style.STROKE
            //设置虚线效果new float[]{可见长度, 不可见长度},偏移值
            pathEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        }

        mModes = arrayListOf(
            PorterDuffXfermode(PorterDuff.Mode.CLEAR),
            PorterDuffXfermode(PorterDuff.Mode.SRC),
            PorterDuffXfermode(PorterDuff.Mode.DST),
            PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
            PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
            PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
            PorterDuffXfermode(PorterDuff.Mode.DST_IN),
            PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
            PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
            PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
            PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
            PorterDuffXfermode(PorterDuff.Mode.XOR),
            PorterDuffXfermode(PorterDuff.Mode.DARKEN),
            PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
            PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
            PorterDuffXfermode(PorterDuff.Mode.SCREEN),
            PorterDuffXfermode(PorterDuff.Mode.ADD),
            PorterDuffXfermode(PorterDuff.Mode.OVERLAY)
        )

        mModeText = arrayListOf(
            "CLEAR",
            "SRC",
            "DST",
            "SRC_OVER",
            "DST_OVER",
            "SRC_IN",
            "DST_IN",
            "SRC_OUT",
            "DST_OUT",
            "SRC_ATOP",
            "DST_ATOP",
            "XOR",
            "DARKEN",
            "LIGHTEN",
            "MULTIPLY",
            "SCREEN",
            "ADD",
            "OVERLAY"
        )

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        HelpDraw.drawCoordinates(context, canvas, origin)
        canvas.translate(origin.x.toFloat(), origin.y.toFloat())

        //创建一个图层，在图层上演示图形混合后的效果
        var sc = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sc = canvas.saveLayer(RectF(0f, 0f, 2500f, 2500f), mLayerPaint)
        }

        for (i in 0 until 18) {
            var line = i % 6
            var row = i / 6

            mMainPaint.xfermode = null

            //目标图象 (圆形)  1. 先画目标图像
            canvas.drawBitmap(dst, 200f * line, 200f * row, mMainPaint)
            //设置对源的叠合模式  2. 设置混合的模式
            mMainPaint.xfermode = mModes[i]
            //源图像 (矩形)   3. 在画源图像
            canvas.drawBitmap(src, 50 + 200f * line, 50 + 200f * row, mMainPaint)

            //辅助信息
            canvas.drawText(mModeText[i], 10 + 200f * line, 50 + 100 + 30 + 200f * row, mTextPaint)

            canvas.drawCircle(50 + 200f * line, 50 + 200f * row, 50f, mDashPaint)

            canvas.drawRect(
                50 + 200f * line, 50 + row * 200f,
                100 + 50 + 200f * line, 100 + 50f + row * 200,
                mDashPaint
            )

        }

        canvas.restoreToCount(sc)

        //
        //drawBitmapX(canvas, Color.BLUE)

        canvas.restore()
    }

    /**
     *
     */
    private fun drawBitmapX(canvas: Canvas, color: Int) {
        canvas.drawBitmap(createBitmapX(canvas, color), 100f, 100f, mMainPaint)
    }

    /**
     * 创建一个Bitmap
     */
    private fun createBitmapX(canvas: Canvas, color: Int): Bitmap {

        //创建一个ARGB_8888, 宽高200的bitmap
        val bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
        //使用Bitmap创建一个canvas画板, 画板上的一切都会保留在bitmap上
        val bitmapCanvas = Canvas(bitmap)
        //接下来就是在画板上操作
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = color
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        bitmapCanvas.drawRect(rect, p)
        p.color = Color.GRAY
        p.strokeWidth = 3f
        bitmapCanvas.drawLine(0f, 0f, 200f, 200f, p)
        bitmapCanvas.drawLine(200f, 0f, 0f, 200f, p)
        return bitmap
    }

    /**
     * 创建源图片 (矩形)
     * @return bitmap
     */
    private fun createSrcBitmap():Bitmap {
        //创建一个ARGB_8888, 宽高100的bitmap
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        //使用Bitmap创建一个canvas画板，画板上的一切都会保留在bitmap上
        val bitmapCanvas = Canvas(bitmap)
        //接下来就是在画板上操作
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = Color.parseColor("#882045F3")
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        bitmapCanvas.drawRect(rect, p)
        return bitmap
    }

    /**
     * 创建目标图片 （圆形）
     * @return bitmap
     */
    private fun createDstBitmap(): Bitmap {
        //创建一个ARGB_8888, 宽高100的bitmap
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        //使用Bitmap创建一个canvas画板，画板上的一切都会保留在bitmap上
        val bitmapCanvas = Canvas(bitmap)
        //接下来就是在画板上操作
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = Color.parseColor("#FF43F41D")
        bitmapCanvas.drawCircle(bitmap.width / 2f, bitmap.height / 2f, bitmap.height / 2f, p)
        return bitmap
    }
}