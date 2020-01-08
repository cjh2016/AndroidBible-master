package com.cjh.component_viewpaper.transformer

import android.view.View
import com.nineoldandroids.view.ViewHelper
import kotlin.math.abs

/**
 * @author: caijianhui
 * @date: 2019/8/8 19:36
 * @description:
 */
class ZoomOutTransformer: BaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        val scale = 1.0f.plus(abs(position))
        ViewHelper.setScaleX(page, scale)
        ViewHelper.setScaleY(page, scale)
        ViewHelper.setPivotX(page, page.width.times(0.5f))
        ViewHelper.setPivotY(page, page.height.times(0.5f))
        ViewHelper.setAlpha(page, if (position < -1.0f || position > 1.0f) 0.0f else (1.0f - (scale - 1.0f)))
        if (-1.0f == position) {
            ViewHelper.setTranslationX(page, -page.width.toFloat())
        }

    }

}