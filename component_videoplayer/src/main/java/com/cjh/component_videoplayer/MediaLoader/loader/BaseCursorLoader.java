package com.cjh.component_videoplayer.MediaLoader.loader;


import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.cjh.component_videoplayer.MediaLoader.inter.ILoader;

/**
 * @author: caijianhui
 * @date: 2019/9/20 15:18
 * @description:
 */
public class BaseCursorLoader extends CursorLoader {

    public BaseCursorLoader(Context context, ILoader iLoader) {
        super(context);
        setProjection(iLoader.getSelectProjection());
        setUri(iLoader.getQueryUri());
        setSelection(iLoader.getSelections());
        setSelectionArgs(iLoader.getSelectionsArgs());
        setSortOrder(iLoader.getSortOrderSql());
    }

}
