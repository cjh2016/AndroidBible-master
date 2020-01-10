package com.cjh.component_videoplayer.MediaLoader.callback;

import android.database.Cursor;
import androidx.loader.content.Loader;
import com.cjh.component_videoplayer.MediaLoader.inter.ILoader;

/**
 * @author: caijianhui
 * @date: 2019/9/19 19:28
 * @description:
 */
public abstract class OnLoaderCallBack implements ILoader {

    public abstract void onLoadFinish(Loader<Cursor> loader, Cursor data);

    public void onLoaderReset() {

    }
}
