package com.cjh.component_viewpaper.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.cjh.component_viewpaper.listener.MyOnPageChangeListener
import com.cjh.component_viewpaper.R
import com.cjh.component_viewpaper.adapter.MyFragmentStatePagerAdapter
import com.cjh.component_viewpaper.fragment.Fragment1
import com.cjh.component_viewpaper.fragment.Fragment2
import java.util.ArrayList

/**
 * @author: caijianhui
 * @date: 2019/8/8 15:16
 * @description:
 */
class FragmentStatePagerActivity: AppCompatActivity() {

    var viewPager: ViewPager? = null

    private val fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        viewPager = findViewById(R.id.view_pager)

        initFragmentStateViewPager()
    }

    private fun initFragmentStateViewPager() {

        fragments.add(Fragment1())
        fragments.add(Fragment2())

        viewPager?.run {
            adapter = MyFragmentStatePagerAdapter(supportFragmentManager, fragments)
            currentItem = 0
            addOnPageChangeListener(MyOnPageChangeListener())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}