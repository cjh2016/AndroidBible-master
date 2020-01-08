package com.cjh.component_videoplayer.playerbase.extension;

/**
 * @author: caijianhui
 * @date: 2019/8/24 11:31
 * @description:
 */
public abstract class BaseEventProducer implements EventProducer{

    private ReceiverEventSender mReceiverEventSender;

    void attachSender(ReceiverEventSender receiverEventSender){
        this.mReceiverEventSender = receiverEventSender;
    }

    @Override
    public ReceiverEventSender getSender() {
        return mReceiverEventSender;
    }

}
