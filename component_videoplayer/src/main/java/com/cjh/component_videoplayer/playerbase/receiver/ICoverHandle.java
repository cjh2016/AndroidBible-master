package com.cjh.component_videoplayer.playerbase.receiver;

import android.os.Bundle;

/**
 * @author: caijianhui
 * @date: 2019/8/9 15:44
 * @description:
 */
public interface ICoverHandle {

    void requestPause(Bundle bundle);
    void requestResume(Bundle bundle);
    void requestSeek(Bundle bundle);
    void requestStop(Bundle bundle);
    void requestReset(Bundle bundle);
    void requestRetry(Bundle bundle);
    void requestReplay(Bundle bundle);
    void requestPlayDataSource(Bundle bundle);

    void requestNotifyTimer();
    void requestStopTimer();

}
