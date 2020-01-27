package com.cjh.component_materialdesign.appbarlayout

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cjh.component_materialdesign.BaseFragment
import com.cjh.component_materialdesign.R
import com.cjh.component_materialdesign.tablayout.AdvancedTabLayoutFragment
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_appbarlayout_collapsing_toolbar_layout.*

class AppBarLayoutFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.get("layoutId")?.let {
            return inflater.inflate(it as Int, container, false)
        }
        return inflater.inflate(R.layout.fragment_appbarlayout_scroll, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appbar_collapsing_toolbar?.apply {
            setCollapsedTitleTextColor(resources.getColor(R.color.white))
            setExpandedTitleColor(Color.TRANSPARENT)
        }

        appbar_container?.apply {
            addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                Log.e("zhouwei","appbarHeight: ${appBarLayout.height} getTotalScrollRange: ${appBarLayout.totalScrollRange} offSet: $verticalOffset")

            })
        }
    }

    companion object {
        private var sInstance: BaseFragment? = null

        fun newInstance(): AppBarLayoutFragment? {
            if (null == sInstance) {
                sInstance = AppBarLayoutFragment()
            }
            return sInstance as AppBarLayoutFragment
        }
    }


}