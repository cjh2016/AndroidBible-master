package com.cjh.component_xfermode.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.cjh.component_xfermode.R
import com.cjh.component_xfermode.adapter.XFerModeAdapter
import com.cjh.component_xfermode.fragment.XFerModeFragment
import kotlinx.android.synthetic.main.activity_xfermode_item.*

/**
 * @author: caijianhui
 * @date: 2020/4/28 19:52
 * @description:
 */
class XFerModeItemActivity : AppCompatActivity() {

    companion object {
        val INDEX = "xfermode"
        val DST = "dst"
        val SRC = "src"
        val SHOW_INDEX = "showIndex"
    }

    private var dst = 0
    private var src = 0

    private var showIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xfermode_item)

        dst = intent.getIntExtra(DST, 0)
        src = intent.getIntExtra(SRC, 0)
        showIndex = intent.getIntExtra(SHOW_INDEX, 0)

        view_pager.apply {
            adapter = XFerModeViewPagerAdapter(supportFragmentManager, dst, src)
            currentItem = showIndex
            offscreenPageLimit = 3
        }
    }

    inner class XFerModeViewPagerAdapter(fm: FragmentManager, val dst: Int, val src: Int) :
        FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return XFerModeFragment.getInstance(position, dst, src)
        }

        override fun getCount(): Int {
            return 16
        }


    }
}
