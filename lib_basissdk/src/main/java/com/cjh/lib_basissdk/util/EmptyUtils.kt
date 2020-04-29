package com.cjh.lib_basissdk.util

import android.annotation.SuppressLint
import android.os.Build
import android.util.*
import androidx.collection.SimpleArrayMap
import java.lang.reflect.Array


/**
 * @author: caijianhui
 * @date: 2020/4/29 17:43
 * @description: 判空处理
 */
object EmptyUtils {

    /**
     * 判断对象是否为空
     *
     * @param obj 对象
     * @return `true`: 为空<br></br>`false`: 不为空
     */
    @SuppressLint("NewApi")
    fun isEmpty(obj: Any?): Boolean {
        if (null == obj) {
            return true
        }
        if (obj is CharSequence && obj.toString().isEmpty()) {
            return true
        }
        if (obj.javaClass.isArray && Array.getLength(obj) === 0) {
            return true
        }
        if (obj is Collection<*> && obj.isEmpty()) {
            return true
        }
        if (obj is Map<*, *> && obj.isEmpty()) {
            return true
        }
        if (obj is SimpleArrayMap<*, *> && obj.isEmpty) {
            return true
        }
        if (obj is SparseArray<*> && obj.size() === 0) {
            return true
        }
        if (obj is SparseBooleanArray && obj.size() === 0) {
            return true
        }
        if (obj is SparseIntArray && obj.size() === 0) {
            return true
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj is SparseLongArray && obj.size() === 0) {
                return true
            }
        }
        if (obj is LongSparseArray<*> && obj.size() === 0) {
            return true
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (obj is LongSparseArray<*> && obj.size() == 0) {
                return true
            }
        }
        return false
    }

    /**
     * 判断对象是否非空
     *
     * @param obj 对象
     * @return `true`: 非空<br></br>`false`: 空
     */
    fun isNotEmpty(obj: Any?): Boolean {
        return !isEmpty(obj)
    }

}