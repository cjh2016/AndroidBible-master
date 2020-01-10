package com.cjh.component_viewpaper

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cjh.component_viewpaper.activity.FragmentPagerActivity
import com.cjh.component_viewpaper.activity.FragmentStatePagerActivity
import com.cjh.component_viewpaper.activity.ViewPagerActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goViewPager(view: View) {
        val intent = Intent(this, ViewPagerActivity::class.java)
        startActivity(intent)
    }

    fun goFragmentPager(view: View) {
        val intent = Intent(this, FragmentPagerActivity::class.java)
        startActivity(intent)
    }

    fun goFragmentStatePager(view: View) {
        val intent = Intent(this, FragmentStatePagerActivity::class.java)
        startActivity(intent)
    }
}
