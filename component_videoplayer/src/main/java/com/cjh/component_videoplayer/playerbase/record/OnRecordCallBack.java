package com.cjh.component_videoplayer.playerbase.record;

import com.cjh.component_videoplayer.playerbase.entity.DataSource;

/**
 * @author: caijianhui
 * @date: 2019/8/13 16:56
 * @description:
 *
 *  if you want to custom save record, you can set it.
 */
public interface OnRecordCallBack {

    int onSaveRecord(DataSource dataSource, int record);

    int onGetRecord(DataSource dataSource);

    int onResetRecord(DataSource dataSource);

    int onRemoveRecord(DataSource dataSource);

    void onClearRecord();
}
