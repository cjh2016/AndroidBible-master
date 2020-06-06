package com.boll.lib_coremodel.adaptation.loadviewhelper

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.cjh.lib_basissdk.util.ViewUtils


/**
 * @author: caijianhui
 * @date: 2019/7/5 13:45
 * @description: 根据宽和高同时适配的转换类
 */
class BothLoadViewHelper(context: Context, designWidth: Int,designHeight: Int,
    designDpi: Int, fontSize: Float, unit: String): AbsLoadViewHelper(context, designWidth, designHeight, designDpi, fontSize, unit) {


    override fun installBenchMark(): Int {
        return BenchMark.BOTH
    }

    override fun loadWidthHeightFont(view: View) {
        val layoutParams = view.layoutParams
        if (layoutParams.width > 0) {
            layoutParams.width = setValue(layoutParams.width)
        }
        if (layoutParams.height > 0) {
            layoutParams.height = setValue(layoutParams.height, true)
        }
        loadViewFont(view)
    }

    override fun loadPadding(view: View) {
        view.setPadding(setValue(view.paddingLeft), setValue(view.paddingTop, true),
                setValue(view.paddingRight), setValue(view.paddingBottom, true))
    }

    override fun loadLayoutMargin(view: View) {
        val params = view.layoutParams
        val marginLayoutParams: ViewGroup.MarginLayoutParams
        if (params is ViewGroup.MarginLayoutParams) {
            marginLayoutParams = params
            marginLayoutParams.leftMargin = setValue(marginLayoutParams.leftMargin)
            marginLayoutParams.topMargin = setValue(marginLayoutParams.topMargin, true)
            marginLayoutParams.rightMargin = setValue(marginLayoutParams.rightMargin)
            marginLayoutParams.bottomMargin = setValue(marginLayoutParams.bottomMargin, true)
            view.layoutParams = marginLayoutParams
        }
    }

    override fun loadMaxWidthAndHeight(view: View) {
        ViewUtils.setMaxWidth(view, setValue(ViewUtils.getMaxWidth(view)))
        ViewUtils.setMaxHeight(view, setValue(ViewUtils.getMaxHeight(view), true))
    }

    override fun loadMinWidthAndHeight(view: View) {
        ViewUtils.setMinWidth(view, setValue(ViewUtils.getMinWidth(view)))
        ViewUtils.setMinHeight(view, setValue(ViewUtils.getMinHeight(view), true))
    }

}