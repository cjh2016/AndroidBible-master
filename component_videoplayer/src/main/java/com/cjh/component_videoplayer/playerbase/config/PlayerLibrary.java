package com.cjh.component_videoplayer.playerbase.config;

import android.content.Context;

/**
 * @author: caijianhui
 * @date: 2019/8/9 14:29
 * @description:
 */
public class PlayerLibrary {

    public static void init(Context context){
        AppContextAttach.attach(context);
    }

}
