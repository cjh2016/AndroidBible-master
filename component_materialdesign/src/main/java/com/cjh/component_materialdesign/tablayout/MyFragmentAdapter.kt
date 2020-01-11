package com.cjh.component_materialdesign.tablayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * @author: caijianhui
 * @date: 2020/1/11 16:52
 * @description:
 */
class MyFragmentAdapter(var fragmentManager: FragmentManager, var fragments: ArrayList<Fragment>): FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

}