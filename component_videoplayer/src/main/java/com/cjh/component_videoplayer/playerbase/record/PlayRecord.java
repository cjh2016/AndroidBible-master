package com.cjh.component_videoplayer.playerbase.record;

import com.cjh.component_videoplayer.playerbase.entity.DataSource;
import com.cjh.component_videoplayer.playerbase.log.PLog;

/**
 * @author: caijianhui
 * @date: 2019/8/13 17:04
 * @description:
 */
public class PlayRecord {

    private final String TAG = "PlayRecord";
    private static PlayRecord i;

    private RecordInvoker mRecordInvoker;

    private PlayRecord(){
        mRecordInvoker = new RecordInvoker(PlayRecordManager.getConfig());
    }

    public static PlayRecord get(){
        if(null==i){
            synchronized (PlayRecord.class){
                if(null==i){
                    i = new PlayRecord();
                }
            }
        }
        return i;
    }

    public int record(DataSource data, int record){
        if(data==null)
            return -1;
        int saveRecord = mRecordInvoker.saveRecord(data, record);
        PLog.d(TAG,"<<Save>> : record = " + record);
        return saveRecord;
    }

    public int reset(DataSource data){
        if(data==null)
            return -1;
        return mRecordInvoker.resetRecord(data);
    }

    public int removeRecord(DataSource data){
        if(data==null)
            return -1;
        return mRecordInvoker.removeRecord(data);
    }

    public int getRecord(DataSource data){
        if(data==null)
            return 0;
        int record = mRecordInvoker.getRecord(data);
        PLog.d(TAG,"<<Get>> : record = " + record);
        return record;
    }

    public void clearRecord(){
        mRecordInvoker.clearRecord();
    }

    public void destroy(){
        clearRecord();
        i = null;
    }

}
