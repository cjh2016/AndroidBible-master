package com.cjh.component_viewpaper.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.cjh.component_viewpaper.listener.MyOnPageChangeListener
import com.cjh.component_viewpaper.R
import com.cjh.component_viewpaper.adapter.MyViewPagerAdapter
import com.cjh.component_viewpaper.transformer.ZoomInTransformer
import java.util.ArrayList

/**
 * @author: caijianhui
 * @date: 2019/8/8 10:19
 * @description:
 */
class ViewPagerActivity: AppCompatActivity() {

    var viewPager: ViewPager? = null

    private val views: ArrayList<View> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        viewPager = findViewById(R.id.view_pager)

        initViewPager()
    }

    private fun initViewPager() {
        val layoutInflater: LayoutInflater = LayoutInflater.from(this)
        val view1 = layoutInflater.inflate(R.layout.layout_view1, null)
        val view2 = layoutInflater.inflate(R.layout.layout_view2, null)
        val view3 = layoutInflater.inflate(R.layout.layout_view3, null)

        views.add(view1)
        views.add(view2)
        views.add(view3)

        viewPager?.run {
            adapter = MyViewPagerAdapter(views)
            currentItem = 0
            addOnPageChangeListener(MyOnPageChangeListener())
            setPageTransformer(true, ZoomInTransformer())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}