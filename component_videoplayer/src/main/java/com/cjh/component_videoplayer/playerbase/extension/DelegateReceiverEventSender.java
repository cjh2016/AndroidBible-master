package com.cjh.component_videoplayer.playerbase.extension;

import android.os.Bundle;

import com.cjh.component_videoplayer.playerbase.receiver.IReceiverGroup;

/**
 * @author: caijianhui
 * @date: 2019/8/26 9:31
 * @description:
 */
public interface DelegateReceiverEventSender {

    void sendEvent(int eventCode, Bundle bundle, IReceiverGroup.OnReceiverFilter receiverFilter);

    void sendObject(String key, Object value, IReceiverGroup.OnReceiverFilter receiverFilter);
}
