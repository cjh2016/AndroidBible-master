package com.boll.lib_coremodel.adaptation.conversion

import android.view.View
import com.boll.lib_coremodel.adaptation.loadviewhelper.AbsLoadViewHelper

/**
 * @author: caijianhui
 * @date: 2019/7/5 11:29
 * @description: 简单的转换接口，只转换宽度，高度，字体，padding和margin
 */
class SimpleConversion: IConversion {

    override fun transform(view: View, loadViewHelper: AbsLoadViewHelper) {
        if (view.layoutParams != null) {
            loadViewHelper.loadWidthHeightFont(view)
            loadViewHelper.loadPadding(view)
            loadViewHelper.loadLayoutMargin(view)
        }
    }
}