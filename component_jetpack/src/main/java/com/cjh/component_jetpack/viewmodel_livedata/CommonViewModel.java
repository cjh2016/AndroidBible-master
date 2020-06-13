package com.cjh.component_jetpack.viewmodel_livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cjh.component_jetpack.utils.QrLog;

/**
 * @author: caijianhui
 * @date: 2020/6/9 10:31
 * @description:  屏幕旋转、语言切换后能存活，但是如果被系统杀掉了，就要靠onSavedInstanceState、ViewModelSavedState
 * ViewModelSavedState：todo
 */
public class CommonViewModel extends ViewModel {
    public MutableLiveData<String> text = new MutableLiveData<>();

    @Override
    protected void onCleared() {
        super.onCleared();
        //可以在这里清理资源
        QrLog.e("CommonViewModel - onCleared");
    }
}
