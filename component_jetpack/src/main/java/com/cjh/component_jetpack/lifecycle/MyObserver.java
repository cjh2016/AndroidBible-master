package com.cjh.component_jetpack.lifecycle;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author: caijianhui
 * @date: 2020/6/9 9:35
 * @description: 用注解的方式实现，不再推荐
 */
public class MyObserver implements LifecycleObserver {

    private static final String TAG = "Lifecycle";

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        Log.e(TAG, "Lifecycle call onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume() {
        Log.e(TAG, "Lifecycle call onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        Log.e(TAG, "Lifecycle call onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        Log.e(TAG, "Lifecycle call onDestroy");
    }

}
