package com.cjh.component_videoplayer.playerbase.style;

import android.graphics.Rect;

/**
 * @author: caijianhui
 * @date: 2019/8/14 10:06
 * @description:
 */
public interface IStyleSetter {

    void setRoundRectShape(float radius);

    void setRoundRectShape(Rect rect, float radius);

    void setOvalRectShape();

    void setOvalRectShape(Rect rect);

    void clearShapeStyle();

    void setElevationShadow(float elevation);

    /**
     * must setting a color when set shadow, not transparent.
     * @param backgroundColor
     * @param elevation
     */
    void setElevationShadow(int backgroundColor, float elevation);

}
