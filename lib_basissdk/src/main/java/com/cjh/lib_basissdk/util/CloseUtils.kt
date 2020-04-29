package com.cjh.lib_basissdk.util

import java.io.Closeable
import java.io.IOException

/**
 * @author: caijianhui
 * @date: 2020/4/29 17:16
 * @description: 关闭相关工具类
 */
object CloseUtils {

    /**
     * 关闭 IO
     *
     * @param closeables closeables
     */
    fun closeIO(vararg closeables: Closeable?) {
        if (null == closeables) return
        for (closeable in closeables) {
            if (closeable != null) {
                try {
                    closeable.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }


    /**
     * 安静关闭 IO(不打印IO异常就是安静？)
     *
     * @param closeables closeables
     */
    fun closeIOQuietly(vararg closeables: Closeable?) {
        if (closeables == null) return
        for (closeable in closeables) {
            if (closeable != null) {
                try {
                    closeable.close()
                } catch (ignored: IOException) {
                }
            }
        }
    }


}