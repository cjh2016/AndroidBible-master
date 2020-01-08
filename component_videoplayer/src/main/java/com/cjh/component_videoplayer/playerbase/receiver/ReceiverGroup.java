package com.cjh.component_videoplayer.playerbase.receiver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: caijianhui
 * @date: 2019/8/9 16:25
 * @description:
 */
public final class ReceiverGroup implements IReceiverGroup {

    //receiver key value collection
    private Map<String, IReceiver> mReceivers;
    //receiver array for loop
    private List<IReceiver> mReceiverArray;
    //receiver items change listener
    private List<OnReceiverGroupChangeListener> mOnReceiverGroupChangeListeners;

    private GroupValue mGroupValue;

    public ReceiverGroup(){
        this(null);
    }

    public ReceiverGroup(GroupValue groupValue){
        mReceivers = new ConcurrentHashMap<>(16);
        mReceiverArray = Collections.synchronizedList(new ArrayList<IReceiver>());
        mOnReceiverGroupChangeListeners = new CopyOnWriteArrayList<>();
        if(groupValue==null){
            mGroupValue = new GroupValue();
        }else{
            mGroupValue = groupValue;
        }
    }

    @Override
    public void addOnReceiverGroupChangeListener(
            OnReceiverGroupChangeListener onReceiverGroupChangeListener) {
        if(mOnReceiverGroupChangeListeners.contains(onReceiverGroupChangeListener))
            return;
        mOnReceiverGroupChangeListeners.add(onReceiverGroupChangeListener);
    }

    @Override
    public void removeOnReceiverGroupChangeListener(OnReceiverGroupChangeListener onReceiverGroupChangeListener) {
        mOnReceiverGroupChangeListeners.remove(onReceiverGroupChangeListener);
    }

    void callBackOnReceiverAdd(String key, IReceiver receiver){
        for(OnReceiverGroupChangeListener listener:mOnReceiverGroupChangeListeners){
            listener.onReceiverAdd(key, receiver);
        }
    }

    void callBackOnReceiverRemove(String key, IReceiver receiver){
        for(OnReceiverGroupChangeListener listener:mOnReceiverGroupChangeListeners){
            listener.onReceiverRemove(key, receiver);
        }
    }

    @Override
    public void addReceiver(String key, IReceiver receiver){
        ((BaseReceiver)receiver).setKey(key);
        receiver.bindGroup(this);
        //call back method onReceiverBind().
        receiver.onReceiverBind();
        mReceivers.put(key, receiver);
        mReceiverArray.add(receiver);
        //call back on receiver add
        callBackOnReceiverAdd(key, receiver);
    }

    @Override
    public void removeReceiver(String key) {
        //remove it from map
        IReceiver receiver = mReceivers.remove(key);
        //remove it from array
        mReceiverArray.remove(receiver);
        //call back some methods
        onReceiverRemove(key, receiver);
    }

    private void onReceiverRemove(String key, IReceiver receiver) {
        if(receiver!=null){
            //call back on receiver remove
            callBackOnReceiverRemove(key, receiver);
            //call back method onReceiverUnBind().
            receiver.onReceiverUnBind();
        }
    }

    @Override
    public void sort(Comparator<IReceiver> comparator) {
        Collections.sort(mReceiverArray, comparator);
    }

    @Override
    public void forEach(OnLoopListener onLoopListener) {
        forEach(null, onLoopListener);
    }

    @Override
    public void forEach(OnReceiverFilter filter, OnLoopListener onLoopListener) {
        for(IReceiver receiver:mReceiverArray){
            if(filter==null || filter.filter(receiver))
                onLoopListener.onEach(receiver);
        }
    }

    @Override
    public <T extends IReceiver> T getReceiver(String key) {
        if(mReceivers!=null)
            return (T) mReceivers.get(key);
        return null;
    }

    @Override
    public GroupValue getGroupValue() {
        return mGroupValue;
    }

    @Override
    public void clearReceivers(){
        for(IReceiver receiver:mReceiverArray){
            onReceiverRemove(receiver.getKey(), receiver);
        }
        mReceiverArray.clear();
        mReceivers.clear();
    }

}
