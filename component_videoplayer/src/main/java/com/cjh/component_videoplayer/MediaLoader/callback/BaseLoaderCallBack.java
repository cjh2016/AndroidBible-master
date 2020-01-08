package com.cjh.component_videoplayer.MediaLoader.callback;

import android.provider.MediaStore;

/**
 * @author: caijianhui
 * @date: 2019/9/19 19:32
 * @description:
 */
public abstract class BaseLoaderCallBack<T> extends OnLoaderCallBack {

    public abstract void onResult(T result);

    @Override
    public String getSelections() {
        return MediaStore.MediaColumns.SIZE + " > ?";
    }

    @Override
    public String[] getSelectionsArgs() {
        return new String[]{"0"};
    }

    @Override
    public String getSortOrderSql() {
        return MediaStore.MediaColumns.DATE_MODIFIED + " DESC";
    }

}
