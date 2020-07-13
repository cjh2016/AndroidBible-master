package com.cjh.lib_basissdk.audio

/**
 * @author: caijianhui
 * @date: 2020/6/4 15:47
 * @description: 音频播放监听器
 */
interface PlayerListener {

    fun onStarted()

    fun onPaused()

    fun onResumed()

    fun onCompleted()

    fun onStopped()

    fun onError()
}