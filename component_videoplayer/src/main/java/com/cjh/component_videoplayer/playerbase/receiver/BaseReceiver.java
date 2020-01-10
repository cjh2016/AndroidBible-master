package com.cjh.component_videoplayer.playerbase.receiver;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cjh.component_videoplayer.playerbase.log.PLog;
import com.cjh.component_videoplayer.playerbase.receiver.*;

/**
 * @author: caijianhui
 * @date: 2019/8/9 15:39
 * @description:
 */
public abstract class BaseReceiver implements IReceiver, StateGetter {

    private Context mContext;
    private OnReceiverEventListener mOnReceiverEventListener;

    private IReceiverGroup mHostGroup;
    private StateGetter mStateGetter;

    private String mKey;

    /**
     * If you want to use Activity related functions or features,
     * please pass in the context of Activity.
     * Use it carefully to avoid memory leaks
     *
     * @param context
     *
     */
    public BaseReceiver(Context context){
        this.mContext = context;
    }

    /**
     * When the receiver is added to the group, callback this method.
     */
    public void onReceiverBind(){

    }

    /**
     * When the receiver is removed in the group, callback this method.
     */
    @Override
    public void onReceiverUnBind() {

    }

    /**
     * Bind the ReceiverGroup. This method is called by the inside of the framework.
     * Please do not call this method.
     *
     * @param receiverGroup
     */
    @Override
    public final void bindGroup(@NonNull IReceiverGroup receiverGroup) {
        this.mHostGroup = receiverGroup;
    }

    protected final GroupValue getGroupValue(){
        return mHostGroup.getGroupValue();
    }

    @Override
    public final void bindStateGetter(StateGetter stateGetter) {
        this.mStateGetter = stateGetter;
    }

    @Override
    @Nullable
    public final PlayerStateGetter getPlayerStateGetter() {
        if(mStateGetter!=null)
            return mStateGetter.getPlayerStateGetter();
        return null;
    }

    /**
     * Bind the listener. This method is called by the inside of the framework.
     * Please do not call this method.
     *
     * @param onReceiverEventListener
     */
    @Override
    public final void bindReceiverEventListener(OnReceiverEventListener onReceiverEventListener) {
        this.mOnReceiverEventListener = onReceiverEventListener;
    }

    /**
     * A receiver sends an event, and the receiver in the same group can receive the event.
     * @param eventCode
     * @param bundle
     */
    protected final void notifyReceiverEvent(int eventCode, Bundle bundle){
        if(mOnReceiverEventListener!=null)
            mOnReceiverEventListener.onReceiverEvent(eventCode, bundle);
    }

    /**
     * Send an event to the specified receiver,
     * make sure that the key value you imported is correct.
     *
     * @param key The unique value of a receiver can be found.
     * @param eventCode
     * @param bundle
     *
     * @return Bundle Return value after the receiver's response, nullable.
     *
     */
    protected final @Nullable Bundle notifyReceiverPrivateEvent(
            @NonNull String key, int eventCode, Bundle bundle){
        if(mHostGroup!=null && !TextUtils.isEmpty(key)){
            IReceiver receiver = mHostGroup.getReceiver(key);
            if(receiver!=null){
                return receiver.onPrivateEvent(eventCode, bundle);
            }else{
                PLog.e("BaseReceiver",
                        "not found receiver use you incoming key.");
            }
        }
        return null;
    }

    /**
     * private event
     * @param eventCode
     * @param bundle
     *
     * @return
     */
    @Override
    public Bundle onPrivateEvent(int eventCode, Bundle bundle) {
        return null;
    }

    /**
     * producer event from producers send.
     * @param eventCode
     * @param bundle
     */
    @Override
    public void onProducerEvent(int eventCode, Bundle bundle) {

    }

    /**
     * producer data from producers send
     * @param key
     * @param data
     */
    @Override
    public void onProducerData(String key, Object data) {

    }

    protected final Context getContext(){
        return mContext;
    }

    //default tag is class simple name
    public Object getTag(){
        return this.getClass().getSimpleName();
    }

    void setKey(String key){
        this.mKey = key;
    }

    /**
     * the receiver key you put.
     * @return
     */
    @Override
    public final String getKey() {
        return mKey;
    }

}
