package com.cjh.lib_basissdk.util

import android.util.Log

/**
 * @author: caijianhui
 * @date: 2020/4/29 17:40
 * @description: 事件点击工具类
 */
object ClickUtil {

    private var lastClickTime: Long = 0
    private var lastViewId = -1
    private var DEFAULT_DIFF: Long = 1000 //时间间隔,单位毫秒

    /**
     * 判断两次点击的间隔，如果小于1s，则认为是多次无效点击（任意两个view，固定时长1s）
     *
     * @return
     */
    fun isFastDoubleClick(): Boolean {
        return isFastDoubleClick(-1, DEFAULT_DIFF)
    }

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击（任意两个view，自定义间隔时长）
     *
     * @return
     */
    fun isFastDoubleClick(diff: Long): Boolean {
        return isFastDoubleClick(-1, diff)
    }


    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击（同一view，自定义间隔时长）
     *
     * @param diff
     * @return
     */
    fun isFastDoubleClick(viewId: Int, diff: Long): Boolean {
        val time = System.currentTimeMillis()
        val timeDiff = time - lastClickTime
        if (lastViewId == viewId && lastClickTime > 0 && timeDiff < diff) {
            Log.d("isFastDoubleClick", "短时间内view被多次点击")
            return true
        }
        lastClickTime = time
        lastViewId = viewId
        return false
    }

}