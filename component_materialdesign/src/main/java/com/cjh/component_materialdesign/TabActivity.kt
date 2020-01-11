package com.cjh.component_materialdesign

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.cjh.component_materialdesign.tablayout.FirstFragment
import com.cjh.component_materialdesign.tablayout.MyFragmentAdapter
import com.cjh.component_materialdesign.tablayout.SecondFragment
import com.cjh.component_materialdesign.tablayout.ThreeFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_tab.*
import java.util.ArrayList

/**
 * @author: caijianhui
 * @date: 2020/1/11 14:53
 * @description:
 */
class TabActivity: AppCompatActivity() {

    private var TAG = TabActivity::class.java.simpleName

    private var sTitle = arrayOf("ITEM FIRST","ITEM SECOND","ITEM THIRD")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
        initView()
    }


    private fun initView() {
        tabLayout?.apply {

            //创建Tab
            addTab(newTab().setText(sTitle[0]))
            addTab(newTab().setText(sTitle[1]))
            addTab(newTab().setText(sTitle[2]))

            //监听Tab切换
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    Log.i(TAG,"onTabSelected: $tab.getText()")
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })

            //关联ViewPager
            setupWithViewPager(view_pager)

            val fragments = ArrayList<Fragment>()
            fragments.add(FirstFragment())
            fragments.add(SecondFragment())
            fragments.add(ThreeFragment())

            val fragmentAdapter = MyFragmentAdapter(supportFragmentManager, fragments)
            view_pager.adapter = fragmentAdapter
            view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    Log.i(TAG, "select page:$position")
                }

            })
        }

        view_pager?.apply {

        }
    }
}