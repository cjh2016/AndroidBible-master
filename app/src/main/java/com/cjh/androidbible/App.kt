package com.cjh.androidbible

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

/**
 * @author: caijianhui
 * @date: 2019/9/19 9:34
 * @description:
 */
class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}