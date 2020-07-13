package com.cjh.component_jetpack.viewmodel_livedata;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.cjh.component_jetpack.R;
import com.cjh.component_jetpack.base.BaseActivity;
import com.cjh.component_jetpack.databinding.ActivityViewModelBinding;
import com.cjh.component_jetpack.utils.QrLog;

/**
 * @author: caijianhui
 * @date: 2020/6/9 10:41
 * @description:
 */
public class ViewModelActivity extends BaseActivity {

    private CommonViewModel mCommonViewModel;
    private ActivityViewModelBinding mBinding;
    private MutableLiveData<String> mLiveData = new MutableLiveData<>();

    private String mTime;

    private SavedStateViewModel mSavedStateViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_model);
        mCommonViewModel = ViewModelProviders.of(this).get(CommonViewModel.class);
        mBinding.setCommonViewModel(mCommonViewModel);
        mCommonViewModel.text.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.tvText.setText(s);
            }
        });

        //旋转屏幕重建act后，vm还是同一个实例
        QrLog.e("mCommonViewModel hashCode = " + mCommonViewModel.hashCode());

        mLiveData.observe(this, s -> {
            //只有处于活跃状态：onStart、onResume、onPause才会收到回调
            QrLog.e("----------------- observe = " + s + "," + getLifecycle().getCurrentState());
        });

        //观察所有生命周期，需要手动removeObserver
        mLiveData.observeForever(s -> {
//            QrLog.e("----------------- observeForever = " + s + "," + getLifecycle().getCurrentState());
        });

        mLiveData.setValue("onCreate");

        if (null == savedInstanceState) {
            mTime = String.valueOf(System.currentTimeMillis() / 1000);
            QrLog.e("onCreate 获取当前时间 = " + mTime);
        } else {
            mTime = savedInstanceState.getString("test");
            QrLog.e("onCreate 恢复上次时间 = " + mTime);
        }

        mSavedStateViewModel = ViewModelProviders.of(this,
                new SavedStateViewModelFactory(getApplication(), this))
                .get(SavedStateViewModel.class);

        QrLog.e("mSavedStateViewModel hashCode = " + mSavedStateViewModel.hashCode());

    }

    @Override
    protected void onStart() {
        super.onStart();
        mLiveData.setValue("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLiveData.setValue("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLiveData.setValue("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLiveData.setValue("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLiveData.setValue("onDestroy");
    }

    public void changeData(View view) {
        mCommonViewModel.text.setValue(String.valueOf(System.currentTimeMillis()));
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("test", mTime);
    }
}
