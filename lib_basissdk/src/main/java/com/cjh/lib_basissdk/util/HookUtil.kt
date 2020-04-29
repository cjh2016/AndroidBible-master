package com.cjh.lib_basissdk.util

import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * @author: caijianhui
 * @date: 2020/4/29 17:50
 * @description:
 */
object HookUtil {

    fun hookClickListener(view: View?) {
        try {
            val viewClazz = Class.forName("android.view.View")
            val getListenerInfoMethod: Method = viewClazz.getDeclaredMethod("getListenerInfo")
            getListenerInfoMethod.isAccessible = true
            val listenerInfo: Any = getListenerInfoMethod.invoke(view)
            val listenerInfoClass =
                Class.forName("android.view.View\$ListenerInfo")
            val keyListener: Field = listenerInfoClass.getDeclaredField("mOnClickListener")
            keyListener.isAccessible = true
            keyListener.set(
                listenerInfo,
                HookClickListener(keyListener.get(listenerInfo) as View.OnClickListener)
            )
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }


    fun hookKeyListener(view: View?) {
        try {
            val viewClazz = Class.forName("android.view.View")
            val getListenerInfoMethod =
                viewClazz.getDeclaredMethod("getListenerInfo")
            getListenerInfoMethod.isAccessible = true
            val listenerInfo = getListenerInfoMethod.invoke(view)
            val listenerInfoClass =
                Class.forName("android.view.View\$ListenerInfo")
            val keyListener =
                listenerInfoClass.getDeclaredField("mOnKeyListener")
            keyListener.isAccessible = true
            keyListener[listenerInfo] = HookKeyListener(keyListener[listenerInfo] as View.OnKeyListener)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: java.lang.IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }

    internal class HookKeyListener(originalKeyListener: View.OnKeyListener) :
        View.OnKeyListener {
        var mKeyListener: View.OnKeyListener = originalKeyListener
        override fun onKey(
            v: View,
            keyCode: Int,
            event: KeyEvent
        ): Boolean {
            Toast.makeText(v.context, "key", Toast.LENGTH_SHORT).show()
            return mKeyListener.onKey(v, keyCode, event)
        }
    }

    internal class HookClickListener(originalClickListener: View.OnClickListener) :
        View.OnClickListener {
        var mOnClickListener: View.OnClickListener = originalClickListener
        override fun onClick(v: View) {
            Toast.makeText(v.context, "click", Toast.LENGTH_SHORT).show()
            mOnClickListener.onClick(v)
        }

    }


}