package com.cjh.component_materialdesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cjh.component_materialdesign.appbarlayout.AppBarLayoutFragment
import com.cjh.component_materialdesign.tablayout.AdvancedTabLayoutFragment
import com.cjh.component_materialdesign.tablayout.NormalTabLayoutFragment
import com.cjh.component_materialdesign.tablayout.XmlTabLayoutFragment
import kotlinx.android.synthetic.main.fragment_appbarlayout_scroll.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initFragments()
    }

    fun XmlTabLayout(view: View) {
        goFragment(XmlTabLayoutFragment.newInstance())
    }

    fun NormalTabLayout(view: View) {
        goFragment(NormalTabLayoutFragment.newInstance())
    }

    fun AdvancedTabLayout(view: View) {
        goFragment(AdvancedTabLayoutFragment.newInstance())

    }

    fun AppBarLayoutScroll(view: View) {
        AppBarLayout(R.layout.fragment_appbarlayout_scroll)
    }

    fun AppBarLayoutEnterAlways(view: View) {
        AppBarLayout(R.layout.fragment_appbarlayout_enter_always)
    }

    fun AppBarLayoutEnterAlwaysCollapsed(view: View) {
        AppBarLayout(R.layout.fragment_appbarlayout_enter_always_collapsed)
    }

    fun AppBarLayoutExitUntilCollapsed(view: View) {
        AppBarLayout(R.layout.fragment_appbarlayout_exit_until_collapsed)
    }

    fun AppBarLayoutSnap(view: View) {
        AppBarLayout(R.layout.fragment_appbarlayout_snap)
    }

    fun AppBarLayoutCollapsingToolbarLayout(view: View) {
        AppBarLayout(R.layout.fragment_appbarlayout_collapsing_toolbar_layout)
    }


    private fun AppBarLayout(layoutId: Int) {
        val bundle = Bundle()
        bundle.putInt("layoutId", layoutId)
        val fragment = AppBarLayoutFragment()
        fragment?.arguments = bundle
        goFragment(fragment)
    }





    private fun goFragment(fragment: BaseFragment?) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment!!)
            .commit()
    }

    private fun initFragments() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, XmlTabLayoutFragment.newInstance()!!)
            .add(R.id.fragment_container, NormalTabLayoutFragment.newInstance()!!)
            .hide(NormalTabLayoutFragment.newInstance()!!)
            .show(XmlTabLayoutFragment.newInstance()!!)
            .commit()
    }




}
