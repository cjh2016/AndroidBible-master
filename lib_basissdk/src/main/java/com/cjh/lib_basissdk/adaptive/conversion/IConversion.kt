package com.boll.lib_coremodel.adaptation.conversion

import android.view.View
import com.boll.lib_coremodel.adaptation.loadviewhelper.AbsLoadViewHelper

/**
 * @author: caijianhui
 * @date: 2019/7/5 11:03
 * @description: 根据机型分辨率大小，密度自动转换成适配的宽和高等等属性的接口，具体属性可以自定义
 */
interface IConversion {

    fun transform(view: View, loadViewHelper: AbsLoadViewHelper)
}