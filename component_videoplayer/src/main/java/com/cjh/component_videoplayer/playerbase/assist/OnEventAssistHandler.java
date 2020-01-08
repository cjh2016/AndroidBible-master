package com.cjh.component_videoplayer.playerbase.assist;

import android.os.Bundle;

/**
 * @author: caijianhui
 * @date: 2019/8/13 16:27
 * @description:
 */
public interface OnEventAssistHandler<T> {

    void onAssistHandle(T assist, int eventCode, Bundle bundle);

    void requestPause(T assist, Bundle bundle);
    void requestResume(T assist, Bundle bundle);
    void requestSeek(T assist, Bundle bundle);
    void requestStop(T assist, Bundle bundle);
    void requestReset(T assist, Bundle bundle);
    void requestRetry(T assist, Bundle bundle);
    void requestReplay(T assist, Bundle bundle);
    void requestPlayDataSource(T assist, Bundle bundle);
}
