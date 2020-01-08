package com.cjh.component_viewpaper.transformer

import android.view.View
import com.nineoldandroids.view.ViewHelper
import kotlin.math.abs

/**
 * @author: caijianhui
 * @date: 2019/8/8 20:17
 * @description:
 */
class ZoomInTransformer: BaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        val scale = if (position < 0) position.plus(1.0f) else abs(1.0f.minus(position))
        ViewHelper.setScaleX(page, scale)
        ViewHelper.setScaleY(page, scale)
        ViewHelper.setPivotX(page, page.width.times(0.5f))
        ViewHelper.setPivotY(page, page.height.times(0.5f))
        ViewHelper.setAlpha(page, if ((position < -1.0f) or (position > 1.0f)) 0.0f else 1.0f.minus(scale.minus(1.0f)))
    }
}