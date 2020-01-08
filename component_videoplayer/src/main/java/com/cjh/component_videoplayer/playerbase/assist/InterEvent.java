package com.cjh.component_videoplayer.playerbase.assist;

/**
 * @author: caijianhui
 * @date: 2019/8/9 16:37
 * @description:
 */
public interface InterEvent {
    int CODE_REQUEST_PAUSE = -66001;
    int CODE_REQUEST_RESUME = -66003;
    int CODE_REQUEST_SEEK = -66005;
    int CODE_REQUEST_STOP = -66007;
    int CODE_REQUEST_RESET = -66009;
    int CODE_REQUEST_RETRY = -660011;
    int CODE_REQUEST_REPLAY = -66013;
    int CODE_REQUEST_PLAY_DATA_SOURCE = -66014;
    int CODE_REQUEST_NOTIFY_TIMER = -66015;
    int CODE_REQUEST_STOP_TIMER = -66016;
}
