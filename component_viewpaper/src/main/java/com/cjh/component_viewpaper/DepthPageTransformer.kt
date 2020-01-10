package com.cjh.component_viewpaper

import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.nineoldandroids.view.ViewHelper

/**
 * 参考连接 {@link http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/1030/1870.html}
 * @author: caijianhui
 * @date: 2019/8/8 15:38
 * @description:
 */
class DepthPageTransformer: ViewPager.PageTransformer {


    /**
     *  注意这个方法中的page是会变的
     *  当我们滑动时：会打印出当前ViewPager中存活的每个View以及它们的position的变化~~注意是每一个，
     *  所以建议别只log position，不然你会觉得莫名其妙的输出~~
     * */
    override fun transformPage(page: View, position: Float) {
        Log.i(TAG, "DepthPageTransformer transformPage position = $position  page = $page")
        val pageWidth = page.width
        page?.run {
            when {
                position < -1 -> {   // [-Infinity, -1)  表示当前的View处在屏幕的左边
                    ViewHelper.setAlpha(this, 0.0f)  //用ViewHelper可以兼容版本低于3.0的机器
                }

                position <= 0 -> {   // [-1, 0]  表示当前的View是从左屏幕出发到完全出现在第一屏或者从第一屏出发到完全滑出左屏幕
                    ViewHelper.setAlpha(this, 1.0f)
                    ViewHelper.setTranslationX(this, 0.0f)
                    ViewHelper.setScaleX(this, 1.0f)
                    ViewHelper.setScaleY(this, 1.0f)
                }

                position <= 1 -> {   // (0, 1]  表示当前的View是从右屏幕出发到完全出现在第一屏或者从第一屏出发到完全滑出右屏幕
                    ViewHelper.setAlpha(this, 1 - position)
                    ViewHelper.setTranslationX(this, -(pageWidth?.times(position))!!)
                    val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - position)
                    ViewHelper.setScaleX(this, scaleFactor)
                    ViewHelper.setScaleY(this, scaleFactor)
                }

                else -> {   // (1, +Infinity]  表示当前的View处在屏幕的右边
                    ViewHelper.setAlpha(this, 1.0f)
                }
            }
        }

    }

    companion object {
        val TAG: String = DepthPageTransformer.javaClass.simpleName
        const val MIN_SCALE = 0.75f
    }
}