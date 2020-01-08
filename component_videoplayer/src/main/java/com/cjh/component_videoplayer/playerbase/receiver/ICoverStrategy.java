package com.cjh.component_videoplayer.playerbase.receiver;

import android.view.ViewGroup;
import com.cjh.component_videoplayer.playerbase.receiver.BaseCover;

/**
 * @author: caijianhui
 * @date: 2019/8/9 15:48
 * @description:
 */
public interface ICoverStrategy {

    void addCover(BaseCover cover);
    void removeCover(BaseCover cover);
    void removeAllCovers();
    boolean isContainsCover(BaseCover cover);
    int getCoverCount();
    ViewGroup getContainerView();

}
