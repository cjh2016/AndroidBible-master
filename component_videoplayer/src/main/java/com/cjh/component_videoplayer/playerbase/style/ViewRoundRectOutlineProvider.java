package com.cjh.component_videoplayer.playerbase.style;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

/**
 * @author: caijianhui
 * @date: 2019/8/14 10:37
 * @description:
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ViewRoundRectOutlineProvider extends ViewOutlineProvider {

    private float mRadius;
    private Rect mRect;

    public ViewRoundRectOutlineProvider(float radius){
        this.mRadius = radius;
    }

    public ViewRoundRectOutlineProvider(float radius, Rect rect) {
        this.mRadius = radius;
        this.mRect = rect;
    }

    @Override
    public void getOutline(View view, Outline outline) {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        int leftMargin = 0;
        int topMargin = 0;
        Rect selfRect = new Rect(leftMargin, topMargin,
                rect.right - rect.left - leftMargin, rect.bottom - rect.top - topMargin);
        if(mRect!=null){
            selfRect = mRect;
        }
        outline.setRoundRect(selfRect, mRadius);
    }
}
