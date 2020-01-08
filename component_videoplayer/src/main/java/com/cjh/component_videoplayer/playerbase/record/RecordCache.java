package com.cjh.component_videoplayer.playerbase.record;

import android.util.LruCache;

/**
 * @author: caijianhui
 * @date: 2019/8/13 16:58
 * @description:
 *
 *  play position  memory record, use LruCache.
 */
public class RecordCache {

    private LruCache<String, Integer> mLruCache;

    public RecordCache(int maxCacheCount) {
        mLruCache = new LruCache<String, Integer>(maxCacheCount * 4) {
            @Override
            protected int sizeOf(String key, Integer value) {
                return 4;
            }
        };
    }

    public int putRecord(String key, int record){
        Integer put = mLruCache.put(key, record);
        return put!=null?put:-1;
    }

    public int removeRecord(String key){
        Integer remove = mLruCache.remove(key);
        return remove!=null?remove:-1;
    }

    public int getRecord(String key){
        Integer integer = mLruCache.get(key);
        return integer!=null?integer:0;
    }

    public void clearRecord(){
        mLruCache.evictAll();
    }

}
