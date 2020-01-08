package com.cjh.component_viewpaper.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * 当页卡是Fragment时：用FragmentPagerAdapter
 * 数据源：List<Fragment>
 * @author: caijianhui
 * @date: 2019/8/8 14:56
 * @description:
 */
class MyFragmentStatePagerAdapter(fm: FragmentManager, private val listFragments: List<Fragment>? = null): FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        listFragments?.let {
            return it[position]
        }
        return null
    }

    override fun getCount(): Int {
        return listFragments?.size ?: 0
    }
}