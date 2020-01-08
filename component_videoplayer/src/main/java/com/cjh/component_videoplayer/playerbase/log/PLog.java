package com.cjh.component_videoplayer.playerbase.log;

import android.util.Log;

/**
 * @author: caijianhui
 * @date: 2019/8/9 9:40
 * @description:
 */
public class PLog {

    public static boolean LOG_OPEN = false;

    public static void d(String tag, String message){
        if(!LOG_OPEN)
            return;
        Log.d(tag, message);
    }

    public static void w(String tag, String message){
        if(!LOG_OPEN)
            return;
        Log.w(tag, message);
    }

    public static void e(String tag, String message){
        if(!LOG_OPEN)
            return;
        Log.e(tag, message);
    }

}