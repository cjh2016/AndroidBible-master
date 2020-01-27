package com.cjh.component_materialdesign.tablayout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cjh.component_materialdesign.BaseFragment
import com.cjh.component_materialdesign.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_tablayout_normal.*

/**
 * @author: caijianhui
 * @date: 2020/1/11 12:14
 * @description:
 */
class NormalTabLayoutFragment: BaseFragment() {

    private var TAG = NormalTabLayoutFragment::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tablayout_normal, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tab_layout_create?.apply {
            //创建Tab, 可以设置文字和图标
            addTab(newTab().setText("个性推荐").setIcon(R.mipmap.ic_launcher))
            addTab(newTab().setText("歌单").setIcon(R.mipmap.ic_launcher))
            addTab(newTab().setText("主播电台").setIcon(R.mipmap.ic_launcher))
            addTab(newTab().setText("排行榜").setIcon(R.mipmap.ic_launcher))

            //监听Tab选择状态
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    Log.i(TAG,"onTabSelected: ${tab?.text}")
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    Log.i(TAG,"onTabUnselected: ${tab?.text}")
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    Log.i(TAG,"onTabReselected: ${tab?.text}")
                }

            })
        }
    }

    companion object {
        private var sInstance: BaseFragment? = null

        fun newInstance(): NormalTabLayoutFragment? {
            if (null == sInstance) {
                sInstance = NormalTabLayoutFragment()
            }
            return sInstance as NormalTabLayoutFragment
        }
    }

}