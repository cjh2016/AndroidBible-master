package com.cjh.component_viewpaper.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * 当页卡是Fragment时：用FragmentPagerAdapter
 * FragmentPagerAdapter 数据源：List<Fragment>
 * @author: caijianhui
 * @date: 2019/8/8 10:03
 * @description:
 */
class MyFragmentPagerAdapter @JvmOverloads constructor(fm: FragmentManager, private val listFragments: List<Fragment>? = null): FragmentPagerAdapter(fm) {

    /**
     *  根据Item的位置返回对应位置的Fragment，绑定item和Fragment
     * */
    override fun getItem(position: Int): Fragment? {
        listFragments?.run {
            return get(position)
        }
        return null
    }

    /**
     *  设置Item的数量
     * */
    override fun getCount(): Int {
        return listFragments?.size ?: 0
    }
}