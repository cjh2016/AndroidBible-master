package com.cjh.component_viewpaper.listener

import android.support.v4.view.ViewPager
import android.util.Log

/**
 * @author: caijianhui
 * @date: 2019/8/8 14:23
 * @description:
 */
class MyOnPageChangeListener: ViewPager.OnPageChangeListener {

    /**
     *  方法调用：页面状态改变时
     *  参数说明：state = 页面状态，页面状态分别是：
     *  SCROLL_STATE_IDLE：空闲状态
     *  SCROLL_STATE_DRAGGING：滑动状态
     *  SCROLL_STATE_SETTLING：滑动后自然沉降的状态
     *
     * */
    override fun onPageScrollStateChanged(state: Int) {
        when(state) {  //state: 页面状态
            ViewPager.SCROLL_STATE_IDLE -> {  //空闲状态
                Log.i(TAG, "onPageScrollStateChanged state = SCROLL_STATE_IDLE ==空闲状态")
            }
            ViewPager.SCROLL_STATE_DRAGGING -> {  //滑动状态
                Log.i(TAG, "onPageScrollStateChanged state = SCROLL_STATE_DRAGGING ==正在滑动")
            }
            ViewPager.SCROLL_STATE_SETTLING -> {  //滑动后自然沉降的状态
                Log.i(TAG, "onPageScrollStateChanged state = SCROLL_STATE_SETTLING ==自然沉降")
            }
        }
    }

    /**
     *  方法调用：当页面在滑动时至滑动被停止之前，此方法会一直调用
     *  position: 当前页面，及你点击滑动的页面
     *  positionOffset: 当前页面偏移的百分比
     *  positionOffsetPixels: 当前页面偏移的像素位置
     * */
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        Log.i(TAG, "onPageScrolled ==滑动中== 滑动前页面位置是：position = $position positionOffset = $positionOffset positionOffsetPixels = $positionOffsetPixels")
    }

    /**
     *  方法调用：页面跳转完后调用
     *  参数说明：position = 当前选中页面的Position（位置编号）
     * 
     * */
    override fun onPageSelected(position: Int) {
        Log.i(TAG, "onPageSelected ==滑动后==页面停留位置是：position = $position")
    }

    companion object {
        val TAG: String = MyOnPageChangeListener::class.java.simpleName
    }
}