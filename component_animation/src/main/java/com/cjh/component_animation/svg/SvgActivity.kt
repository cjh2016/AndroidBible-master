package com.cjh.component_animation.svg

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cjh.component_animation.R
import kotlinx.android.synthetic.main.activity_svg.*

/**
 * @author: caijianhui
 * @date: 2020/5/30 13:50
 * @description:
 */
class SvgActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_svg)
    }

    fun onViewClicked(view: View) {
        val drawable = id_back.drawable

        if (drawable is Animatable) {
            drawable.start()
        }
    }


    fun onViewClicked2(view: View) {
        val drawable = id_iv.drawable

        if (drawable is Animatable) {
            drawable.start()
        }
    }
}