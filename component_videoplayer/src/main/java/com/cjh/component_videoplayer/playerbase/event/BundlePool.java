package com.cjh.component_videoplayer.playerbase.event;

import android.os.Bundle;
import com.cjh.component_videoplayer.playerbase.log.PLog;

import java.util.List;

/**
 * @author: caijianhui
 * @date: 2019/8/9 12:06
 * @description:
 */
public class BundlePool {

    private static final int POOL_SIZE = 3;

    private static List<Bundle> mPool;

    public synchronized static Bundle obtain() {
        for(int i=0; i < POOL_SIZE; i++){
            if(mPool.get(i).isEmpty()){
                return mPool.get(i);
            }
        }
        PLog.w("BundlePool","<create new bundle object>");
        return new Bundle();
    }

}
