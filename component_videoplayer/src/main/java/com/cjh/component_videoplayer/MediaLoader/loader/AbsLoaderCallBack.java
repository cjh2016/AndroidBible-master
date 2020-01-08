package com.cjh.component_videoplayer.MediaLoader.loader;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.cjh.component_videoplayer.MediaLoader.callback.OnLoaderCallBack;

import java.lang.ref.WeakReference;

/**
 * @author: caijianhui
 * @date: 2019/9/21 15:09
 * @description:
 */
public class AbsLoaderCallBack implements LoaderManager.LoaderCallbacks<Cursor> {

    private WeakReference<Context> context;

    private OnLoaderCallBack onLoaderCallBack;
    private int mLoaderId;

    public AbsLoaderCallBack(Context context, OnLoaderCallBack onLoaderCallBack){
        this.context = new WeakReference<>(context);
        this.onLoaderCallBack = onLoaderCallBack;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        mLoaderId = id;
        return new BaseCursorLoader(context.get(), onLoaderCallBack);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        onLoaderCallBack.onLoadFinish(loader, data);
        destroyLoader();
    }

    private void destroyLoader(){
        try {
            if(context!=null){
                Context ctx = this.context.get();
                if(ctx!=null){
                    ((FragmentActivity)ctx).getSupportLoaderManager().destroyLoader(mLoaderId);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        onLoaderCallBack.onLoaderReset();
    }
}
