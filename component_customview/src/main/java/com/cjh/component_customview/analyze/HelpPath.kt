package com.cjh.component_customview.analyze

import android.graphics.Path
import android.graphics.Point

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

}