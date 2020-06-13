package com.cjh.component_jetpack.utils;

import android.util.Log;

/**
 * @author: caijianhui
 * @date: 2020/6/8 11:33
 * @description:
 */
public class QrLog {

    private static boolean DEBUG = true;
    private static final String TAG = "哈利迪";

    public static void e(String s) {
        if (DEBUG) Log.e(TAG, s);
    }

    public static void e(String tag, String s) {
        if (DEBUG) Log.e(tag, s);
    }
}
