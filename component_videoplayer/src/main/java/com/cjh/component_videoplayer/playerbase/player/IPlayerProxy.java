package com.cjh.component_videoplayer.playerbase.player;

import android.os.Bundle;
import com.cjh.component_videoplayer.playerbase.entity.DataSource;

/**
 * @author: caijianhui
 * @date: 2019/8/9 11:59
 * @description:
 */
public interface IPlayerProxy {

    void onDataSourceReady(DataSource dataSource);

    void onIntentStop();

    void onIntentReset();

    void onIntentDestroy();

    void onPlayerEvent(int eventCode, Bundle bundle);

    void onErrorEvent(int eventCode, Bundle bundle);

    int getRecord(DataSource dataSource);

}

