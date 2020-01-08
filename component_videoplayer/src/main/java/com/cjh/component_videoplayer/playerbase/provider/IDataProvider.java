package com.cjh.component_videoplayer.playerbase.provider;

import android.os.Bundle;
import com.cjh.component_videoplayer.playerbase.entity.DataSource;

/**
 * @author: caijianhui
 * @date: 2019/8/9 17:39
 * @description:
 */
public interface IDataProvider {

    int PROVIDER_CODE_SUCCESS_MEDIA_DATA = -77001;

    int PROVIDER_CODE_DATA_PROVIDER_ERROR = -77003;

    void setOnProviderListener(OnProviderListener onProviderListener);

    /**
     * the provider handle data source, Users usually need to be implemented
     * @param sourceData
     */
    void handleSourceData(DataSource sourceData);

    /**
     * cancel the DataProvider handle data source.
     */
    void cancel();

    /**
     * destroy the provider.
     */
    void destroy();


    interface OnProviderListener{
        /**
         * on provider start load data
         */
        void onProviderDataStart();

        /**
         * on provider load data success
         * @param code
         * @param bundle you can set some data to bundle
         */
        void onProviderDataSuccess(int code, Bundle bundle);

        /**
         * on provider load data error
         * @param code
         * @param bundle
         */
        void onProviderError(int code, Bundle bundle);
    }

}
