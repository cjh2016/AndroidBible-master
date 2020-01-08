package com.cjh.component_videoplayer.playerbase.player;

import android.os.Bundle;

/**
 * @author: caijianhui
 * @date: 2019/8/9 11:46
 * @description:
 */
public interface OnBufferingListener {

    void onBufferingUpdate(int bufferPercentage, Bundle extra);
}
