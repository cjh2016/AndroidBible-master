package com.cjh.component_videoplayer.playerbase.assist;

import android.view.ViewGroup;
import com.cjh.component_videoplayer.playerbase.entity.DataSource;
import com.cjh.component_videoplayer.playerbase.event.OnErrorEventListener;
import com.cjh.component_videoplayer.playerbase.event.OnPlayerEventListener;
import com.cjh.component_videoplayer.playerbase.provider.IDataProvider;
import com.cjh.component_videoplayer.playerbase.receiver.IReceiverGroup;
import com.cjh.component_videoplayer.playerbase.receiver.OnReceiverEventListener;
import com.cjh.component_videoplayer.playerbase.render.AspectRatio;

/**
 * @author: caijianhui
 * @date: 2019/8/13 16:24
 * @description:
 *
 *  The Association for auxiliary view containers and players
 */
public interface AssistPlay {

    void setOnPlayerEventListener(OnPlayerEventListener onPlayerEventListener);
    void setOnErrorEventListener(OnErrorEventListener onErrorEventListener);
    void setOnReceiverEventListener(OnReceiverEventListener onReceiverEventListener);

    void setOnProviderListener(IDataProvider.OnProviderListener onProviderListener);
    void setDataProvider(IDataProvider dataProvider);
    boolean switchDecoder(int decoderPlanId);

    void setRenderType(int renderType);
    void setAspectRatio(AspectRatio aspectRatio);

    void setVolume(float left, float right);
    void setSpeed(float speed);

    void setReceiverGroup(IReceiverGroup receiverGroup);

    void attachContainer(ViewGroup userContainer);

    void setDataSource(DataSource dataSource);

    void play();
    void play(boolean updateRender);

    boolean isInPlaybackState();
    boolean isPlaying();
    int getCurrentPosition();
    int getDuration();
    int getAudioSessionId();
    int getBufferPercentage();
    int getState();

    void rePlay(int msc);

    void pause();
    void resume();
    void seekTo(int msc);
    void stop();
    void reset();
    void destroy();


}
