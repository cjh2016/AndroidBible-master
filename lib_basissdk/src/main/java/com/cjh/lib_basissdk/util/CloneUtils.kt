package com.cjh.lib_basissdk.util

import java.io.*

/**
 * @author: caijianhui
 * @date: 2020/4/29 17:35
 * @description: 克隆相关工具
 */
object CloneUtils {

    /**
     * Deep clone.
     *
     * @param data The data.
     * @param <T>  The value type.
     * @return The object of cloned
    </T> */
    fun <T> deepClone(data: Serializable?): T? {
        return if (data == null) null else bytes2Object(serializable2Bytes(data as Serializable?)) as T?
    }


    private fun serializable2Bytes(serializable: Serializable?): ByteArray? {
        if (serializable == null) return null
        var baos: ByteArrayOutputStream? = null
        var oos: ObjectOutputStream? = null
        return try {
            oos = ObjectOutputStream(ByteArrayOutputStream().also { baos = it })
            oos.writeObject(serializable)
            baos?.toByteArray()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        } finally {
            try {
                oos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    private fun bytes2Object(bytes: ByteArray?): Any? {
        if (bytes == null) return null
        var ois: ObjectInputStream? = null
        return try {
            ois = ObjectInputStream(ByteArrayInputStream(bytes))
            ois.readObject()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            try {
                ois?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}