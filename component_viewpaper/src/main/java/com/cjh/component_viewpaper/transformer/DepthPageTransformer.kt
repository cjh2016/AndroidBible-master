package com.cjh.component_viewpaper.transformer

import android.util.Log
import android.view.View
import com.nineoldandroids.view.ViewHelper
import kotlin.math.abs

/**
 * @author: caijianhui
 * @date: 2019/8/8 17:38
 * @description:
 */
class DepthPageTransformer: BaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        when {
            position <= 0.0f -> {
                ViewHelper.setTranslationX(page, 0.0f)
                ViewHelper.setScaleX(page, 1.0f)
                ViewHelper.setScaleY(page, 1.0f)
            }

            position <= 1.0f -> {
                Log.i(TAG, "alpha = ${1 - position} translationX = ${-page.width * position}")
                val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - abs(position))
                ViewHelper.setAlpha(page, 1 - position)
                ViewHelper.setPivotY(page, 0.5f * page.height)
                ViewHelper.setTranslationX(page, -page.width * position)
                ViewHelper.setScaleX(page, scaleFactor)
                ViewHelper.setScaleY(page, scaleFactor)
            }
        }
    }

    override fun isPagingEnabled(): Boolean {
        return true
    }

    companion object {
        const val MIN_SCALE = 0.75f
        val TAG = DepthPageTransformer.javaClass.simpleName
    }
}