package com.cjh.component_videoplayer.playerbase.player;

/**
 * @author: caijianhui
 * @date: 2019/8/9 13:46
 * @description:
 *   in AVPlayer default open timer proxy, you can use update callback to refresh UI.
 *   if you close timer proxy{@link AVPlayer#setUseTimerProxy(boolean)},
 *   you will not receive this timer update callback.
 *   if timer open , the call back called per second.
 *   in some scene, you can close it to improve battery performance.
 */
public interface OnTimerUpdateListener {
    void onTimerUpdate(int curr, int duration, int bufferPercentage);
}
