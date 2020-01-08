package com.cjh.component_videoplayer.playerbase.extension;

/**
 * @author: caijianhui
 * @date: 2019/8/24 11:22
 * @description:
 *
 *  The producer of the event. It is usually added by the users themselves,
 *  such as the system's power change events or network change events.
 *  The framework adds the network change event producer by default.
 *
 */
public interface EventProducer {

    void onAdded();

    void onRemoved();

    ReceiverEventSender getSender();

    void destroy();
}
