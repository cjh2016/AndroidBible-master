package com.cjh.component_customview.paint

import android.content.Context
import android.graphics.*
import android.graphics.Color.parseColor
import android.graphics.Typeface.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.cjh.component_customview.R
import com.cjh.component_customview.analyze.HelpDraw
import com.cjh.component_customview.analyze.HelpPath
import kotlin.math.abs


/**
 * @author: caijianhui
 * @date: 2020/4/18 10:30
 * @description:
 */
class PaintView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = -1
) : View(context, attrs, defStyle) {

    lateinit var mEffectPaint: Paint
    lateinit var mRedPaint: Paint
    lateinit var mTextPaint: TextPaint


    var lastX = 0f
    var lastY = 0f
    var winWidth = 0f

    var mEffectCorner = 0f
    var mDashOffset = 0f


    init {
        init()
    }

    private fun init() {
        mRedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mRedPaint.color = Color.RED
        mRedPaint.strokeWidth = 20f
        mRedPaint.style = Paint.Style.STROKE


        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        winWidth = outMetrics.widthPixels.toFloat()

        mEffectPaint = Paint(mRedPaint)

        mTextPaint = TextPaint()
    }

    override fun onDraw(canvas: Canvas) {

        super.onDraw(canvas)

        //背景色
        canvas.drawRGB(224, 247, 245);

        //绘制表格
        HelpDraw.draw(canvas, HelpDraw.getGrid(context))
        //绘制坐标系
        HelpDraw.draw(canvas, HelpDraw.getCoo(context, Point(550, 300)))

        //保存画布
        canvas.save()
        //设置画布原点为（250, 200）
        canvas.translate(550f, 300f)

        //dashEffect(canvas)

        //cornerEffect(canvas)

        //discreteEffect(canvas)

        //pathDashPathEffect(canvas)

        //composeEffect(canvas)

        //sumEffect(canvas)

        //drawLinearGradient(canvas)

        //drawMultiLinearGradient(canvas)

        //drawRadialGradient(canvas)

        //drawMultiRadialGradient(canvas)

        //drawSweepGradient(canvas)

        //drawBitmapShader(canvas)

        //drawPathBitmapShader(canvas)

        //drawLightingColorFilter(canvas)

        //drawPorterDuffColorFilter(canvas)

        //drawColorMatrixColorFilter(canvas)

        //drawTextPaint(canvas)

        //drawTypeface(canvas)

        //drawFontMetrics(canvas)

        //drawTextBounds(canvas)

        drawShadowLayer(canvas)

        canvas.restore()

    }

    /**
     * 绘制阴影
     */
    private fun drawShadowLayer(canvas: Canvas) {

        mRedPaint.apply {
            setShadowLayer(50f, 20f, 20f, Color.parseColor("#EEF39729"))
            textSize = 200f
            strokeWidth = 10f
            style = Paint.Style.FILL
        }
        canvas.drawText("Toly", 300f, 300f, mRedPaint)
    }


    /**
     * 矩形文字区域
     */
    private fun drawTextBounds(canvas: Canvas) {

        drawFontMetrics(canvas)

        canvas.save()
        canvas.translate(100f, 200f)


        val text = "I am Toly"
        val textRect = Rect()
        mTextPaint.getTextBounds(text, 0, text.length, textRect)
        Log.e(TAG, textRect.toShortString())

        //绘制矩形
        val tempPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        tempPaint.apply {
            color = Color.parseColor("#66F4F628")
            style = Paint.Style.FILL
        }
        canvas.drawRect(textRect, tempPaint)

        canvas.restore()
    }

    /**
     * 文字测量
     */
    private fun drawFontMetrics(canvas: Canvas) {
        canvas.save()
        canvas.translate(100f, 200f)
        mTextPaint.apply {
            textSize = 100f
            typeface = SERIF
        }

        canvas.drawText("I am Toly", 0f, 0f, mTextPaint)

        val fm: Paint.FontMetrics = mTextPaint.fontMetrics

        Log.e(TAG, "top: ${fm.top}")
        Log.e(TAG, "ascent: ${fm.ascent}")
        Log.e(TAG, "leading: ${fm.leading}")
        Log.e(TAG, "descent: ${fm.descent}")
        Log.e(TAG, "bottom: ${fm.bottom}")

        val tempPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        tempPaint.apply {
            strokeWidth = 1f
            color = Color.RED
            style = Paint.Style.STROKE
        }

        canvas.drawLine(0f, fm.top, winWidth, fm.top, tempPaint)

        tempPaint.apply {
            color = Color.MAGENTA
        }

        canvas.drawLine(0f, fm.ascent, winWidth, fm.ascent, tempPaint)

        tempPaint.apply {
            color = Color.parseColor("#4C17F9")
        }

        canvas.drawLine(0f, fm.leading, winWidth, fm.leading, tempPaint)

        tempPaint.apply {
            color = Color.GREEN
        }

        canvas.drawLine(0f, fm.descent, winWidth, fm.descent, tempPaint)

        tempPaint.apply {
            color = Color.parseColor("#E74EDD")
        }

        canvas.drawLine(0f, fm.bottom, winWidth, fm.bottom, tempPaint)

        canvas.restore()
    }

    /**
     * 设置字体
     */
    private fun drawTypeface(canvas: Canvas) {
        mTextPaint.textSize = 50f
        canvas.save()
        canvas.translate(50f, -300f)
        mTextPaint.typeface = Typeface.MONOSPACE
        canvas.drawText("MONOSPACE", 0f, 0f, mTextPaint)

        //粗体
        canvas.translate(0f, 100f)
        mTextPaint.typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
        canvas.drawText("MONOSPACE+BOLD", 0f, 0f, mTextPaint)

        //斜体
        canvas.translate(0f, 100f)
        val typeface2 = Typeface.create(MONOSPACE, Typeface.ITALIC)
        mTextPaint.typeface = typeface2
        canvas.drawText("MONOSPACE+ITALIC", 0f, 0f, mTextPaint)

        //粗斜体
        canvas.translate(0f, 100f)
        val typeface3 = Typeface.create(MONOSPACE, BOLD_ITALIC)
        mTextPaint.typeface = typeface3
        canvas.drawText("MONOSPACE+BOLD_ITALIC", 0f, 0f, mTextPaint)

        //使用外部字体
        canvas.translate(0f, 100f)
        val myFont = Typeface.createFromAsset(context.assets, "ACHAFSEX.TTF")
        mTextPaint.typeface = myFont
        canvas.drawText("Hello I am Toly", 0f, 0f, mTextPaint)

        canvas.restore()
    }

    /**
     * 文字相关
     */
    private fun drawTextPaint(canvas: Canvas) {
        canvas.save()
        canvas.translate(200f, -100f)
        mTextPaint.typeface = Typeface.SANS_SERIF
        mTextPaint.textAlign = Paint.Align.LEFT
        mTextPaint.textSize = 100f
        canvas.drawText("SANS_SERIF", 0f, 0f, mTextPaint)
        val tempPaint = Paint()
        tempPaint.strokeWidth = 4f
        tempPaint.color = Color.RED
        tempPaint.style = Paint.Style.STROKE
        canvas.drawRect(0f, -100f, winWidth, 0f, tempPaint)

        canvas.translate(0f, 150f)
        mTextPaint.textAlign = Paint.Align.RIGHT
        mTextPaint.typeface = Typeface.SERIF
        canvas.drawText("SERIF", 0f, 0f, mTextPaint)
        canvas.drawRect(0f, -100f, winWidth, 0f, tempPaint)

        canvas.translate(0f, 150f)
        mTextPaint.typeface = Typeface.MONOSPACE
        mTextPaint.textAlign = Paint.Align.CENTER
        canvas.drawText("MONOSPACE", 0f, 0f, mTextPaint)
        canvas.drawRect(0f, -100f, winWidth, 0f, tempPaint)

        canvas.restore()
    }

    /**
     * ColorMatrixColorFilter
     */
    private fun drawColorMatrixColorFilter(canvas: Canvas) {
        canvas.save()
        canvas.translate(-500f, 0f)
        val mainBitmap =
            BitmapFactory.decodeResource(resources, R.drawable.nima)
        mRedPaint.style = Paint.Style.FILL

        //关闭RGB颜色通道(变为黑色)，后偏移红色255
        val matrix = floatArrayOf(
            -1f, 0f, 0f, 0f, 255f,
            0f, -1f, 0f, 0f, 0f,
            0f, 0f, -1f, 0f, 0f,
            0f, 0f, 0f, 1f, 0f
        )
        val colorMatrix = ColorMatrix(matrix)
        mRedPaint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        canvas.drawBitmap(mainBitmap, null,
            RectF(0f, 0f, 200f, 200f), mRedPaint)

        canvas.translate(250f, 0f)
        //关闭RGB颜色通道(变为黑色)，后偏移绿色255
        val matrix2 = floatArrayOf(
            -1f, 0f, 0f, 0f, 0f,
            0f, -1f, 0f, 0f, 255f,
            0f, 0f, -1f, 0f, 0f,
            0f, 0f, 0f, 1f, 0f
        )
        val colorMatrix2 = ColorMatrix(matrix2)
        mRedPaint.colorFilter = ColorMatrixColorFilter(colorMatrix2)
        canvas.drawBitmap(mainBitmap, null,
            RectF(0f, 0f, 200f, 200f), mRedPaint)

        canvas.translate(250f, 0f)
        //关闭RGB颜色通道(变为黑色)，后偏移绿色255
        val matrix3 = floatArrayOf(
            -1f, 0f, 0f, 0f, 0f,
            0f, -1f, 0f, 0f, 0f,
            0f, 0f, -1f, 0f, 255f,
            0f, 0f, 0f, 1f, 0f
        )
        val colorMatrix3 = ColorMatrix(matrix3)
        mRedPaint.colorFilter = ColorMatrixColorFilter(colorMatrix3)
        canvas.drawBitmap(mainBitmap, null,
            RectF(0f, 0f, 200f, 200f), mRedPaint)

        canvas.translate(250f, 0f)
        //关闭RGB颜色通道(变为黑色)，后偏移三色255
        val matrix4 = floatArrayOf(
            -1f, 0f, 0f, 0f, 255f,
            0f, -1f, 0f, 0f, 255f,
            0f, 0f, -1f, 0f, 255f,
            0f, 0f, 0f, 1f, 0f
        )
        val colorMatrix4 = ColorMatrix(matrix4)
        mRedPaint.colorFilter = ColorMatrixColorFilter(colorMatrix4)
        canvas.drawBitmap(mainBitmap, null,
            RectF(0f, 0f, 200f, 200f), mRedPaint)

        canvas.translate(250f, 0f)

        val matrix5 = floatArrayOf(
            //只要把RGB三通道的色彩信息设置成一样:即：R＝G＝B,
            // 为了保证图像亮度不变，同一个通道中的R+G+B=1
            0.3086f, 0.6094f, 0.0820f, 0f, 0f,
            0.3086f, 0.6094f, 0.0820f, 0f, 0f,
            0.3086f, 0.6094f, 0.0820f, 0f, 0f,
            0f, 0f, 0f, 1f, 0f
        )
        val colorMatrix5 = ColorMatrix(matrix5)
        mRedPaint.colorFilter = ColorMatrixColorFilter(colorMatrix5)
        canvas.drawBitmap(mainBitmap, null,
            RectF(0f, 0f, 200f, 200f), mRedPaint)

        canvas.restore()
    }

    /**
     * PorterDuffColorFilter
     */
    private fun drawPorterDuffColorFilter(canvas: Canvas) {
        canvas.save()
        canvas.translate(-300f, 0f)
        val mainBitmap =
            BitmapFactory.decodeResource(resources, R.drawable.nima)
        mRedPaint.style = Paint.Style.FILL

        mRedPaint.colorFilter = PorterDuffColorFilter(
            parseColor("#0000ff"), PorterDuff.Mode.DARKEN
        )
        canvas.drawBitmap(mainBitmap, null,
            RectF(0f, 0f, 200f, 200f), mRedPaint)

        canvas.translate(250f, 0f)
        mRedPaint.colorFilter = PorterDuffColorFilter(
            parseColor("#0000ff"), PorterDuff.Mode.LIGHTEN
        )
        canvas.drawBitmap(mainBitmap, null,
            RectF(0f, 0f, 200f, 200f), mRedPaint)

        canvas.translate(250f, 0f)
        mRedPaint.colorFilter = PorterDuffColorFilter(
            parseColor("#0000ff"), PorterDuff.Mode.SCREEN
        )
        canvas.drawBitmap(mainBitmap, null,
            RectF(0f, 0f, 200f, 200f), mRedPaint)

        canvas.translate(250f, 0f)
        mRedPaint.colorFilter = PorterDuffColorFilter(
            parseColor("#0000ff"), PorterDuff.Mode.OVERLAY
        )
        canvas.drawBitmap(mainBitmap, null,
            RectF(0f, 0f, 200f, 200f), mRedPaint)
        canvas.restore()
    }

    /**
     * LightingColorFilter
     */
    private fun drawLightingColorFilter(canvas: Canvas) {
        canvas.save()
        val mainBitmap = BitmapFactory.decodeResource(resources, R.drawable.nima)
        mRedPaint.style = Paint.Style.FILL
        mRedPaint.colorFilter = LightingColorFilter(
            parseColor("#FF0000"),  //红
            parseColor("#0000ff") //蓝
        )
        canvas.drawBitmap(mainBitmap, null,
            RectF(0f, 0f, 200f, 200f), mRedPaint)


        canvas.translate(250f, 0f)
        mRedPaint.colorFilter = LightingColorFilter(
            parseColor("#FF0000"),  //红
            parseColor("#00ff00") //绿
        )
        canvas.drawBitmap(mainBitmap, null,
            RectF(0f, 0f, 200f, 200f), mRedPaint)


        canvas.translate(250f, 0f)
        mRedPaint.colorFilter = LightingColorFilter(
            parseColor("#FF0000"),  //红
            parseColor("#000000") //黑
        )
        canvas.drawBitmap(mainBitmap, null,
            RectF(0f, 0f, 200f, 200f), mRedPaint)

        canvas.restore()
    }

    /**
     * 路径+图片着色器
     */
    private fun drawPathBitmapShader(canvas: Canvas) {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.nima)
        val bs = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        mRedPaint.shader = bs
        mRedPaint.style = Paint.Style.FILL
        val path = HelpPath.regularStarPath(8, 150f)
        canvas.drawPath(path, mRedPaint)
    }


    /**
     * 图片着色器
     */
    private fun drawBitmapShader(canvas: Canvas) {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.nima)
        val bs = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        mRedPaint.shader = bs
        mRedPaint.textSize = 120f
        mRedPaint.strokeWidth = 10f
        mRedPaint.style = Paint.Style.FILL_AND_STROKE
        canvas.drawText("张风捷特烈", 0f, 200f, mRedPaint)
    }

    /**
     * 扫描渐变
     */
    private fun drawSweepGradient(canvas: Canvas) {
        val colorStart = parseColor("#84F125")
        val colorEnd = parseColor("#5825F1")
        mRedPaint.style = Paint.Style.FILL
        mRedPaint.shader =
            SweepGradient(0f, 0f, colorStart, colorEnd)
        canvas.drawCircle(0f, 0f, 150f, mRedPaint)

        canvas.translate(400f, 0f)
        val colors = intArrayOf(
            parseColor("#F60C0C"),  //红
            parseColor("#F3B913"),  //橙
            parseColor("#E7F716"),  //黄
            parseColor("#3DF30B"),  //绿
            parseColor("#0DF6EF"),  //青
            parseColor("#0829FB"),  //蓝
            parseColor("#B709F4")   //紫
        )
        val pos = floatArrayOf(
            1f / 7, 2f / 7, 3f / 7, 4f / 7, 5f / 7, 6f / 7, 1f
        )
        mRedPaint.shader = SweepGradient(0f, 0f, colors, pos)
        canvas.drawCircle(0f, 0f, 150f, mRedPaint)
    }

    /**
     * 多色多点径向渐变
     */
    private fun drawMultiRadialGradient(canvas: Canvas) {
        val colors = intArrayOf(
            parseColor("#F60C0C"),  //红
            parseColor("#F3B913"),  //橙
            parseColor("#E7F716"),  //黄
            parseColor("#3DF30B"),  //绿
            parseColor("#0DF6EF"),  //青
            parseColor("#0829FB"),  //蓝
            parseColor("#B709F4")   //紫
        )

        val pos = floatArrayOf(
            1f / 7, 2f / 7, 3f / 7, 4f / 7, 5f / 7, 6f / 7, 1f
        )

        mRedPaint.style = Paint.Style.FILL
        mRedPaint.shader = RadialGradient(
            0f, 0f, 200f,
            colors, pos,
            Shader.TileMode.CLAMP
        )
        canvas.drawCircle(0f, 0f, 250f, mRedPaint)
    }

    /**
     * 径向渐变
     */
    private fun drawRadialGradient(canvas: Canvas) {
        val colorStart = Color.parseColor("#84F125")
        val colorEnd = Color.parseColor("#5825F1")
        mRedPaint.style = Paint.Style.FILL
        mRedPaint.shader = RadialGradient(0f, 0f, 50f,
            colorStart, colorEnd, Shader.TileMode.MIRROR)
        canvas.drawCircle(0f, 0f, 150f, mRedPaint)

        canvas.translate(350f, 0f)
        mRedPaint.shader = RadialGradient(0f, 0f, 50f,
            colorStart, colorEnd, Shader.TileMode.CLAMP)
        canvas.drawCircle(0f, 0f, 150f, mRedPaint)

        canvas.translate(350f, 0f)
        mRedPaint.shader = RadialGradient(0f, 0f, 50f,
            colorStart, colorEnd, Shader.TileMode.REPEAT)
        canvas.drawCircle(0f, 0f, 150f, mRedPaint)
    }

    /**
     * 多色多点渐变
     */
    private fun drawMultiLinearGradient(canvas: Canvas) {
        mRedPaint.style = Paint.Style.FILL

        val colors = intArrayOf(
            Color.parseColor("#F60C0C"),//红
            Color.parseColor("#F3B913"),//橙
            Color.parseColor("#E7F716"),//黄
            Color.parseColor("#3DF30B"),//绿
            Color.parseColor("#0DF6EF"),//青
            Color.parseColor("#0829FB"),//蓝
            Color.parseColor("#B709F4")//紫
        )

        val pos = floatArrayOf(
            1f / 7, 2f / 7, 3f / 7, 4f / 7, 5f / 7, 6f / 7, 1f
        )
        canvas.translate(0f, 150f)
        mRedPaint.shader = LinearGradient(-300f, 0f, 300f, 0f, colors, pos,
            Shader.TileMode.CLAMP)
        canvas.drawRect(-400f, -200f, 400f, -100f, mRedPaint)
    }

    /**
     * 线性渐变
     */
    private fun drawLinearGradient(canvas: Canvas) {
        val colorStart = Color.parseColor("#84F125")
        val colorEnd = Color.parseColor("#5825F1")
        canvas.save()
        mRedPaint.style = Paint.Style.FILL
        mRedPaint.shader = LinearGradient(
            -200f, 0f, 200f, 0f,
            colorStart, colorEnd,
            Shader.TileMode.MIRROR
        )
        canvas.drawRect(-400f, -200f, 400f, -100f, mRedPaint)

        canvas.translate(0f, 150f)
        mRedPaint.shader = LinearGradient(
            -200f, 0f, 200f, 0f,
            colorStart, colorEnd,
            Shader.TileMode.CLAMP
        )
        canvas.drawRect(-400f, -200f, 400f, -100f, mRedPaint)

        canvas.translate(0f, 150f)
        mRedPaint.shader = LinearGradient(
            -200f, 0f, 200f, 0f,
            colorStart, colorEnd,
            Shader.TileMode.REPEAT
        )
        canvas.drawRect(-400f, -200f, 400f, -100f, mRedPaint)

        canvas.restore()
    }

    /**
     * 路径叠加
     */
    private fun sumEffect(canvas: Canvas) {
        canvas.save()
        canvas.translate(0f, 300f)
        val path = Path()
        //定义路径的起点
        path.moveTo(100f, 80f)
        path.lineTo(300f, -100f)
        path.lineTo(600f, 80f)
        val effect1 = PathDashPathEffect(
            HelpPath.regularStarPath(5, 10f),
            40f,
            mDashOffset,
            PathDashPathEffect.Style.ROTATE
        )
        val effect2 = DiscretePathEffect(20f, 5f)
        mEffectPaint.pathEffect = SumPathEffect(effect1, effect2) //离散效果+样点效果
        canvas.drawPath(path, mEffectPaint)
        canvas.restore()
    }

    /**
     * 叠加样式
     */
    private fun composeEffect(canvas: Canvas) {
        mEffectPaint.style = Paint.Style.STROKE
        mEffectPaint.strokeWidth = 40f
        canvas.save()
        canvas.translate(0f, 350f)
        val path = Path()
        //定义路径的起点
        path.moveTo(100f, 80f)
        path.lineTo(300f, -100f)
        path.lineTo(600f, 80f)
        val effect1 = PathDashPathEffect(
            HelpPath.regularStarPath(5, 10f),
            40f,
            mDashOffset,
            PathDashPathEffect.Style.MORPH
        )
        val effect2 = DiscretePathEffect(25f, 25f)
        mEffectPaint.pathEffect = ComposePathEffect(effect1, effect2) //离散效果+样点效果
        canvas.drawPath(path, mEffectPaint)
        canvas.restore()
    }

    /**
     * 路径点样路径样式
     */
    private fun pathDashPathEffect(canvas: Canvas) {
        canvas.save()
        canvas.translate(0f, 400f)
        val path = Path()
        //定义路径的起点
        path.moveTo(100f, 80f)
        path.lineTo(300f, -100f)
        path.lineTo(500f, 80f)
        //旋转过渡
        mEffectPaint.pathEffect = PathDashPathEffect(
            HelpPath.regularStarPath(5, 10f), 40f,
            mDashOffset,
            PathDashPathEffect.Style.ROTATE
        )
        canvas.drawPath(path, mEffectPaint)
        canvas.restore()
        //变形过渡
        canvas.save()
        canvas.translate(0f, 500f)
        mEffectPaint.pathEffect = PathDashPathEffect(
            HelpPath.regularStarPath(5, 10f),
            40f,
            mDashOffset,
            PathDashPathEffect.Style.MORPH
        )
        canvas.drawPath(path, mEffectPaint)
        canvas.restore()
        //移动过渡
        canvas.save()
        canvas.translate(0f, 600f)
        mEffectPaint.pathEffect = PathDashPathEffect(
            HelpPath.regularStarPath(5, 10f),
            40f,
            mDashOffset,
            PathDashPathEffect.Style.TRANSLATE
        )
        canvas.drawPath(path, mEffectPaint)
        canvas.restore()
    }

    /**
     * 离散路径
     */
    private fun discreteEffect(canvas: Canvas) {
        canvas.save()//保存画布状态
        canvas.translate(0f, 450f)
        val path = Path()
        path.moveTo(100f, 0f)
        path.lineTo(400f, -100f)
        path.lineTo(800f, 0f)

        //第一个参数：将原来的路径切成多长的线段,越小，所切成的小线段越多
        //第二参数：被切成的每个小线段的可偏移距离。越大，每个线段的可偏移距离就越大。
        mEffectPaint.pathEffect = DiscretePathEffect(2f, 5f)
        mEffectPaint.strokeWidth = 2f
        mEffectPaint.style = Paint.Style.STROKE

        canvas.drawPath(path, mEffectPaint)
        canvas.translate(0f, 100f)
        mEffectPaint.pathEffect = DiscretePathEffect(20f, 25f)
        canvas.drawPath(path, mEffectPaint)
        canvas.restore()//重新存储画布状态
    }

    /**
     * 圆角折线
     */
    private fun cornerEffect(canvas: Canvas) {
        mEffectPaint.strokeWidth = 40f
        mEffectPaint.pathEffect = CornerPathEffect(mEffectCorner)
        val path = Path()
        path.moveTo(550f, 450f)
        path.lineTo(900f, 300f)
        path.lineTo(1000f, 450f)

        canvas.drawPath(path, mEffectPaint)

        //蓝色辅助线
        val tempPaint = Paint()
        tempPaint.style = Paint.Style.STROKE
        tempPaint.strokeWidth = 2f
        tempPaint.pathEffect = DashPathEffect(floatArrayOf(20f, 20f), 0f)
        val helpPath = Path()
        helpPath.moveTo(550f, 450f)
        helpPath.lineTo(900f, 300f)
        helpPath.lineTo(1000f, 450f)
        canvas.drawPath(helpPath, tempPaint)
    }

    /**
     * 虚线路径
     */
    private fun dashEffect(canvas: Canvas) {
        mEffectPaint.strokeCap = Paint.Cap.BUTT
        //显示100，隐藏50，显示50，隐藏50，的循环
        mEffectPaint.pathEffect = DashPathEffect(floatArrayOf(100f, 50f, 50f, 50f), 0f)
        val path = Path()
        path.moveTo(100f, 350f)
        path.lineTo(1000f, 350f)
        canvas.drawPath(path, mEffectPaint)
        //显示100，隐藏50，显示50，隐藏50，的循环, 偏移：mDashOffset
        mEffectPaint.pathEffect = DashPathEffect(floatArrayOf(100f, 50f, 50f, 50f), mDashOffset)
        val pathOffset50 = Path()
        pathOffset50.moveTo(100f, 450f)
        pathOffset50.lineTo(1000f, 450f)
        canvas.drawPath(pathOffset50, mEffectPaint)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                val dx = abs(event.x - lastX)
                mDashOffset = dx / winWidth / 2 * 100
                mEffectCorner = mDashOffset
                invalidate()
                return true
            }

            MotionEvent.ACTION_UP -> {

            }


        }
        return super.onTouchEvent(event)
    }

    companion object {
        val TAG = PaintView::class.java.simpleName
    }


}