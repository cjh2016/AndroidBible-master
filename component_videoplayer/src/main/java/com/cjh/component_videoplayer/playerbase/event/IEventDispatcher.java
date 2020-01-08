package com.cjh.component_videoplayer.playerbase.event;

import android.os.Bundle;
import android.view.MotionEvent;
import com.cjh.component_videoplayer.playerbase.receiver.IReceiverGroup;

/**
 * @author: caijianhui
 * @date: 2019/8/9 15:08
 * @description:
 */
public interface IEventDispatcher {

    void dispatchPlayEvent(int eventCode, Bundle bundle);
    void dispatchErrorEvent(int eventCode, Bundle bundle);
    void dispatchReceiverEvent(int eventCode, Bundle bundle);
    void dispatchReceiverEvent(int eventCode, Bundle bundle, IReceiverGroup.OnReceiverFilter onReceiverFilter);
    void dispatchProducerEvent(int eventCode, Bundle bundle, IReceiverGroup.OnReceiverFilter onReceiverFilter);
    void dispatchProducerData(String key, Object data, IReceiverGroup.OnReceiverFilter onReceiverFilter);


    void dispatchTouchEventOnSingleTabUp(MotionEvent event);
    void dispatchTouchEventOnDoubleTabUp(MotionEvent event);
    void dispatchTouchEventOnDown(MotionEvent event);
    void dispatchTouchEventOnScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);
    void dispatchTouchEventOnEndGesture();
}
