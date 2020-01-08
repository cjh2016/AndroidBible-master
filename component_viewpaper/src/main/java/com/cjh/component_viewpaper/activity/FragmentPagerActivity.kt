package com.cjh.component_viewpaper.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.cjh.component_viewpaper.listener.MyOnPageChangeListener
import com.cjh.component_viewpaper.R
import com.cjh.component_viewpaper.adapter.MyFragmentPagerAdapter
import com.cjh.component_viewpaper.fragment.Fragment1
import com.cjh.component_viewpaper.fragment.Fragment2
import java.util.ArrayList

/**
 * @author: caijianhui
 * @date: 2019/8/8 12:20
 * @description:
 */
class FragmentPagerActivity: AppCompatActivity() {

    var viewPager: ViewPager? = null

    private val fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        viewPager = findViewById(R.id.view_pager)

        initFragmentViewPager()
    }

    private fun initFragmentViewPager() {

        fragments.add(Fragment1())
        fragments.add(Fragment2())

        viewPager?.run {
            adapter = MyFragmentPagerAdapter(supportFragmentManager, fragments)
            currentItem = 0
            addOnPageChangeListener(MyOnPageChangeListener())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}