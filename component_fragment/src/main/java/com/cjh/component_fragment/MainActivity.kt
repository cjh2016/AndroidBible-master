package com.cjh.component_fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.cjh.component_fragment.ui.activity.*
import com.cjh.component_fragment.ui.fragment.NormalFragment1

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goReplaceFragment(view: View) {
        val intent = Intent(this@MainActivity, ReplaceFragmentActivity::class.java)
        startActivity(intent)
    }

    fun goHideShowFragment(view: View) {
        val intent = Intent(this@MainActivity, HideShowFragmentActivity::class.java)
        startActivity(intent)
    }

    fun goNormalFragment(view: View) {
        val intent = Intent(this@MainActivity, NormalFragmentActivity::class.java)
        startActivity(intent)
    }

    fun goLazyLoadFragment(view: View) {
        val intent = Intent(this@MainActivity, LazyLoadFragmentActivity::class.java)
        startActivity(intent)
    }

    fun goXmlLoadFragment(view: View) {
        val intent = Intent(this@MainActivity, XmlLoadFragmentActivity::class.java)
        startActivity(intent)
    }

}
