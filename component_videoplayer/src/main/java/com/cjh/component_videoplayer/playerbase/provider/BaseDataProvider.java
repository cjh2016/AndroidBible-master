package com.cjh.component_videoplayer.playerbase.provider;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.cjh.component_videoplayer.playerbase.entity.DataSource;
import com.cjh.component_videoplayer.playerbase.event.BundlePool;
import com.cjh.component_videoplayer.playerbase.event.EventKey;

/**
 * @author: caijianhui
 * @date: 2019/8/9 17:40
 * @description:
 */
public abstract class BaseDataProvider implements IDataProvider {

    private OnProviderListener mOnProviderListener;

    @Override
    public final void setOnProviderListener(OnProviderListener onProviderListener) {
        this.mOnProviderListener = onProviderListener;
    }

    /**
     * call back provider start. Recommended invocation.
     */
    protected final void onProviderDataStart(){
        if(mOnProviderListener!=null)
            mOnProviderListener.onProviderDataStart();
    }

    /**
     * send media data for player. must invocation.
     * @param bundle
     */
    @Deprecated
    protected final void onProviderMediaDataSuccess(@NonNull Bundle bundle){
        if(mOnProviderListener!=null)
            mOnProviderListener.onProviderDataSuccess(PROVIDER_CODE_SUCCESS_MEDIA_DATA, bundle);
    }

    /**
     * send media data for player. must invocation.
     * @param dataSource
     */
    protected final void onProviderMediaDataSuccess(@NonNull DataSource dataSource){
        Bundle bundle = BundlePool.obtain();
        bundle.putSerializable(EventKey.SERIALIZABLE_DATA, dataSource);
        if(mOnProviderListener!=null)
            mOnProviderListener.onProviderDataSuccess(PROVIDER_CODE_SUCCESS_MEDIA_DATA, bundle);
    }

    /**
     * send extra data, usually custom by yourself according to your need.
     * @param code
     * @param bundle
     */
    protected final void onProviderExtraDataSuccess(int code, Bundle bundle){
        if(mOnProviderListener!=null)
            mOnProviderListener.onProviderDataSuccess(code, bundle);
    }

    /**
     * when provider media data error. must invocation.
     * @param bundle
     */
    protected final void onProviderMediaDataError(@NonNull Bundle bundle){
        if(mOnProviderListener!=null)
            mOnProviderListener.onProviderError(PROVIDER_CODE_DATA_PROVIDER_ERROR, bundle);
    }

    /**
     * if occur error, It is strongly recommended to call the method.
     * @param code
     * @param bundle
     */
    protected final void onProviderError(int code, Bundle bundle){
        if(mOnProviderListener!=null)
            mOnProviderListener.onProviderError(code, bundle);
    }

}
