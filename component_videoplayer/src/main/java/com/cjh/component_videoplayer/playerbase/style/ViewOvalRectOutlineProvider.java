package com.cjh.component_videoplayer.playerbase.style;

import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.annotation.RequiresApi;
import com.cjh.component_videoplayer.playerbase.utils.RectUtils;

/**
 * @author: caijianhui
 * @date: 2019/8/14 10:26
 * @description:
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ViewOvalRectOutlineProvider extends ViewOutlineProvider {

    private Rect mRect;

    public ViewOvalRectOutlineProvider(Rect rect){
        this.mRect = rect;
    }

    @Override
    public void getOutline(final View view, final Outline outline) {
        Rect selfRect;
        if (mRect != null) {
            selfRect = mRect;
        } else {
            Rect rect = new Rect();
            view.getGlobalVisibleRect(rect);
            selfRect = RectUtils.getOvalRect(rect);
        }
        outline.setOval(selfRect);
    }


}
