package com.boll.lib_coremodel.adaptation.conversion

import android.view.View
import com.boll.lib_coremodel.adaptation.loadviewhelper.AbsLoadViewHelper

/**
 * @author: caijianhui
 * @date: 2019/7/5 13:43
 * @description: 自定义要转换的属性，这里默认自动转换宽度，高度，字体，padding，margin，最大宽度，最大高度，
 *               最小宽度，最小高度
 */
class CustomConversion: IConversion {

    override fun transform(view: View, loadViewHelper: AbsLoadViewHelper) {
        if (view.layoutParams != null) {
            loadViewHelper.loadWidthHeightFont(view)
            loadViewHelper.loadPadding(view)
            loadViewHelper.loadLayoutMargin(view)
            loadViewHelper.loadMaxWidthAndHeight(view)
            loadViewHelper.loadMinWidthAndHeight(view)
        }
    }
}