package com.cjh.component_viewpaper.transformer

import android.view.View
import com.nineoldandroids.view.ViewHelper
import kotlin.math.abs
import kotlin.math.max

/**
 * @author: caijianhui
 * @date: 2019/8/8 19:52
 * @description:
 */
class ZoomOutSlideTransformer: BaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        if ((position >= -1) or (position <= 1)) {
            val height = page.height
            val scaleFactor = max(MIN_SCALE, 1 - abs(position))
            val vertMargin = height.times(1 - scaleFactor).div(2)
            val horzMargin = page.width.times(1 - scaleFactor).div(2)

            ViewHelper.setPivotY(page, 0.5f.times(height))

            if (position < 0) {
                ViewHelper.setTranslationX(page, horzMargin - vertMargin.div(2))

            } else {
                ViewHelper.setTranslationX(page, -horzMargin + vertMargin.div(2))
            }

            ViewHelper.setScaleX(page, scaleFactor)
            ViewHelper.setScaleY(page, scaleFactor)

            ViewHelper.setAlpha(page, MIN_ALPHA.plus(scaleFactor.minus(MIN_SCALE).div(1.0f.minus(MIN_SCALE)).times(1.0f.minus(MIN_ALPHA))))


        }
    }

    companion object {
        const val MIN_SCALE = 0.85f
        const val MIN_ALPHA = 0.5f
    }
}