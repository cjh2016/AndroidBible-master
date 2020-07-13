package com.cjh.androidbible

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.cjh.lib_common.base.IBaseApp
import com.cjh.lib_common.config.ModuleConfig

/**
 * @author: caijianhui
 * @date: 2019/9/19 9:34
 * @description:
 */
class App : Application(), IBaseApp {

    private val TAG = App::class.java.simpleName

    override fun onCreate() {
        super.onCreate()
        onInitSpeed(this@App)
        //耗时处理
        onInitLow(this@App)

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onInitSpeed(application: Application): Boolean {
        for (appClazz in ModuleConfig.initModules) {
            try {
                appClazz?.let {
                    val clazz = Class.forName(it)
                    val moduleApp = clazz.newInstance() as IBaseApp
                    moduleApp.onInitSpeed(application)
                }
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            }
        }
        return true
    }

    override fun onInitLow(application: Application): Boolean {
        for (appClazz in ModuleConfig.initModules) {
            try {
                appClazz?.let {
                    val clazz = Class.forName(it)
                    val moduleApp = clazz.newInstance() as IBaseApp
                    moduleApp.onInitLow(application)
                }
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            }
        }
        return true
    }


}