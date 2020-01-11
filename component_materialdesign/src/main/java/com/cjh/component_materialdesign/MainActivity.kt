package com.cjh.component_materialdesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cjh.component_materialdesign.tablayout.NormalTabLayoutFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, NormalTabLayoutFragment())
            .commit()
    }
}
