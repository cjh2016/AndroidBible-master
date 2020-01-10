package com.cjh.component_videoplayer.playerbase.receiver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: caijianhui
 * @date: 2019/8/9 15:19
 * @description:
 *
 *  This class is a bridge of data sharing between the receivers.
 *  default put data will call back ValueUpdateListeners.
 *
 */
public final class GroupValue implements ValueInter {

    private Map<String,Object> mValueMap;
    private Map<IReceiverGroup.OnGroupValueUpdateListener, String[]> mListenerKeys;

    private List<IReceiverGroup.OnGroupValueUpdateListener> mOnGroupValueUpdateListeners;

    public GroupValue(){
        mValueMap = new ConcurrentHashMap<>();
        mListenerKeys = new ConcurrentHashMap<>();
        mOnGroupValueUpdateListeners = new CopyOnWriteArrayList<>();
    }

    //If you want to listen to changes in some data,
    //you can register a listener to implement it.
    public void registerOnGroupValueUpdateListener(
            IReceiverGroup.OnGroupValueUpdateListener onGroupValueUpdateListener){
        if(mOnGroupValueUpdateListeners.contains(onGroupValueUpdateListener))
            return;
        mOnGroupValueUpdateListeners.add(onGroupValueUpdateListener);
        //sort it for Arrays.binarySearch();
        String[] keyArrays = onGroupValueUpdateListener.filterKeys();
        Arrays.sort(keyArrays);
        mListenerKeys.put(onGroupValueUpdateListener, keyArrays);
        //when listener add, if user observe keys in current KeySet, call back it.
        checkCurrentKeySet(onGroupValueUpdateListener);
    }

    private void checkCurrentKeySet(
            IReceiverGroup.OnGroupValueUpdateListener onGroupValueUpdateListener) {
        Set<String> keys = mValueMap.keySet();
        for (String key : keys) {
            if (containsKey(mListenerKeys.get(onGroupValueUpdateListener), key)) {
                onGroupValueUpdateListener.onValueUpdate(key, mValueMap.get(key));
            }
        }
    }

    public void unregisterOnGroupValueUpdateListener(
            IReceiverGroup.OnGroupValueUpdateListener onGroupValueUpdateListener){
        mListenerKeys.remove(onGroupValueUpdateListener);
        mOnGroupValueUpdateListeners.remove(onGroupValueUpdateListener);
    }

    public void clearOnGroupValueUpdateListeners(){
        mOnGroupValueUpdateListeners.clear();
    }

    public void clearValues(){
        mValueMap.clear();
    }

    @Override
    public void putBoolean(String key, boolean value){
        put(key, value);
    }

    @Override
    public void putInt(String key, int value){
        put(key, value);
    }

    @Override
    public void putString(String key, String value){
        put(key, value);
    }

    @Override
    public void putFloat(String key, float value){
        put(key, value);
    }

    @Override
    public void putLong(String key, long value){
        put(key, value);
    }

    @Override
    public void putDouble(String key, double value){
        put(key, value);
    }

    @Override
    public void putObject(String key, Object value){
        put(key, value);
    }

    @Override
    public void putBoolean(String key, boolean value, boolean notifyUpdate) {
        put(key, value, notifyUpdate);
    }

    @Override
    public void putInt(String key, int value, boolean notifyUpdate) {
        put(key, value, notifyUpdate);
    }

    @Override
    public void putString(String key, String value, boolean notifyUpdate) {
        put(key, value, notifyUpdate);
    }

    @Override
    public void putFloat(String key, float value, boolean notifyUpdate) {
        put(key, value, notifyUpdate);
    }

    @Override
    public void putLong(String key, long value, boolean notifyUpdate) {
        put(key, value, notifyUpdate);
    }

    @Override
    public void putDouble(String key, double value, boolean notifyUpdate) {
        put(key, value, notifyUpdate);
    }

    @Override
    public void putObject(String key, Object value, boolean notifyUpdate) {
        put(key, value, notifyUpdate);
    }

    private void put(String key, Object value){
        put(key, value, true);
    }

    private void put(String key, Object value, boolean notifyUpdate){
        mValueMap.put(key, value);
        if(notifyUpdate)
            callBackValueUpdate(key, value);
    }

    private void callBackValueUpdate(String key, Object value) {
        List<IReceiverGroup.OnGroupValueUpdateListener> mCallbacks = new ArrayList<>();
        //filter callbacks
        for (IReceiverGroup.OnGroupValueUpdateListener listener : mOnGroupValueUpdateListeners) {
            if (containsKey(mListenerKeys.get(listener), key)) {
                mCallbacks.add(listener);
            }
        }
        //call back
        for(IReceiverGroup.OnGroupValueUpdateListener callback : mCallbacks){
            callback.onValueUpdate(key, value);
        }
    }

    private boolean containsKey(String[] keys, String nowKey){
        if(keys!=null && keys.length>0){
            return Arrays.binarySearch(keys, nowKey) >= 0;
        }
        return false;
    }

    @Override
    public <T> T get(String key){
        Object o = mValueMap.get(key);
        try {
            if(o!=null){
                return (T) o;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean getBoolean(String key){
        return getBoolean(key, false);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue){
        Boolean bool = get(key);
        if(bool==null)
            return defaultValue;
        return bool;
    }

    @Override
    public int getInt(String key){
        return getInt(key, 0);
    }

    @Override
    public int getInt(String key, int defaultValue){
        Integer integer = get(key);
        if(integer==null)
            return defaultValue;
        return integer;
    }

    @Override
    public String getString(String key){
        String s = get(key);
        return s;
    }

    @Override
    public float getFloat(String key){
        return getFloat(key, 0f);
    }

    @Override
    public float getFloat(String key, float defaultValue){
        Float f = get(key);
        if(f==null)
            return defaultValue;
        return f;
    }

    @Override
    public long getLong(String key){
        return getLong(key, 0);
    }

    @Override
    public long getLong(String key, long defaultValue){
        Long l = get(key);
        if(l==null)
            return defaultValue;
        return l;
    }

    @Override
    public double getDouble(String key){
        return getDouble(key, 0);
    }

    @Override
    public double getDouble(String key, double defaultValue){
        Double d = get(key);
        if(d==null)
            return defaultValue;
        return d;
    }


}