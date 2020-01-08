package com.cjh.component_videoplayer.exoplayer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.EventLog;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.cjh.component_videoplayer.playerbase.config.AppContextAttach;
import com.cjh.component_videoplayer.playerbase.config.PlayerConfig;
import com.cjh.component_videoplayer.playerbase.config.PlayerLibrary;
import com.cjh.component_videoplayer.playerbase.entity.DataSource;
import com.cjh.component_videoplayer.playerbase.entity.DecoderPlan;
import com.cjh.component_videoplayer.playerbase.entity.TimedTextSource;
import com.cjh.component_videoplayer.playerbase.event.BundlePool;
import com.cjh.component_videoplayer.playerbase.event.EventKey;
import com.cjh.component_videoplayer.playerbase.event.OnErrorEventListener;
import com.cjh.component_videoplayer.playerbase.event.OnPlayerEventListener;
import com.cjh.component_videoplayer.playerbase.log.PLog;
import com.cjh.component_videoplayer.playerbase.player.BaseInternalPlayer;
import com.cjh.component_videoplayer.playerbase.player.IPlayer;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.AssetDataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;

import java.util.HashMap;

/**
 * @author: caijianhui
 * @date: 2019/8/26 20:18
 * @description:
 */
public class ExoMediaPlayer extends BaseInternalPlayer {

    private final String TAG = "ExoMediaPlayer";

    public static final int PLAN_ID = 200;

    private final Context mAppContext;
    private SimpleExoPlayer mInternalPlayer;

    private int mVideoWidth, mVideoHeight;

    private int mStartPos = -1;

    private boolean isPreparing = true;
    private boolean isBuffering = false;
    private boolean isPendingSeek = false;

    private final DefaultBandwidthMeter mBandwidthMeter;

    public static void init(Context context){
        PlayerConfig.addDecoderPlan(new DecoderPlan(
                PLAN_ID,
                ExoMediaPlayer.class.getName(),
                "exoplayer"));
        PlayerConfig.setDefaultPlanId(PLAN_ID);
        PlayerLibrary.init(context);
    }

    public ExoMediaPlayer() {
        mAppContext = AppContextAttach.getApplicationContext();
        RenderersFactory renderersFactory = new DefaultRenderersFactory(mAppContext);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        mInternalPlayer = ExoPlayerFactory.newSimpleInstance(mAppContext, renderersFactory, trackSelector);

        // Measures bandwidth during playback. Can be null if not required.
        mBandwidthMeter = new DefaultBandwidthMeter();

        mInternalPlayer.addListener(mEventListener);
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        updateStatus(STATE_INITIALIZED);
        mInternalPlayer.addVideoListener(mVideoListener);
        String data = dataSource.getData();
        Uri uri = dataSource.getUri();
        String assetsPath = dataSource.getAssetsPath();
        int rawId = dataSource.getRawId();

        Uri videoUri = null;

        if (!TextUtils.isEmpty(data)) {
            videoUri = Uri.parse(data);
        } else if (uri != null) {
            videoUri = uri;
        } else if (!TextUtils.isEmpty(assetsPath)) {
            try {
                DataSpec dataSpec = new DataSpec(DataSource.buildAssetsUri(assetsPath));
                AssetDataSource assetDataSource = new AssetDataSource(mAppContext);
                assetDataSource.open(dataSpec);
                videoUri = assetDataSource.getUri();
            } catch (AssetDataSource.AssetDataSourceException e) {
                e.printStackTrace();
            }
        } else if (rawId > 0) {
            try {
                DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(dataSource.getRawId()));
                RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(mAppContext);
                rawResourceDataSource.open(dataSpec);
                videoUri = rawResourceDataSource.getUri();
            } catch (RawResourceDataSource.RawResourceDataSourceException e) {
                e.printStackTrace();
            }
        }

