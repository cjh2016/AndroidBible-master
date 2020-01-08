package com.cjh.component_viewpaper.transformer

import android.support.v4.view.ViewPager
import android.view.View
import com.nineoldandroids.view.ViewHelper

/**
 * @author: caijianhui
 * @date: 2019/8/8 17:12
 * @description:
 */
abstract class BaseTransformer: ViewPager.PageTransformer {

    /**
     * Called each {@link #transformPage(View, float)}.
     *
     * @param page
     *            Apply the transformation to this page
     * @param position
     *            Position of page relative to the current front-and-center position of the pager. 0 is front and
     *            center. 1 is one full page position to the right, and -1 is one page position to the left.
     */
    protected abstract fun onTransform(page: View, position: Float)

    /**
     * Apply a property transformation to the given page. For most use cases, this method should not be overridden.
     * Instead use {@link #transformPage(View, float)} to perform typical transformations.
     *
     * @param page
     *            Apply the transformation to this page
     * @param position
     *            Position of page relative to the current front-and-center position of the pager. 0 is front and
     *            center. 1 is one full page position to the right, and -1 is one page position to the left.
     */
    override
    fun transformPage(page: View, position: Float) {
        onPreTransform(page, position)
        onTransform(page, position)
        onPostTransform(page, position)
    }

    /**
     * If the position offset of a fragment is less than negative one or greater than one, returning true will set the
     * fragment alpha to 0f. Otherwise fragment alpha is always defaulted to 1f.
     *
     * @return
     */
    open fun hideOffscreenPages(): Boolean {
        return true
    }

    /**
     * Indicates if the default animations of the view pager should be used.
     *
     * @return
     */
    open fun isPagingEnabled(): Boolean {
        return false
    }

    /**
     * Called each {@link #transformPage(View, float)} before {{@link #onTransform(View, float)}.
     * <p>
     * The default implementation attempts to reset all view properties. This is useful when toggling transforms that do
     * not modify the same page properties. For instance changing from a transformation that applies rotation to a
     * transformation that fades can inadvertently leave a fragment stuck with a rotation or with some degree of applied
     * alpha.
     *
     * @param page
     *            Apply the transformation to this page
     * @param position
     *            Position of page relative to the current front-and-center position of the pager. 0 is front and
     *            center. 1 is one full page position to the right, and -1 is one page position to the left.
     */
    open fun onPreTransform(page: View, position: Float) {
        val width = page.width
        //使用ViewHelper可以兼容3.0以下的机型
        ViewHelper.setRotationX(page, 0.0f)
        ViewHelper.setRotationY(page, 0.0f)
        ViewHelper.setRotation(page, 0.0f)
        ViewHelper.setScaleX(page, 1.0f)
        ViewHelper.setScaleY(page, 1.0f)
        ViewHelper.setPivotX(page, 0.0f)
        ViewHelper.setPivotY(page, 0.0f)
        ViewHelper.setTranslationY(page, 0.0f)
        ViewHelper.setTranslationX(page, if (isPagingEnabled()) 0.0f else (-width * position))

        if (hideOffscreenPages()) {
            ViewHelper.setAlpha(page, if (position <= -1.0f || position >= 1.0f) 0.0f else 1.0f)
            page.isEnabled = false
        } else {
            page.isEnabled = true
            ViewHelper.setAlpha(page, 1.0f)
        }
    }

    /**
     * Called each [.transformPage] after [.onTransform].
     *
     * @param page
     * Apply the transformation to this page
     * @param position
     * Position of page relative to the current front-and-center position of the pager. 0 is front and
     * center. 1 is one full page position to the right, and -1 is one page position to the left.
     */
    open fun onPostTransform(page: View, position: Float) {

    }

}