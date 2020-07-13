package com.boll.lib_coremodel.adaptation.loadviewhelper

import android.view.View

/**
 * @author: caijianhui
 * @date: 2019/7/5 11:14
 * @description:  具体转换的属性的接口
 */
interface ILoadViewHelper {

    fun loadWidthHeightFont(view: View)

    fun loadPadding(view: View)

    fun loadLayoutMargin(view: View)

    fun loadMaxWidthAndHeight(view: View)

    fun loadMinWidthAndHeight(view: View)

    fun loadCustomAttrValue(px: Int): Int

}