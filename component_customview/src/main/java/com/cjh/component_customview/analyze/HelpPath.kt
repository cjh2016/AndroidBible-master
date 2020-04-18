package com.cjh.component_customview.analyze

import android.graphics.Path
import android.graphics.Point
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author: caijianhui
 * @date: 2020/4/14 20:10
 * @description: 辅助分析路径
 */
object HelpPath {

    /**
     * 绘制网格:注意只有用path才能绘制虚线
     *
     * @param step    小正方形边长
     * @param winSize 屏幕尺寸
     */
    fun gridPath(step: Int, winSize: Point): Path {
        val path = Path()

        //画水平虚线
        for (i in 0..(winSize.y / step + 1)) {
            path.moveTo(0f, (step * i).toFloat())
            path.lineTo(winSize.x.toFloat(), (step*i).toFloat())
        }
        //画垂直虚线
        for (i in 0..(winSize.x / step + 1)) {
            path.moveTo((step*i).toFloat(), 0f)
            path.lineTo((step*i).toFloat(), (winSize.y).toFloat())
        }
        return path
    }

    /**
     * 坐标系路径
     *
     * @param coo     坐标点
     * @param winSize 屏幕尺寸
     * @return 坐标系路径
     */
    fun cooPath(coo: Point, winSize: Point): Path {
        val path = Path()

        //x正半轴线
        path.moveTo(coo.x.toFloat(), coo.y.toFloat())
        path.lineTo(winSize.x.toFloat(), coo.y.toFloat())
        //x负半轴线
        path.moveTo(coo.x.toFloat(), coo.y.toFloat())
        path.lineTo(coo.x.toFloat() - winSize.x, coo.y.toFloat())
        //y负半轴线
        path.moveTo(coo.x.toFloat(), coo.y.toFloat())
        path.lineTo(coo.x.toFloat(), coo.y.toFloat() - winSize.y)
        //y正半轴线
        path.moveTo(coo.x.toFloat(), coo.y.toFloat())
        path.lineTo(coo.x.toFloat(), winSize.y.toFloat())

        return path
    }

    /**
     * 画正n边形的路径
     *
     * @param num 边数
     * @param R   外接圆半径
     * @return 画正n边形的路径
     */
    fun regularPolygonPath(num: Int, R: Float): Path {
        val r = R * (cos(rad((360 / num / 2).toFloat()))) //!!一点解决
        return nStarPath(num, R, r)
    }


    /**
     * 画正n角星的路径:
     *
     * @param num 角数
     * @param R   外接圆半径
     * @return 画正n角星的路径
     */
    fun regularStarPath(num: Int, R: Float): Path {
        var degA: Float
        var degB: Float
        if (num % 2 == 1) {
            //奇数和偶数角区别对待
            degA = (360 / num / 2 / 2).toFloat()
            degB = (180 - degA - 360 / num / 2)
        } else {
            degA = (360 / num / 2).toFloat()
            degB = 180 - degA - 360 / num / 2
        }
        val r = (R * sin(rad(degA) / sin(rad(degB))))
        return nStarPath(num, R, r)
    }

    /**
     * n角星路径
     *
     * @param num 几角星
     * @param R   外接圆半径
     * @param r   内接圆半径
     * @return n角星路径
     */
    fun nStarPath(num: Int, R: Float, r: Float): Path {
        val path = Path()
        var perDeg = 360 / num
        var degA = perDeg / 2 / 2
        var degB = 360 / (num - 1) / 2 - degA / 2 + degA
        path.moveTo(
            (cos(rad((degA + perDeg * 0).toFloat())) * R + R * cos(rad(degA.toFloat()))),
            (-sin(rad((degA + perDeg * 0).toFloat())) * R + R))

        for (i in 0 until num) {
            path.lineTo(
                (cos(rad((degA + perDeg * i).toFloat()).toDouble()) * R + R * cos(rad(degA.toFloat()).toDouble())).toFloat(),
                (-sin(rad((degA + perDeg * i).toFloat()).toDouble()) * R + R).toFloat()
            )
            path.lineTo(
                (cos(rad((degB + perDeg * i).toFloat()).toDouble()) * r + R * cos(rad(degA.toFloat()).toDouble())).toFloat(),
                (-sin(rad((degB + perDeg * i).toFloat()).toDouble()) * r + R).toFloat()
            )
        }

        path.close()
        return path
    }

    /**
     * 角度制化为弧度制
     *
     * @param deg 角度
     * @return 弧度
     */
    fun rad(deg: Float): Float {
        return (deg * Math.PI / 180).toFloat()
    }

}