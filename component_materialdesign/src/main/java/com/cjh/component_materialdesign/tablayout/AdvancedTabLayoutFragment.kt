package com.cjh.component_materialdesign.tablayout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.cjh.component_materialdesign.BaseFragment
import com.cjh.component_materialdesign.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_tablayout_advanced.*
import java.util.ArrayList

/**
 * @author: caijianhui
 * @date: 2020/1/11 14:42
 * @description:
 */
class AdvancedTabLayoutFragment: BaseFragment() {

    private var TAG = AdvancedTabLayoutFragment::class.java.simpleName


    private var sTitle = arrayOf("你好","ITEM SECOND","ITEM THIRD")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tablayout_advanced, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        tabLayout?.apply {



            //创建Tab
            addTab(newTab().setText(sTitle[0]).setIcon(R.mipmap.ic_launcher))
            addTab(newTab().setText(sTitle[1]).setIcon(R.mipmap.ic_launcher))
            addTab(newTab().setText(sTitle[2]).setIcon(R.mipmap.ic_launcher))

            //监听Tab切换
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    Log.i(TAG,"onTabSelected: ${tab?.text}")
                    view_pager.currentItem = tab?.position!!
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })

            val fragments = ArrayList<Fragment>()
            fragments.add(FirstFragment())
            fragments.add(SecondFragment())
            fragments.add(ThreeFragment())

            val fragmentAdapter = MyFragmentAdapter(activity?.supportFragmentManager!!, fragments)
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
                    tabLayout.setScrollPosition(position, 0f, true)
                }

            })

            //关联ViewPager,这个方法会先清除掉以前的tab,然后在根据adapter添加tab，具体自己查看源码
            //setupWithViewPager(view_pager)
        }



    }

    companion object {
        private var sInstance: BaseFragment? = null

        fun newInstance(): AdvancedTabLayoutFragment? {
            if (null == sInstance) {
                sInstance = AdvancedTabLayoutFragment()
            }
            return sInstance as AdvancedTabLayoutFragment
        }
    }


}