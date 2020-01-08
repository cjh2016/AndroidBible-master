package com.cjh.component_videoplayer.playerbase.extension;

import android.os.Bundle;

import com.cjh.component_videoplayer.playerbase.receiver.IReceiverGroup;

/**
 * @author: caijianhui
 * @date: 2019/8/24 11:28
 * @description:
 */
public interface ReceiverEventSender {

    void sendEvent(int eventCode, Bundle bundle);
    void sendEvent(int eventCode, Bundle bundle, IReceiverGroup.OnReceiverFilter receiverFilter);

    void sendBoolean(String key, boolean value);
    void sendBoolean(String key, boolean value, IReceiverGroup.OnReceiverFilter receiverFilter);

    void sendInt(String key, int value);
    void sendInt(String key, int value, IReceiverGroup.OnReceiverFilter receiverFilter);

    void sendString(String key, String value);
    void sendString(String key, String value, IReceiverGroup.OnReceiverFilter receiverFilter);

    void sendFloat(String key, float value);
    void sendFloat(String key, float value, IReceiverGroup.OnReceiverFilter receiverFilter);

    void sendLong(String key, long value);
    void sendLong(String key, long value, IReceiverGroup.OnReceiverFilter receiverFilter);

    void sendDouble(String key, double value);
    void sendDouble(String key, double value, IReceiverGroup.OnReceiverFilter receiverFilter);

    void sendObject(String key, Object value);
    void sendObject(String key, Object value, IReceiverGroup.OnReceiverFilter receiverFilter);
}
