package com.cjh.component_jetpack.lifecycle;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * @author: caijianhui
 * @date: 2020/6/8 11:53
 * @description: 实现DefaultLifecycleObserver接口，推荐
 */
public class MyObserver2 implements DefaultLifecycleObserver {

    private static final String TAG = "Lifecycle";

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Log.e(TAG, "Lifecycle2 call onCreate");
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        Log.e(TAG, "Lifecycle2 call onStart");
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Log.e(TAG, "Lifecycle2 call onResume");
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        Log.e(TAG, "Lifecycle2 call onPause");
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        Log.e(TAG, "Lifecycle2 call onStop");
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        Log.e(TAG, "Lifecycle2 call onDestroy");
    }

    //如果不想全部实现，可以自己写一个simple类；或启用java8，因为需要支持DefaultLifecycleObserver接口的默认实现
}
