package com.cjh.component_videoplayer.playerbase.receiver;

/**
 * @author: caijianhui
 * @date: 2019/8/9 15:14
 * @description:
 *
 *  the state getter for receivers, because receivers dynamic attach,
 *  maybe you need get some state on attach.
 */
public interface StateGetter {

    PlayerStateGetter getPlayerStateGetter();
}
