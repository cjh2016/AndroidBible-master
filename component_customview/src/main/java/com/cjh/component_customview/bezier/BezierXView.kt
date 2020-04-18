package com.cjh.component_customview.bezier

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.cjh.component_customview.analyze.HelpDraw
import com.cjh.component_customview.analyze.HelpPath
import com.cjh.component_customview.analyze.Utils

/**
 * @author: caijianhui
 * @date: 2020/4/17 11:05
 * @description:贝塞尔曲线
 */
class BezierXView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = -1) :
    View(context, attrs, defStyle) {


    lateinit var mHelpPaint: Paint  //辅助画笔
    lateinit var mPaint: Paint      //贝塞尔曲线画笔
    lateinit var mBezierPath: Path  //贝尔塞曲线画笔

    lateinit var start: PointF      //起点
    lateinit var end: PointF        //终点
    lateinit var control: PointF


    lateinit var mPicture: Picture  //坐标系和网格的Canvas元件
    lateinit var mCoo: Point        //坐标系




    init {
        init()
    }

    private fun init() {

        start = PointF(0f, 0f)
        end = PointF(400f, 0f)
        control = PointF(200f, 200f)


        //贝塞尔曲线画笔
        mPaint = Paint()
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.parseColor("#88EC17F3")
        mPaint.strokeWidth = 8f

        //辅助线画笔
        resetHelpPaint()
        recordBg()//初始化时录制坐标系和网络--避免在OnDraw里重复调用
        mBezierPath=Path()
    }

    /**
     * 初始化时录制坐标系和网络--避免在OnDraw里重复调用
     */
    private fun recordBg() {
        //准备屏幕尺寸
        val winSize = Point()
        mCoo = Point(200, 200)
        Utils.loadWinSize(context, winSize)
        val gridPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        gridPaint.color = Color.RED
        gridPaint.strokeWidth = 2f
        gridPaint.style = Paint.Style.STROKE
        gridPaint.pathEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        gridPaint.strokeCap = Paint.Cap.ROUND


        mPicture = Picture()
        val recordCanvas = mPicture.beginRecording(winSize.x, winSize.y)
        //绘制辅助网格
        recordCanvas.drawPath(HelpPath.gridPath(50, winSize), gridPaint)
        //绘制坐标系
        recordCanvas.drawPath(HelpPath.cooPath(mCoo, winSize), gridPaint)
        mPicture.endRecording()
    }

    /**
     * 重置辅助画笔
     */
    private fun resetHelpPaint() {
        mHelpPaint = Paint()
        mHelpPaint.color = Color.BLUE
        mHelpPaint.strokeWidth = 2f
        mHelpPaint.style = Paint.Style.STROKE
        mHelpPaint.pathEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        mHelpPaint.strokeCap = Paint.Cap.ROUND
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //根据触摸位置更新控制点，并提示重绘
        Log.e(TAG, "event.x=${event.x}, event.y=${event.y}")
        control.x = event.x - mCoo.x
        control.y = event.y - mCoo.y
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //背景色
        canvas.drawRGB(224, 247, 245);

        //绘制表格
        HelpDraw.draw(canvas, HelpDraw.getGrid(context))
        //绘制坐标系
        HelpDraw.draw(canvas, HelpDraw.getCoo(context, mCoo))

        canvas.save()
        canvas.translate(mCoo.x.toFloat(), mCoo.y.toFloat())
        drawHelpElement(canvas)
        mBezierPath.moveTo(start.x, start.y)
        mBezierPath.quadTo(control.x, control.y, end.x, end.y)
        canvas.drawPath(mBezierPath, mPaint)
        mBezierPath.reset()
        canvas.restore()
    }

    /**
     *
     */
    private fun drawHelpElement(canvas: Canvas) {
        //绘制数据点和控制点
        mHelpPaint.color = Color.parseColor("#8820ECE2")
        canvas.drawPoint(start.x, start.y, mHelpPaint)
        canvas.drawPoint(end.x, end.y, mHelpPaint)
        canvas.drawPoint(control.x, control.y, mHelpPaint)


        //绘制辅助线
        resetHelpPaint()
        canvas.drawLine(start.x, start.y, control.x, control.y, mHelpPaint)
        canvas.drawLine(end.x, end.y, control.x, control.y, mHelpPaint)

    }

    companion object{
        val TAG = BezierXView::class.java.simpleName

    }

}