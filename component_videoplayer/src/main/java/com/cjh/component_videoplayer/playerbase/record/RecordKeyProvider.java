package com.cjh.component_videoplayer.playerbase.record;

import com.cjh.component_videoplayer.playerbase.entity.DataSource;

/**
 * @author: caijianhui
 * @date: 2019/8/13 16:57
 * @description:
 */
public interface RecordKeyProvider {

    String generatorKey(DataSource dataSource);
}
