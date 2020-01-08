package com.cjh.component_videoplayer.playerbase.player;

import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.cjh.component_videoplayer.playerbase.entity.DataSource;
import com.cjh.component_videoplayer.playerbase.event.BundlePool;
import com.cjh.component_videoplayer.playerbase.event.EventKey;
import com.cjh.component_videoplayer.playerbase.event.OnErrorEventListener;
import com.cjh.component_videoplayer.playerbase.event.OnPlayerEventListener;

/**
 * @author: caijianhui
 * @date: 2019/8/9 12:23
 * @description:
 */
public abstract class BaseInternalPlayer implements IPlayer {

    private int mCurrentState = STATE_IDLE;

    private OnPlayerEventListener mOnPlayerEventListener;
    private OnErrorEventListener mOnErrorEventListener;
    private OnBufferingListener mOnBufferingListener;

    private int mBufferPercentage;

    @Override
    public void option(int code, Bundle bundle) {
        //not handle
    }

    @Override
    public void setOnBufferingListener(OnBufferingListener onBufferingListener) {
        this.mOnBufferingListener = onBufferingListener;
    }

    @Override
    public void setOnPlayerEventListener(OnPlayerEventListener onPlayerEventListener) {
        this.mOnPlayerEventListener = onPlayerEventListener;
    }

    @Override
    public void setOnErrorEventListener(OnErrorEventListener onErrorEventListener) {
        this.mOnErrorEventListener = onErrorEventListener;
    }

    protected final void submitPlayerEvent(int eventCode, Bundle bundle) {
        if(mOnPlayerEventListener!=null)
            mOnPlayerEventListener.onPlayerEvent(eventCode, bundle);
    }

    protected final void submitErrorEvent(int eventCode, Bundle bundle) {
        if(mOnErrorEventListener!=null)
            mOnErrorEventListener.onErrorEvent(eventCode, bundle);
    }

    protected final void submitBufferingUpdate(int bufferPercentage, Bundle extra) {
        mBufferPercentage = bufferPercentage;
        if(mOnBufferingListener!=null)
            mOnBufferingListener.onBufferingUpdate(bufferPercentage, extra);
    }

    protected final void updateStatus(int status){
        this.mCurrentState = status;
        Bundle bundle = BundlePool.obtain();
        bundle.putInt(EventKey.INT_DATA, status);
        submitPlayerEvent(OnPlayerEventListener.PLAYER_EVENT_ON_STATUS_CHANGE, bundle);
    }

    @Override
    public int getBufferPercentage() {
        return mBufferPercentage;
    }

    @Override
    public int getState() {
        return mCurrentState;
    }

}
