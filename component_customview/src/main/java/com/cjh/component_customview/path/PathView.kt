package com.cjh.component_customview.path

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.cjh.component_customview.analyze.HelpDraw
import com.cjh.component_customview.analyze.HelpPath


/**
 * @author: caijianhui
 * @date: 2020/4/16 14:08
 * @description:
 */
class PathView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = -1
) :
    View(context, attrs, -1) {

    lateinit var mCoo: Point

    lateinit var mPathPaint: Paint


    init {
        init()
    }

    private fun init() {
        mCoo = Point(250, 200)

        mPathPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPathPaint.style = Paint.Style.STROKE
        mPathPaint.textSize = 10f
        mPathPaint.color = Color.BLUE
        mPathPaint.strokeWidth = 6f

        //initPathMeasureX()


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


        //测试Path系列

        //测试画正n边形
        /*drawRegularPolygonPath(canvas, 3)

        canvas.translate(120f, 0f)
        drawRegularPolygonPath(canvas, 4)

        canvas.translate(120f, 0f)
        drawRegularPolygonPath(canvas, 5)

        canvas.translate(120f, 0f)
        drawRegularPolygonPath(canvas, 6)

        canvas.translate(120f, 0f)
        drawRegularPolygonPath(canvas, 7)

        canvas.translate(120f, 0f)
        drawRegularPolygonPath(canvas, 8)*/


        //测试画正n角星
        /*drawRegularStarPath(canvas, 3)

        canvas.translate(120f, 0f)
        drawRegularStarPath(canvas, 4)

        canvas.translate(120f, 0f)
        drawRegularStarPath(canvas, 5)

        canvas.translate(120f, 0f)
        drawRegularStarPath(canvas, 6)

        canvas.translate(120f, 0f)
        drawRegularStarPath(canvas, 7)

        canvas.translate(120f, 0f)
        drawRegularStarPath(canvas, 8)*/


        //
        //moveLineClosePath(canvas)


        //
        //rMoveRLineClosePath(canvas)

        //
        //moveArcPath(canvas)

        //测试添加矩形系列path
        //addRectX(canvas)

        //测试添加类圆系列path
        //addLikeCircle(canvas)


        //测试添加path系列path
        //addPathX(canvas)

        //测试path其他api
        //otherPath(canvas)

        //测试setLastPoint
        //cwCCWPath(canvas)

        //计算边界
        //computeBounds(canvas)

        //测试顺时针逆时针
        //cwCCWPath2(canvas)

        //填充的环绕原则
        //setFillTypeX(canvas)

        //布尔运算OP：(两个路径之间的运算)
        //opX(canvas)


        //
        //pathMeasureX(canvas)

        canvas.restore()
    }


    /**
     * 画正n边形的路径
     * @param canvas
     * @param nStar 边数
     */
    private fun drawRegularPolygonPath(canvas: Canvas, nStar: Int) {
        canvas.drawPath(HelpPath.regularPolygonPath(nStar, 50f), mPathPaint)
    }

    /**
     * 画正n角星
     * @param canvas
     * @param nStar 角数
     */
    private fun drawRegularStarPath(canvas: Canvas, nStar: Int) {
        canvas.drawPath(HelpPath.regularStarPath(nStar, 50f), mPathPaint)
    }

    /**
     *
     */
    private fun moveLineClosePath(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, 0f)
        //这里是指x的位置为100，y的位置为200，指的是具体的位置
        path.lineTo(100f, 200f)
        //这里是指x的位置为200，y的位置为100，指的是具体的位置
        path.lineTo(200f, 100f)
        path.close()
        canvas.drawPath(path, mPathPaint)
    }

    /**
     * 点的参考系从canvas左上角移变成路径尾部
     */
    private fun rMoveRLineClosePath(canvas: Canvas) {
        val path = Path()
        path.rMoveTo(0f, 0f)
        //这里是指x的方向加100，y的方向加200，指的是增量
        path.rLineTo(100f, 200f)  //参考起点为（0,0）
        //这里是指x的方向加200，y的方向加100，指的是增量
        path.rLineTo(200f, 100f)  //参考起点为(100,200)
        path.close()

        path.rMoveTo(50f, 50f)
        path.rLineTo(10f, 50f)
        path.rLineTo(50f, 10f)
        path.close()
        canvas.drawPath(path, mPathPaint)
    }

    /**
     *
     */
    private fun moveArcPath(canvas: Canvas) {
        val path = Path()
        val rectF = RectF(100f, 100f, 500f, 300f)

        path.moveTo(0f, 0f)
        //arcTo(矩形范围，起点，终点，是否独立--默认false)
        //path.arcTo(rectF, 0f,45f, false)
        path.arcTo(rectF, 0f, 45f, true)
        canvas.drawPath(path, mPathPaint)
    }

    /**
     * 添加矩形系列Path
     */
    private fun addRectX(canvas: Canvas) {
        val path = Path()
        val rectF = RectF(100f, 100f, 500f, 300f)

        //普通矩形
        //path.addRect(rectF, Path.Direction.CW)

        //圆角矩形
        //path.addRoundRect(rectF, 50f, 50f, Path.Direction.CW)

        //用4点控制圆角
        path.addRoundRect(
            rectF, floatArrayOf(
                150f, 150f,   //左上圆角x,y
                0f, 0f,       //右上圆角x,y
                450f, 250f,   //右下圆角x,y
                250f, 200f    //左下圆角x,y
            ), Path.Direction.CW
        )

        canvas.drawPath(path, mPathPaint)
    }

    /**
     * 添加类圆path系列
     */
    private fun addLikeCircle(canvas: Canvas) {
        val path = Path()
        val rectF = RectF(100f, 100f, 500f, 300f)


        //添加圆形
        //path.addCircle(100f, 100f, 100f, Path.Direction.CW)

        //添加椭圆
        //path.addOval(rectF, Path.Direction.CW)

        //添加弧线
        path.addArc(rectF, 0f, 145f)

        canvas.drawPath(path, mPathPaint)
    }

    /**
     * 添加path系列
     */
    private fun addPathX(canvas: Canvas) {

        val path = Path()
        val otherPath = Path()

        val rectF = RectF(100f, 100f, 500f, 300f)
        path.addCircle(100f, 100f, 100f, Path.Direction.CW)

        otherPath.moveTo(0f, 0f)
        otherPath.lineTo(100f, 100f)

        //普通添加addPath(Path)
        //path.addPath(otherPath)

        //偏移添加：addPath(Path，偏移x,偏移y)
        //path.addPath(otherPath, 200f, 200f)


        //矩阵变换添加：addPath(Path，Matrix)
        val matrix = Matrix()
        matrix.setValues(
            floatArrayOf(
                1f, 0f, 100f,
                0f, .5f, 150f,
                0f, 0f, 1f
            )
        )
        path.addPath(otherPath, matrix)


        canvas.drawPath(path, mPathPaint)
    }


    private fun otherPath(canvas: Canvas) {
        val path = Path()
        val rectF = RectF(100f, 100f, 500f, 300f)
        val otherRectF = RectF(600f, 100f, 800f, 300f)

        val otherPath = Path()

        val matrix = Matrix()
        matrix.setValues(
            floatArrayOf(
                1f, 0f, 100f,
                0f, 1f, 150f,
                0f, 0f, 1f
            )
        )

        path.addRect(rectF, Path.Direction.CW)
        otherPath.addRect(otherRectF, Path.Direction.CW)


        //path.reset()//清空path,保留填充类型
        //path.rewind()//清空path,保留数据结构

        Log.e(TAG, "isEmpty = ${path.isEmpty}") //是否为空
        Log.e(TAG, "isRect = ${path.isRect(rectF)}") //是否为矩形
        Log.e(TAG, "isConvex = ${path.isConvex}") //判断path是否为凸多边形，如果是就为true，反之为false
        Log.e(TAG, "isInverseFillType = ${path.isInverseFillType}") //TODO

        //path.set(otherPath)//清空path后添加otherPath

        //path.offset(200f,200f)//平移
        path.transform(matrix)//矩阵变换

        val tempPath = Path()

        //path.offset(200f, 200f, tempPath)//基于path平移注入tempPath，path不变

        path.transform(matrix, tempPath)//基于path变换注入tempPath，path不变

        canvas.drawPath(path, mPathPaint);
        canvas.drawPath(tempPath, mPathPaint);

    }

    /**
     *
     */
    private fun cwCCWPath(canvas: Canvas) {
        val path = Path()
        val rectF = RectF(100f, 100f, 500f, 300f)

        //path.addRect(rectF, Path.Direction.CW) //顺时针画矩形
        path.addRect(rectF,Path.Direction.CCW) //顺时针画矩形

        path.setLastPoint(200f, 200f)
        canvas.drawPath(path, mPathPaint)
    }

    /**
     * 计算边界
     */
    private fun computeBounds(canvas: Canvas) {
        val starPath = HelpPath.regularStarPath(6, 50f)
        val rectF = RectF() //自备矩形区域

        starPath.computeBounds(rectF, true)
        canvas.drawPath(starPath, mPathPaint)
        canvas.drawRect(rectF, mPathPaint)
    }

    /**
     * 顺时针逆时针
     */
    private fun cwCCWPath2(canvas: Canvas) {
        val path = Path()
        mPathPaint.style = Paint.Style.FILL
        val rectF = RectF(100f, 100f, 500f, 300f)
        path.addRect(rectF, Path.Direction.CW)
        //path.addRect(200f, 0f, 400f, 400f, Path.Direction.CW)
        path.addRect(200f, 0f, 400f, 400f, Path.Direction.CCW)
        canvas.drawPath(path, mPathPaint)
    }

    /**
     * 填充的环绕原则
     */
    private fun setFillTypeX(canvas: Canvas) {

        mPathPaint.style=Paint.Style.FILL

        val path = Path()
        path.moveTo(100f, 200f)
        path.lineTo(500f,200f)
        path.lineTo(200f,400f)
        path.lineTo(300f,50f)
        path.lineTo(400f, 400f)
        path.close()

        //非零环绕原则(WINDING) 默认
        //path.fillType = Path.FillType.WINDING

        //反零环绕原则(INVERSE_WINDING)
        //path.fillType = Path.FillType.INVERSE_WINDING

        //奇偶环绕原则(EVEN_ODD)
        //path.fillType = Path.FillType.EVEN_ODD

        //反奇偶环绕原则(INVERSE_EVEN_ODD)
        path.fillType = Path.FillType.INVERSE_EVEN_ODD

        canvas.drawPath(path, mPathPaint)

    }

    /**
     * 布尔运算OP：(两个路径之间的运算)
     */
    private fun opX(canvas: Canvas) {
        mPathPaint.style = Paint.Style.FILL

        val right = Path()
        val left = Path()
        left.addCircle(0f, 0f, 100f, Path.Direction.CW)
        right.addCircle(100f, 0f, 100f, Path.Direction.CW)


//        left.op(right, Path.Op.DIFFERENCE) //差集----晕，咬了一口硫酸
//        left.op(right, Path.Op.REVERSE_DIFFERENCE) //反差集----赔了夫人又折兵
//        left.op(right, Path.Op.INTERSECT) //交集----与你不同的都不是我
//        left.op(right, Path.Op.UNION) //并集----在一起，在一起
        left.op(right, Path.Op.XOR) //异或集---我恨你，我也恨你

        canvas.drawPath(left, mPathPaint)

    }

    /**
     * Path动画：PathMeasure
     */
    private fun initPathMeasureX() {
        val starPath=HelpPath.regularStarPath(5, 50f)
        val pathMeasure = PathMeasure(starPath, true)
        val pathAnimator = ValueAnimator.ofFloat(1f, 0f)
        pathAnimator.duration = 5000L

        pathAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            val effect = DashPathEffect(
                floatArrayOf(pathMeasure.length, pathMeasure.length),
                value * pathMeasure.length
            )
            mPathPaint.pathEffect = effect
            invalidate()
        }
        pathAnimator.start()
    }

    private fun pathMeasureX(canvas: Canvas) {
        canvas.drawPath(HelpPath.regularStarPath(5, 50f), mPathPaint)
    }

    companion object {
        val TAG = PathView::class.java.simpleName
    }



}