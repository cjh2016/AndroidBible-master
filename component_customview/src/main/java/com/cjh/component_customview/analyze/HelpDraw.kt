package com.cjh.component_customview.analyze

import android.content.Context
import android.graphics.*


/**
 * @author: caijianhui
 * @date: 2020/4/15 9:41
 * @description:辅助画布
 */
object HelpDraw {

    val isDraw = true

    /**
     * 绘制picture
     *
     * @param canvas
     * @param pictures
     */
    fun draw(canvas: Canvas, vararg pictures: Picture) {
        if (!isDraw) {
            return
        }
        for (picture in pictures) {
            picture.draw(canvas)
        }
    }

    fun getWinSize(context: Context): Point {
        val point = Point()
        Utils.loadWinSize(context, point)
        return point
    }

    /**
     * 绘制网格
     */
    fun getGrid(context: Context): Picture {
        return getGrid(getWinSize(context))
    }


    private fun getGrid(winSize: Point): Picture {
        val picture = Picture()
        val recording = picture.beginRecording(winSize.x, winSize.y)
        val paint = getHelpPaint()
        recording.drawPath(HelpPath.gridPath(50, winSize), paint)
        return picture
    }

    /**
     * 绘制坐标系
     */
    fun getCoo(context: Context, coo: Point): Picture {
        return getCoo(coo, getWinSize(context))
    }

    /**
     * 绘制点集
     *
     * @param canvas 画布
     * @param paint  画笔
     * @param points 点集
     */
    fun drawPos(canvas: Canvas, paint: Paint, vararg points: Point) {
        for (point in points) {
            canvas.drawPoint(point.x.toFloat(), point.y.toFloat(), paint)
        }
    }

    /**
     * 绘制点集
     *
     * @param canvas 画布
     * @param paint  画笔
     * @param points 点集
     */
    fun drawPos(canvas: Canvas, paint: Paint, vararg points: PointF) {
        for (point in points) {
            canvas.drawPoint(point.x, point.y, paint)
        }
    }

    /**
     * 绘制点集
     *
     * @param canvas 画布
     * @param paint  画笔
     * @param points 点集
     */
    fun drawLines(canvas: Canvas, paint: Paint, vararg points: Point) {
        for (i in points.indices step 2) {
            if (i > points.size - 2) {
                return
            }
            canvas.drawLine(
                points[i].x.toFloat(),
                points[i].y.toFloat(),
                points[i + 1].x.toFloat(),
                points[i + 1].y.toFloat(),
                paint
            )
        }
    }


    /**
     * 绘制点集
     *
     * @param canvas 画布
     * @param paint  画笔
     * @param points 点集
     */
    fun drawLines(canvas: Canvas, paint: Paint, vararg points: PointF) {
        for (i in points.indices step 2) {
            if (i > points.size - 2) {
                return
            }
            canvas.drawLine(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y, paint)
        }
    }

    fun getHelpPaint(color: Int = Color.GRAY): Paint {
        //初始化网格画笔
        val paint = Paint()
        paint.strokeWidth = 2F
        paint.color = color
        paint.textSize = 50f
        paint.strokeCap = Paint.Cap.ROUND
        paint.style = Paint.Style.STROKE
        //设置虚线效果new float[]{可见长度，不可见长度}, 偏移值
        paint.pathEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)

        return paint

    }

    /**
     * 绘制坐标系
     *
     * @param coo     坐标系原点
     * @param winSize 屏幕尺寸
     */
    private fun getCoo(coo: Point, winSize: Point): Picture {
        val picture = Picture()
        val recording = picture.beginRecording(winSize.x, winSize.y)
        //初始化网格画笔
        val paint = Paint()
        paint.strokeWidth = 4f
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        //去除虚线效果new float[可见长度，不可见长度],偏移值
        paint.pathEffect = null
        //绘制直线
        recording.drawPath(HelpPath.cooPath(coo, winSize), paint)
        //左箭头
        recording.drawLine(
            winSize.x.toFloat(),
            coo.y.toFloat(),
            winSize.x.toFloat() - 40,
            coo.y.toFloat() - 20,
            paint
        )
        recording.drawLine(
            winSize.x.toFloat(),
            coo.y.toFloat(),
            winSize.x.toFloat() - 40,
            coo.y.toFloat() + 20,
            paint
        )
        //下箭头
        recording.drawLine(
            coo.x.toFloat(),
            winSize.y.toFloat(),
            coo.x.toFloat() - 20,
            winSize.y.toFloat() - 40,
            paint

        )
        recording.drawLine(
            coo.x.toFloat(),
            winSize.y.toFloat(),
            coo.x.toFloat() + 20,
            winSize.y.toFloat() - 40,
            paint

        )
        //为坐标绘制文字
        drawText4Coo(recording, coo, winSize, paint)

        return picture
    }

    /**
     * 为坐标系绘制文字
     *
     * @param canvas  画布
     * @param coo     坐标系原点
     * @param winSize 屏幕尺寸
     * @param paint   画笔
     */
    private fun drawText4Coo(canvas: Canvas, coo: Point, winSize: Point, paint: Paint) {
        //绘制文字
        paint.textSize = 50f
        canvas.drawText("x", winSize.x.toFloat() - 60, coo.y.toFloat() - 40, paint)
        canvas.drawText("y", coo.x.toFloat() - 40, winSize.y.toFloat() - 60, paint)
        paint.textSize = 25f
        //X正轴文字
        for (i in 1 until ((winSize.x-coo.x)/50)) {
            paint.strokeWidth = 2f
            canvas.drawText(
                (100 * i).toString(),
                coo.x.toFloat() - 20 + 100 * i,
                coo.y.toFloat() + 40,
                paint
            )
            paint.strokeWidth = 5f
            canvas.drawLine(
                coo.x.toFloat() + 100 * i,
                coo.y.toFloat(),
                coo.x.toFloat() + 100 * i,
                coo.y.toFloat() - 10,

                paint
            )
        }


        //X负轴文字
        for (i in 1 until ((winSize.x-coo.x)/50)) {
            paint.strokeWidth = 2f
            canvas.drawText(
                (-100 * i).toString(),
                coo.x.toFloat() - 20 - 100 * i,
                coo.y.toFloat() + 40,
                paint
            )
            paint.strokeWidth = 5f
            canvas.drawLine(
                coo.x.toFloat() - 100 * i,
                coo.y.toFloat(),
                coo.x.toFloat() - 100 * i,
                coo.y.toFloat() - 10,

                paint
            )
        }

        //Y正轴文字
        for (i in 1 until (winSize.y - coo.y) / 50) {
            paint.strokeWidth = 2f
            canvas.drawText(
                (100 * i).toString(),
                (coo.x + 20).toFloat(),
                (coo.y + 10 + 100 * i).toFloat(), paint
            )
            paint.strokeWidth = 5f
            canvas.drawLine(
                coo.x.toFloat(),
                (coo.y + 100 * i).toFloat(),
                (coo.x + 10).toFloat(),
                (coo.y + 100 * i).toFloat(), paint
            )
        }

        //Y负轴文字
        for (i in 1 until coo.y / 50) {
            paint.strokeWidth = 2f
            canvas.drawText(
                (-100 * i).toString(),
                (coo.x + 20).toFloat(),
                (coo.y + 10 - 100 * i).toFloat(), paint
            )
            paint.strokeWidth = 5f
            canvas.drawLine(
                coo.x.toFloat(),
                (coo.y - 100 * i).toFloat(),
                (coo.x + 10).toFloat(),
                (coo.y - 100 * i).toFloat(), paint
            )
        }

    }


}