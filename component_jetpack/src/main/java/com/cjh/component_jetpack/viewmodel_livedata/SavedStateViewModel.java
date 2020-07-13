package com.cjh.component_jetpack.viewmodel_livedata;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.cjh.component_jetpack.utils.QrLog;

/**
 * @author: caijianhui
 * @date: 2020/6/9 10:25
 * @description: 能够处理意外销毁的ViewModel
 */
public class SavedStateViewModel extends ViewModel {

    private SavedStateHandle mHandle;

    public SavedStateViewModel(SavedStateHandle handle) {
        mHandle = handle;

        Object text = mHandle.get("text");
        if (null == text) {
            String time = String.valueOf(System.currentTimeMillis() / 1000);
            mHandle.set("text", time);
            QrLog.e("SavedStateViewModel 初始化数据 = " + time);
        } else {
            QrLog.e("SavedStateViewModel 恢复数据 = " + text);
        }
    }
}
