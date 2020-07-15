package com.cjh.lib_common.banner.provider;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

/**
 * @author: caijianhui
 * @date: 2020/6/16 13:56
 * @description: 为View设置圆角/圆形效果，支持View及ViewGroup，适用Android 5.1及以上系统。
 */
public class ViewStyleSetter {

    private View mView;

    public ViewStyleSetter(View view) {
        this.mView = view;
    }

    /**
     * 为View设置圆角效果
     *
     * @param radius 圆角半径
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setRoundRect(float radius) {
        this.mView.setClipToOutline(true);
        this.mView.setOutlineProvider(new RoundViewOutlineProvider(radius));
    }

    /**
     * 设置View为圆形
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setOvalView() {
        this.mView.setClipToOutline(true);
        this.mView.setOutlineProvider(new OvalViewOutlineProvider());
    }

    /**
     * 清除View的圆角效果
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void clearShapeStyle() {
        this.mView.setClipToOutline(false);
    }


}
