package com.cjh.lib_basissdk.singleton

import android.content.Context
import com.cjh.lib_basissdk.util.Utils
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.ConcurrentHashMap

/**
 * @author: caijianhui
 * @date: 2019/5/27 14:28
 * @description:
 */
open class Singleton {

    companion object {

        private val INSTANCE_MAP = ConcurrentHashMap<Class<*>, Any?>()

        fun <T> getInstance(type: Class<T>): T {
            var instance: Any? = INSTANCE_MAP[type]
            try {
                if (null == instance) {
                    synchronized(INSTANCE_MAP) {
                        val constructor = type.getDeclaredConstructor(Context::class.java)
                        constructor.isAccessible = true
                        instance = constructor.newInstance(Utils.getApp().applicationContext)
                        INSTANCE_MAP.put(type, instance!!)
                    }
                }
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }
            return instance as T
        }
    }

    val context: Context?
        get() = Utils.getApp().applicationContext
}