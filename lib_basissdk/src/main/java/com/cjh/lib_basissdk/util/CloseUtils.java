package com.cjh.lib_basissdk.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * 关闭相关工具类 
 */
public final class CloseUtils {
	
	private CloseUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
	
	/**
     * 关闭 IO
     *
     * @param closeables closeables
     */
	public static void closeIO(final Closeable... closeables) {
		if (null == closeables) return;
		for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	/**
     * 安静关闭 IO(不打印IO异常就是安静？)
     *
     * @param closeables closeables
     */
    public static void closeIOQuietly(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
	
}
