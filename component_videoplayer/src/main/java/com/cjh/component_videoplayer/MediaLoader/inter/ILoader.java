package com.cjh.component_videoplayer.MediaLoader.inter;

import android.net.Uri;

/**
 * @author: caijianhui
 * @date: 2019/9/19 19:23
 * @description:
 */
public interface ILoader {

    String[] getSelectProjection();
    Uri getQueryUri();
    String getSortOrderSql();
    String getSelections();
    String[] getSelectionsArgs();
}
