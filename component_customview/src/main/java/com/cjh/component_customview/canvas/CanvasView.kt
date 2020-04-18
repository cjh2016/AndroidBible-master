package com.cjh.component_customview.canvas

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.cjh.component_customview.R
import com.cjh.component_customview.analyze.HelpDraw
import com.cjh.component_customview.analyze.HelpPath
import com.cjh.component_customview.analyze.Utils

/**
 * @author: caijianhui
 * @date: 2020/4/15 11:56
 * @description:
 */
class CanvasView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = -1)
    :View(context, attrs, defStyle) {

    lateinit var mGridPaint: Paint
    lateinit var mWinSize: Point
    lateinit var mCoo: Point

    lateinit var mRedPaint: Paint

    init {
        init()
    }

    private fun init() {
        mWinSize = Point()
        mCoo = Point(250, 200)
        Utils.loadWinSize(context, mWinSize)
        mGridPaint = Paint(Paint.ANTI_ALIAS_FLAG)


        mRedPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mRedPaint.strokeWidth = 10f
        mRedPaint.color=Color.RED

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //背景色
        canvas.drawRGB(224, 247, 245);

        //绘制表格
        HelpDraw.draw(canvas, HelpDraw.getGrid(context))
        //绘制坐标系
        HelpDraw.draw(canvas, HelpDraw.getCoo(context, mCoo))



        //保存画布
        canvas.save()
        //设置画布原点为（250, 200）
        canvas.translate(250f, 200f)

        //测试Canvas系列

        //测试drawPoint系列
        //drawPointX(canvas)

        //测试drawLine系列
        //drawLineX(canvas)

        //测试drawRect系列
        //drawRectX(canvas)

        //测试类圆
        //drawLikeCircle(canvas)

        //测试drawBitmap系列
        //drawBitmapX(canvas)

        //测试drawPicture系列
        //drawPictureX(canvas)

        //测试drawText系列
        //drawTextX(canvas)

        //测试旋转画布
        //rotateCanvas(canvas)

        //测试平移画布
        //translateCanvas(canvas)

        //测试缩放画布
        //scaleCanvas(canvas)

        //测试斜切画布
        //skewCanvas(canvas)

        //测试save,restore画布
        //saveRestoreCanvas(canvas)

        //裁剪画布
        clipCanvas(canvas)



        //恢复画布
        canvas.restore()

    }

    private fun drawLikeCircle(canvas: Canvas) {
        //绘制圆（矩形边界，画笔）
        canvas.drawCircle(650f, 200f, 100f, mRedPaint)

        //canvas.drawOval(100f,100f,500f,300f,mRedPaint)
        //等价上行
        //绘制椭圆(矩形边界，画笔)
        val rectF = RectF(100f, 100f, 500f, 300f)
        canvas.drawOval(rectF, mRedPaint)


        val rectArc = RectF(100f + 500f, 100f, 500f + 500f, 300f)
        //绘制圆弧(矩形边界，开始角度，扫过角度，使用中心？边缘两点与中心连线区域：边缘两点连线区域)
        canvas.drawArc(rectArc, 0f, 90f, true, mRedPaint)


        val rectArc2 = RectF(800f, 50f, 1000f, 150f)
        //绘制圆弧(矩形边界，开始角度，扫过角度，使用中心？边缘两点与中心连线区域：边缘两点连线区域)
        canvas.drawArc(rectArc2, 0f, 90f, false, mRedPaint)

    }

    /**
     * 测试drawRect系列
     */
    private fun drawRectX(canvas: Canvas) {
        canvas.drawRect(100f, 100f, 500f, 300f, mRedPaint)

        //等价上行
        /*val rect = Rect(100, 100, 500, 300)
        canvas.drawRect(rect, mRedPaint)*/

        //(左，上，右，下，x圆角，y圆角)
        canvas.drawRoundRect(100f + 500f, 100f, 500f + 500f, 300f,
            50f, 50f, mRedPaint)
    }


    /**
     * 测试drawPoint系列
     */
    private fun drawPointX(canvas: Canvas) {
        canvas.drawPoint(100f, 100f, mRedPaint)
        canvas.drawPoints(floatArrayOf(
            400f, 400f, 500f, 500f,
            600f, 400f, 700f, 350f,
            800f, 300f, 900f, 300f
        ), mRedPaint)
    }

    /**
     * 测试drawLine系列
     */
    private fun drawLineX(canvas: Canvas) {
        canvas.drawLine(500f, 200f, 900f, 400f, mRedPaint)
        canvas.drawLines(floatArrayOf(
            200f, 200f, 400f, 200f,
            400f, 200f, 200f, 400f,
            200f, 400f, 400f, 400f
        ), mRedPaint)
    }

    /**
     * 绘制图片
     */
    private fun drawBitmapX(canvas: Canvas) {
        //1.定点绘制图片
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.nima)
        canvas.drawBitmap(bitmap, 100f, 100f, mRedPaint)

        //2.适用变换矩阵绘制图片
        /*val matrix = Matrix()
        matrix.setValues(floatArrayOf(
            1f, 0f, 150 * 2f,
            0f, 1f, 100 * 2f,
            0f, 0f, 2f
        ))
        canvas.drawBitmap(bitmap, matrix, mRedPaint)*/


        //3.图片适用矩形区域不裁剪
        /*val rectf1 = RectF(600f, 100f, 1000f, 400f)
        canvas.drawBitmap(bitmap, null, rectf1, mRedPaint)*/


        //4.图片裁剪出矩形区域
        val rect = Rect(100, 100, 600, 220)
        //图片适用矩形区域
        val rectf2=RectF(250f,100f,500f,300f)
        //TODO 这个方法有待研究，不是很懂
        canvas.drawBitmap(bitmap, rect, rectf2, mRedPaint)
        //canvas.drawRect(rect, mRedPaint)

    }


    /**
     * 绘制picture
     */
    private fun drawPictureX(canvas: Canvas) {
        //创建Picture对象
        val picture = Picture()

        //确定picture产生的Canvas元件的大小，并生产Canvas元件
        val recordingCanvas = picture.beginRecording(canvas.width, canvas.height)

        //Canvas元件的操作
        recordingCanvas.drawRect(100f, 0f, 200f, 100f, mRedPaint)
        recordingCanvas.drawRect(0f, 100f, 100f, 200f, mRedPaint)
        recordingCanvas.drawRect(200f, 100f, 300f, 200f, mRedPaint)

        //Canvas元件绘制结束
        picture.endRecording()


        canvas.save()
        //使用picture的Canvas元件
        canvas.drawPicture(picture)

        canvas.translate(0f, 300f)
        //picture.draw(canvas)  //和canvas.drawPicture(picture)是一样的效果
        canvas.drawPicture(picture)

        canvas.translate(350f, 0f)
        canvas.drawPicture(picture)

        canvas.restore()

    }

    /**
     * 绘制文本
     */
    private fun drawTextX(canvas: Canvas) {
        mRedPaint.textSize = 100f
        canvas.drawText("张风捷特烈--Toly", 200f, 300f, mRedPaint)

    }

    private fun stateTest(canvas: Canvas) {
        canvas.drawLine(500f, 200f, 900f, 400f, mRedPaint)
        canvas.drawRect(100f,100f,300f,200f,mRedPaint)

    }

    /**
     * 旋转画布
     */
    private fun rotateCanvas(canvas: Canvas) {
        stateTest(canvas)

        //保存canvas状态
        canvas.save()
        mRedPaint.color=Color.parseColor("#880FB5FD")

        //canvas.rotate(45f)

        canvas.rotate(45f,100f,100f)

        stateTest(canvas)

        //图层向下合并
        canvas.restore()
    }

    /**
     * 平移画布
     */
    private fun translateCanvas(canvas: Canvas) {
        stateTest(canvas)

        canvas.save()
        canvas.translate(200f, 200f)
        stateTest(canvas)

        canvas.restore()
    }

    /**
     * 缩放画布
     */
    private fun scaleCanvas(canvas: Canvas) {
        stateTest(canvas)

        canvas.save()
        canvas.scale(2f,2f,100f,100f)
        mRedPaint.color = Color.parseColor("#880FB5FD")
        stateTest(canvas)
        canvas.restore()
    }

    /**
     * 斜切画布
     */
    private fun skewCanvas(canvas: Canvas) {
        stateTest(canvas)

        canvas.save()
        canvas.skew(1f, 0f)
        mRedPaint.color = Color.parseColor("#880FB5FD")
        stateTest(canvas)
        canvas.restore()
    }

    /**
     * 保存和合并图层
     */
    private fun saveRestoreCanvas(canvas: Canvas) {
        mRedPaint.textSize = 25f
        canvas.drawText(canvas.saveCount.toString(), 100f, 100f, mRedPaint)
        canvas.save()
        canvas.save()
        canvas.drawText(canvas.saveCount.toString(), 150f, 150f, mRedPaint)
        canvas.restore()
        canvas.drawText(canvas.saveCount.toString(), 200f, 200f, mRedPaint)
        canvas.restoreToCount(2)
        canvas.drawText(canvas.saveCount.toString(), 250f, 250f, mRedPaint)
        //canvas.restoreToCount(0)  //会报错，参数必须要大于0

    }

    /**
     * 裁剪画布
     */
    private fun clipCanvas(canvas: Canvas) {
        val rect = Rect(20, 100, 250, 300)

        //内裁剪
        //canvas.clipRect(rect)

        //外裁剪
        canvas.clipOutRect(rect)

        canvas.drawRect(0f, 0f, 200f, 300f, mRedPaint)
    }



}