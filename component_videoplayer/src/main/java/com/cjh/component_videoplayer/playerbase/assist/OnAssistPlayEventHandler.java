package com.cjh.component_videoplayer.playerbase.assist;

import android.os.Bundle;
import com.cjh.component_videoplayer.playerbase.entity.DataSource;
import com.cjh.component_videoplayer.playerbase.event.EventKey;
import com.cjh.component_videoplayer.playerbase.log.PLog;

/**
 * @author: caijianhui
 * @date: 2019/8/13 16:34
 * @description:
 */
public class OnAssistPlayEventHandler extends BaseEventAssistHandler<AssistPlay> {

    @Override
    public void requestPause(AssistPlay assistPlay, Bundle bundle) {
        if (assistPlay.isInPlaybackState()) {
            assistPlay.pause();
        } else {
            assistPlay.stop();
            assistPlay.reset();
        }
    }

    @Override
    public void requestResume(AssistPlay assistPlay, Bundle bundle) {
        if (assistPlay.isInPlaybackState()) {
            assistPlay.resume();
        } else {
            requestRetry(assistPlay, bundle);
        }
    }

    @Override
    public void requestSeek(AssistPlay assistPlay, Bundle bundle) {
        int pos = 0;
        if (bundle != null) {
            pos = bundle.getInt(EventKey.INT_DATA);
        }
        assistPlay.seekTo(pos);
    }

    @Override
    public void requestStop(AssistPlay assistPlay, Bundle bundle) {
        assistPlay.stop();
    }

    @Override
    public void requestReset(AssistPlay assist, Bundle bundle) {
        assist.reset();
    }

    @Override
    public void requestRetry(AssistPlay assistPlay, Bundle bundle) {
        int pos = 0;
        if(bundle!=null){
            pos = bundle.getInt(EventKey.INT_DATA);
        }
        assistPlay.rePlay(pos);
    }

    @Override
    public void requestReplay(AssistPlay assistPlay, Bundle bundle) {
        assistPlay.rePlay(0);
    }

    @Override
    public void requestPlayDataSource(AssistPlay assist, Bundle bundle) {
        if(bundle!=null){
            DataSource data = (DataSource) bundle.getSerializable(EventKey.SERIALIZABLE_DATA);
            if(data==null){
                PLog.e("OnAssistPlayEventHandler","requestPlayDataSource need legal data source");
                return;
            }
            assist.stop();
            assist.setDataSource(data);
            assist.play();
        }
    }


}
