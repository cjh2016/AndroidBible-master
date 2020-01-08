package com.cjh.component_videoplayer.playerbase.record;

/**
 * @author: caijianhui
 * @date: 2019/8/13 17:03
 * @description:
 */
public interface PlayValueGetter {

    int getCurrentPosition();

    int getBufferPercentage();

    int getDuration();

    int getState();

}
