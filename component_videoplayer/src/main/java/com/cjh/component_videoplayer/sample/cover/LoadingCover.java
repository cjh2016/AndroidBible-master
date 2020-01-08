package com.cjh.component_videoplayer.sample.cover;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.cjh.component_videoplayer.R;
import com.cjh.component_videoplayer.playerbase.event.OnPlayerEventListener;
import com.cjh.component_videoplayer.playerbase.player.IPlayer;
import com.cjh.component_videoplayer.playerbase.receiver.BaseCover;
import com.cjh.component_videoplayer.playerbase.receiver.PlayerStateGetter;

/**
 * @author: caijianhui
 * @date: 2019/9/9 14:55
 * @description:
 */
public class LoadingCover extends BaseCover {

    public LoadingCover(Context context) {
        super(context);
    }

    @Override
    protected void onCoverAttachedToWindow() {
        super.onCoverAttachedToWindow();
        PlayerStateGetter playerStateGetter = getPlayerStateGetter();
        if(playerStateGetter!=null && isInPlaybackState(playerStateGetter)){
            setLoadingState(playerStateGetter.isBuffering());
        }
    }

    private boolean isInPlaybackState(PlayerStateGetter playerStateGetter){
        int state = playerStateGetter.getState();
        return state!= IPlayer.STATE_END
                && state!= IPlayer.STATE_ERROR
                && state!= IPlayer.STATE_IDLE
                && state!= IPlayer.STATE_INITIALIZED
                && state!= IPlayer.STATE_STOPPED;
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode){
            case OnPlayerEventListener.PLAYER_EVENT_ON_BUFFERING_START:
            case OnPlayerEventListener.PLAYER_EVENT_ON_DATA_SOURCE_SET:
            case OnPlayerEventListener.PLAYER_EVENT_ON_PROVIDER_DATA_START:
            case OnPlayerEventListener.PLAYER_EVENT_ON_SEEK_TO:
                setLoadingState(true);
                break;

            case OnPlayerEventListener.PLAYER_EVENT_ON_VIDEO_RENDER_START:
            case OnPlayerEventListener.PLAYER_EVENT_ON_BUFFERING_END:
            case OnPlayerEventListener.PLAYER_EVENT_ON_STOP:
            case OnPlayerEventListener.PLAYER_EVENT_ON_PROVIDER_DATA_ERROR:
            case OnPlayerEventListener.PLAYER_EVENT_ON_SEEK_COMPLETE:
                setLoadingState(false);
                break;
        }
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {
        setLoadingState(false);
    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {

    }

    private void setLoadingState(boolean show){
        setCoverVisibility(show? View.VISIBLE:View.GONE);
    }

    @Override
    protected View onCreateCoverView(Context context) {
        return View.inflate(context, R.layout.layout_loading_cover, null);
    }

    @Override
    public int getCoverLevel() {
        return levelMedium(1);
    }

}
