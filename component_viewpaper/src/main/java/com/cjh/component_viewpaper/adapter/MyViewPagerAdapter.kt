package com.cjh.component_viewpaper.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

/**
 * 当页卡是View时：用PagerAdapter
 * PagerAdapter 数据源：List<View>
 * 直接继承PagerAdapter，至少必须重写下面的四个方法，否则会报错
 * @author: caijianhui
 * @date: 2019/8/8 9:39
 * @description:
 */
class MyViewPagerAdapter(private val listViews: List<View>?): PagerAdapter() {

    /**
     *  返回页卡的数量
     * */
    override fun getCount(): Int {
        return listViews?.size ?: 0
    }

    /**
     *  这个方法用来实例化页卡
     * */
    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        listViews?.let {
            container?.addView(it[position], 0)  //添加页卡
            return it[position]
        }
        return super.instantiateItem(container, position)
    }

    /**
     *  删除页卡
     * */
    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        listViews?.let {
            container?.removeView(it[position])
            return
        }
        super.destroyItem(container, position, `object`)
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`  //Google官方提示这样写
    }


}