        if (null == videoUri) {
            Bundle bundle = BundlePool.obtain();
            bundle.putString(EventKey.STRING_DATA, "Incorrect setting of playback data!");
            submitErrorEvent(OnErrorEventListener.ERROR_EVENT_IO, bundle);
            return;
        }

        //create DefaultDataSourceFactory
        com.google.android.exoplayer2.upstream.DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(mAppContext, Util.getUserAgent(mAppContext, mAppContext.getPackageName()), mBandwidthMeter);

        //if scheme is http or https and DataSource contain extra data, use DefaultHttpDataSourceFactory.
        String scheme = videoUri.getScheme();
        HashMap<String, String> extra = dataSource.getExtra();
        if (extra != null && extra.size() > 0 && ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme))) {
            dataSourceFactory = new DefaultHttpDataSourceFactory(Util.getUserAgent(mAppContext, mAppContext.getPackageName()));
            ((DefaultHttpDataSourceFactory) dataSourceFactory).getDefaultRequestProperties().set(extra);
        }

        // Prepare the player with the source.
        isPreparing = true;

        //create MediaSource
        MediaSource mediaSource = getMediaSource(videoUri, dataSourceFactory);

        //handle timed text source
        TimedTextSource timedTextSource = dataSource.getTimedTextSource();
        if(timedTextSource!=null){
            Format format = Format.createTextSampleFormat(null, timedTextSource.getMimeType(), timedTextSource.getFlag(), null);
            MediaSource timedTextMediaSource = new SingleSampleMediaSource.Factory(new DefaultDataSourceFactory(mAppContext,
                    Util.getUserAgent(mAppContext, mAppContext.getPackageName())))
                    .createMediaSource(Uri.parse(timedTextSource.getPath()), format, C.TIME_UNSET);
            //merge MediaSource and timedTextMediaSource.
            mediaSource = new MergingMediaSource(mediaSource, timedTextMediaSource);
        }

        mInternalPlayer.prepare(mediaSource);
        mInternalPlayer.setPlayWhenReady(false);

        Bundle sourceBundle = BundlePool.obtain();
        sourceBundle.putSerializable(EventKey.SERIALIZABLE_DATA, dataSource);
        submitPlayerEvent(OnPlayerEventListener.PLAYER_EVENT_ON_DATA_SOURCE_SET,sourceBundle);

    }

    private MediaSource getMediaSource(Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory dataSourceFactory){
        int contentType = Util.inferContentType(uri);
        switch (contentType) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_SS:
                return new SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_OTHER:
            default:
                // This is the MediaSource representing the media to be played.
                return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        }
    }

    @Override
    public void setDisplay(SurfaceHolder surfaceHolder) {
        mInternalPlayer.setVideoSurfaceHolder(surfaceHolder);
        submitPlayerEvent(OnPlayerEventListener.PLAYER_EVENT_ON_SURFACE_HOLDER_UPDATE, null);
    }

    @Override
    public void setSurface(Surface surface) {
        mInternalPlayer.setVideoSurface(surface);
        submitPlayerEvent(OnPlayerEventListener.PLAYER_EVENT_ON_SURFACE_UPDATE, null);
    }

    @Override
    public void setVolume(float left, float right) {
        mInternalPlayer.setVolume(left);
    }

    @Override
    public void setSpeed(float speed) {
        PlaybackParameters parameters = new PlaybackParameters(speed, 1f);
        mInternalPlayer.setPlaybackParameters(parameters);
    }

    @Override
    public boolean isPlaying() {
        if (null == mInternalPlayer)
            return false;
        int state = mInternalPlayer.getPlaybackState();
        switch (state) {
            case Player.STATE_BUFFERING:
            case Player.STATE_READY:
                return mInternalPlayer.getPlayWhenReady();
            case Player.STATE_IDLE:
            case Player.STATE_ENDED:
            default:
                return false;
        }
    }

    @Override
    public int getCurrentPosition() {
        return (int) mInternalPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return (int) mInternalPlayer.getDuration();
    }

    @Override
    public int getAudioSessionId() {
        return mInternalPlayer.getAudioSessionId();
    }

    @Override
    public int getVideoWidth() {
        return mVideoWidth;
    }

    @Override
    public int getVideoHeight() {
        return mVideoHeight;
    }

    @Override
    public void start() {
        mInternalPlayer.setPlayWhenReady(true);
    }

    @Override
    public void start(int msc) {
        mStartPos = msc;
        start();
    }

    @Override
    public void pause() {
        int state = getState();
        if(isInPlaybackState()
                && state!=STATE_END
                && state!=STATE_ERROR
                && state!=STATE_IDLE
                && state!=STATE_INITIALIZED
                && state!=STATE_PAUSED
                && state!=STATE_STOPPED)
            mInternalPlayer.setPlayWhenReady(false);
    }

    @Override
    public void resume() {
        if(isInPlaybackState() && getState() == STATE_PAUSED)
            mInternalPlayer.setPlayWhenReady(true);
    }

    @Override
    public void seekTo(int msc) {
        if(isInPlaybackState()){
            isPendingSeek = true;
        }
        mInternalPlayer.seekTo(msc);
        Bundle bundle = BundlePool.obtain();
        bundle.putInt(EventKey.INT_DATA, msc);
        submitPlayerEvent(OnPlayerEventListener.PLAYER_EVENT_ON_SEEK_TO, bundle);
    }

    @Override
    public void stop() {
        isPreparing = true;
        isBuffering = false;
        updateStatus(IPlayer.STATE_STOPPED);
        mInternalPlayer.stop();
    }

    @Override
    public void reset() {
        stop();
    }

    @Override
    public void destroy() {
        isPreparing = true;
        isBuffering = false;
        updateStatus(IPlayer.STATE_END);
        mInternalPlayer.removeListener(mEventListener);
        mInternalPlayer.removeVideoListener(mVideoListener);
        mInternalPlayer.release();
    }

    private boolean isInPlaybackState(){
        int state = getState();
        return state!=STATE_END
                && state!=STATE_ERROR
                && state!=STATE_INITIALIZED
                && state!=STATE_STOPPED;
    }

    private VideoListener mVideoListener = new VideoListener() {

        @Override
        public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
            mVideoWidth = width;
            mVideoHeight = height;
            Bundle bundle = BundlePool.obtain();
            bundle.putInt(EventKey.INT_ARG1, mVideoWidth);
            bundle.putInt(EventKey.INT_ARG2, mVideoHeight);
            bundle.putInt(EventKey.INT_ARG3, 0);
            bundle.putInt(EventKey.INT_ARG4, 0);
            submitPlayerEvent(OnPlayerEventListener.PLAYER_EVENT_ON_VIDEO_SIZE_CHANGE, bundle);
        }

        @Override
        public void onRenderedFirstFrame() {
            PLog.d(TAG,"onRenderedFirstFrame");
            updateStatus(IPlayer.STATE_STARTED);
            submitPlayerEvent(OnPlayerEventListener.PLAYER_EVENT_ON_VIDEO_RENDER_START, null);
        }

    };

    private Player.EventListener mEventListener = new Player.EventListener() {

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            int bufferPercentage = mInternalPlayer.getBufferedPercentage();
            if(!isLoading){
                submitBufferingUpdate(bufferPercentage, null);
            }
            PLog.d(TAG,"onLoadingChanged : "+ isLoading + ", bufferPercentage = " + bufferPercentage);
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            PLog.d(TAG,"onPlayerStateChanged : playWhenReady = "+ playWhenReady
                    + ", playbackState = " + playbackState);

            if(!isPreparing){
                if(playWhenReady){
                    updateStatus(IPlayer.STATE_STARTED);
                    submitPlayerEvent(OnPlayerEventListener.PLAYER_EVENT_ON_RESUME, null);
                }else{
                    updateStatus(IPlayer.STATE_PAUSED);
                    submitPlayerEvent(OnPlayerEventListener.PLAYER_EVENT_ON_PAUSE, null);
                }
            }

            if(isPreparing){
                switch (playbackState){
                    case Player.STATE_READY:
                        isPreparing = false;
                        Format format = mInternalPlayer.getVideoFormat();
                        Bundle bundle = BundlePool.obtain();
                        if(format!=null){
                            bundle.putInt(EventKey.INT_ARG1, format.width);
                            bundle.putInt(EventKey.INT_ARG2, format.height);
                        }
                        updateStatus(IPlayer.STATE_PREPARED);
                        submitPlayerEvent(OnPlayerEventListener.PLAYER_EVENT_ON_PREPARED, bundle);

                        if(playWhenReady){
                            updateStatus(STATE_STARTED);
                            submitPlayerEvent(OnPlayerEventListener.PLAYER_EVENT_ON_START, null);
                        }

                        if(mStartPos > 0){
                            mInternalPlayer.seekTo(mStartPos);
                            mStartPos = -1;
                        }
                        break;
                }
            }

            if(isBuffering){
                switch (playbackState){
                    case Player.STATE_READY:
                    case Player.STATE_ENDED:
                        long bitrateEstimate = mBandwidthMeter.getBitrateEstimate();
                        PLog.d(TAG,"buffer_end, BandWidth : " + bitrateEstimate);
                        isBuffering = false;
                        Bundle bundle = BundlePool.obtain();
                        bundle.putLong(EventKey.LONG_DATA, bitrateEstimate);
                        submitPlayerEvent(OnPlayerEventListener.PLAYER_EVENT_ON_BUFFERING_END, bundle);
                        break;
                }
            }

            if(isPendingSeek){
                switch (playbackState){
                    case Player.STATE_READY:
                        isPendingSeek = false;
                        submitPlayerEvent(OnPlayerEventListener.PLAYER_EVENT_ON_SEEK_COMPLETE, null);
                        break;
                }
            }

            if(!isPreparing){
                switch (playbackState){
                    case Player.STATE_BUFFERING:
                        long bitrateEstimate = mBandwidthMeter.getBitrateEstimate();
                        PLog.d(TAG,"buffer_start, BandWidth : " + bitrateEstimate);
                        isBuffering = true;
                        Bundle bundle = BundlePool.obtain();
                        bundle.putLong(EventKey.LONG_DATA, bitrateEstimate);
                        submitPlayerEvent(OnPlayerEventListener.PLAYER_EVENT_ON_BUFFERING_START, bundle);
                        break;
                    case Player.STATE_ENDED:
                        updateStatus(IPlayer.STATE_PLAYBACK_COMPLETE);
                        submitPlayerEvent(OnPlayerEventListener.PLAYER_EVENT_ON_PLAY_COMPLETE, null);
                        break;
                }
            }

        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {

        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            if(error==null){
                submitErrorEvent(OnErrorEventListener.ERROR_EVENT_UNKNOWN, null);
                return;
            }
            PLog.e(TAG,error.getMessage()==null?"":error.getMessage());
            int type = error.type;
            switch (type){
                case ExoPlaybackException.TYPE_SOURCE:
                    submitErrorEvent(OnErrorEventListener.ERROR_EVENT_IO, null);
                    break;
                case ExoPlaybackException.TYPE_RENDERER:
                    submitErrorEvent(OnErrorEventListener.ERROR_EVENT_COMMON, null);
                    break;
                case ExoPlaybackException.TYPE_UNEXPECTED:
                    submitErrorEvent(OnErrorEventListener.ERROR_EVENT_UNKNOWN, null);
                    break;
            }
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            PLog.d(TAG,"onPlaybackParametersChanged : " + playbackParameters.toString());
        }
    };

}
