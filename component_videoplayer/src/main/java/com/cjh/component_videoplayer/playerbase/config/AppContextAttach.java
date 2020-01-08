package com.cjh.component_videoplayer.playerbase.config;

import android.content.Context;
import android.util.Log;

/**
 * @author: caijianhui
 * @date: 2019/8/9 14:08
 * @description:
 *  if you want get app context, you need call attach method to init it.
 */
public class AppContextAttach {

    private static Context mAppContext;

    static void attach(Context context){
        mAppContext = context.getApplicationContext();
    }

    public static Context getApplicationContext(){
        if(mAppContext==null){
            Log.e("AppContextAttach", "app context not init !!!");
            throw new RuntimeException("if you need context for using decoder, you must call PlayerLibrary.init(context).");
        }
        return mAppContext;
    }

}
