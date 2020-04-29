package com.cjh.component_xfermode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cjh.component_xfermode.activity.HeartActivity
import com.cjh.component_xfermode.activity.ZincXFerModeActivity
import kotlinx.android.synthetic.main.activity_xfermode_client.*

class ClientActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xfermode_client)

        btn_google.setOnClickListener(this)
        btn_zinc.setOnClickListener(this)
        btn_gua_gua.setOnClickListener(this)
        btn_heart.setOnClickListener(this)
        btn_add.setOnClickListener(this)
        btn_overlay.setOnClickListener(this)

    }

    override fun onClick(v: View) {

        when (v.id) {

            R.id.btn_google -> {

            }

            R.id.btn_zinc -> {
                startActivity(Intent(this, ZincXFerModeActivity::class.java))
            }

            R.id.btn_gua_gua -> {

            }

            R.id.btn_heart -> {
                startActivity(Intent(this, HeartActivity::class.java))
            }

            R.id.btn_add -> {

            }

            R.id.btn_overlay -> {

            }
        }
    }
}
