package com.cjh.component_videoplayer.playerbase.record;

import android.os.Bundle;
import com.cjh.component_videoplayer.playerbase.entity.DataSource;
import com.cjh.component_videoplayer.playerbase.event.OnPlayerEventListener;
import com.cjh.component_videoplayer.playerbase.player.IPlayer;
import com.cjh.component_videoplayer.playerbase.player.IPlayerProxy;

/**
 * @author: caijianhui
 * @date: 2019/8/13 17:30
 * @description:
 */
public class RecordProxyPlayer implements IPlayerProxy {

    private PlayValueGetter mPlayValueGetter;

    private DataSource mDataSource;

    public RecordProxyPlayer(PlayValueGetter valueGetter){
        this.mPlayValueGetter = valueGetter;
    }

    @Override
    public void onDataSourceReady(DataSource dataSource) {
        //right now change DataSource, record it.
        record();
        mDataSource = dataSource;
    }

    @Override
    public void onIntentStop() {
        record();
    }

    @Override
    public void onIntentReset() {
        record();
    }

    @Override
    public void onIntentDestroy() {
        record();
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode){
            case OnPlayerEventListener.PLAYER_EVENT_ON_PAUSE:
                //on pause, record play position.
                record();
                break;
            case OnPlayerEventListener.PLAYER_EVENT_ON_PLAY_COMPLETE:
                //on play complete, reset play record.
                PlayRecord.get().reset(mDataSource);
                break;
        }
    }

    private void record(){
        if(isInPlaybackState() && getState()!= IPlayer.STATE_PLAYBACK_COMPLETE){
            PlayRecord.get().record(mDataSource, getCurrentPosition());
        }
    }

    private int getCurrentPosition(){
        if(mPlayValueGetter!=null)
            return mPlayValueGetter.getCurrentPosition();
        return 0;
    }

    private int getState(){
        return mPlayValueGetter!=null?mPlayValueGetter.getState():IPlayer.STATE_IDLE;
    }

    private boolean isInPlaybackState() {
        int state = getState();
        return state!= IPlayer.STATE_END
                && state!= IPlayer.STATE_ERROR
                && state!= IPlayer.STATE_IDLE
                && state!= IPlayer.STATE_INITIALIZED
                && state!= IPlayer.STATE_STOPPED;
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public int getRecord(DataSource dataSource){
        return PlayRecord.get().getRecord(dataSource);
    }


}
