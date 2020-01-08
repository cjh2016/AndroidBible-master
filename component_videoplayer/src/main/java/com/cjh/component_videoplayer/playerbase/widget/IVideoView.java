package com.cjh.component_videoplayer.playerbase.widget;

import com.cjh.component_videoplayer.playerbase.entity.DataSource;
import com.cjh.component_videoplayer.playerbase.render.AspectRatio;
import com.cjh.component_videoplayer.playerbase.render.IRender;

/**
 * @author: caijianhui
 * @date: 2019/8/13 16:44
 * @description:
 */
public interface IVideoView {

    void setDataSource(DataSource dataSource);

    void setRenderType(int renderType);
    void setAspectRatio(AspectRatio aspectRatio);
    boolean switchDecoder(int decoderPlanId);

    void setVolume(float left, float right);
    void setSpeed(float speed);

    IRender getRender();

    boolean isInPlaybackState();
    boolean isPlaying();
    int getCurrentPosition();
    int getDuration();
    int getAudioSessionId();
    int getBufferPercentage();
    int getState();

    void start();
    void start(int msc);
    void pause();
    void resume();
    void seekTo(int msc);
    void stop();
    void stopPlayback();
}
