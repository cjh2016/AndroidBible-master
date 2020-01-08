package com.cjh.component_videoplayer.playerbase.extension;

/**
 * @author: caijianhui
 * @date: 2019/8/24 12:09
 * @description:
 *
 *  To manage multiple event producers
 *
 */
public interface IProducerGroup {

    void addEventProducer(BaseEventProducer eventProducer);

    boolean removeEventProducer(BaseEventProducer eventProducer);

    void destroy();

}
