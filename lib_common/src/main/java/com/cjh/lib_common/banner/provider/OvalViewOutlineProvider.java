package com.cjh.lib_common.banner.provider;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

/**
 * @author: caijianhui
 * @date: 2020/6/16 10:15
 * @description: 圆形效果
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class OvalViewOutlineProvider extends ViewOutlineProvider {

    public OvalViewOutlineProvider() {}

    @Override
    public void getOutline(View view, Outline outline) {
        Rect selfRect;
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        selfRect = getOvalRect(rect);
        outline.setOval(selfRect);
    }

    /**
     * 以矩形的中心点为圆心,较短的边为直径画圆
     *
     * @param rect
     * @return
     */
    private Rect getOvalRect(Rect rect) {
        int width = rect.right - rect.left;
        int height = rect.bottom - rect.top;
        int left, top, right, bottom;
        int dW = width / 2;
        int dH = height / 2;
        if(width > height) {
            left = dW - dH;
            top = 0;
            right = dW + dH;
            bottom = dH * 2;
        } else {
            left = dH - dW;
            top = 0;
            right = dH + dW;
            bottom = dW * 2;
        }
        return new Rect(left, top, right, bottom);
    }
}
