package com.cjh.component_videoplayer.playerbase.style;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * @author: caijianhui
 * @date: 2019/8/14 10:15
 * @description:
 */
public final class StyleSetter implements IStyleSetter {

    private View mView;

    public StyleSetter(View view){
        this.mView = view;
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setRoundRectShape(float radius) {
        setRoundRectShape(null, radius);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setRoundRectShape(Rect rect, float radius) {
        this.mView.setClipToOutline(true);
        this.mView.setOutlineProvider(new ViewRoundRectOutlineProvider(radius, rect));
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setOvalRectShape() {
        setOvalRectShape(null);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setOvalRectShape(Rect rect) {
        this.mView.setClipToOutline(true);
        this.mView.setOutlineProvider(new ViewOvalRectOutlineProvider(rect));
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void clearShapeStyle() {
        this.mView.setClipToOutline(false);
    }

    @Override
    public void setElevationShadow(float elevation) {
        setElevationShadow(Color.BLACK, elevation);
    }

    @Override
    public void setElevationShadow(int backgroundColor, float elevation) {
        mView.setBackgroundColor(backgroundColor);
        ViewCompat.setElevation(mView, elevation);
        mView.invalidate();
    }
}
