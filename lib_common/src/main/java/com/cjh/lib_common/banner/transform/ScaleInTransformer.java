package com.cjh.lib_common.banner.transform;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

/**
 * @author: caijianhui
 * @date: 2020/6/16 14:22
 * @description:
 */
public class ScaleInTransformer implements ViewPager2.PageTransformer {

    private static final float DEFAULT_CENTER = 0.5f;
    private static final float DEFAULT_MIN_SCALE = 0.85f;
    private float mMinScale;

    public ScaleInTransformer(float mMinScale) {
        this.mMinScale = mMinScale;
    }

    @Override
    public void transformPage(@NonNull View view, float position) {

        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        view.setPivotY(pageHeight / 2f);
        view.setPivotX(pageWidth / 2f);

        if (position < -1) { //负无穷到-1
            // This page is way off-screen to the left.
            view.setScaleX(mMinScale);
            view.setScaleY(mMinScale);
            view.setPivotX(pageWidth);
        } else if (position <= 1) { //-1到1
            // Modify the default slide transition to shrink the page as well
            if (position < 0) {
                float scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale;
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                view.setPivotX(pageWidth * (DEFAULT_CENTER + (DEFAULT_CENTER * -position)));
            } else {
                float scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale;
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                view.setPivotX(pageWidth * ((1 - position) * DEFAULT_CENTER));
            }
        } else { //1到正无穷
            view.setPivotX(0);
            view.setScaleX(mMinScale);
            view.setScaleY(mMinScale);
        }

    }
}
