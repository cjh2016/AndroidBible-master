package com.cjh.component_videoplayer.playerbase.extension;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: caijianhui
 * @date: 2019/8/24 12:11
 * @description:
 */
public final class ProducerGroup implements IProducerGroup {

    private ReceiverEventSender mEventSender;

    private List<BaseEventProducer> mEventProducers;

    public ProducerGroup(ReceiverEventSender eventSender){
        this.mEventSender = eventSender;
        mEventProducers = new CopyOnWriteArrayList<>();
    }

    @Override
    public void addEventProducer(BaseEventProducer eventProducer) {
        if(!mEventProducers.contains(eventProducer)){
            eventProducer.attachSender(mEventSender);
            mEventProducers.add(eventProducer);
            eventProducer.onAdded();
        }
    }

    @Override
    public boolean removeEventProducer(BaseEventProducer eventProducer) {
        boolean remove = mEventProducers.remove(eventProducer);
        if(eventProducer!=null){
            eventProducer.onRemoved();
            eventProducer.attachSender(null);
        }
        return remove;
    }

    @Override
    public void destroy() {
        for(BaseEventProducer eventProducer : mEventProducers){
            eventProducer.onRemoved();
            eventProducer.destroy();
            eventProducer.attachSender(null);
        }
        mEventProducers.clear();
    }
}
