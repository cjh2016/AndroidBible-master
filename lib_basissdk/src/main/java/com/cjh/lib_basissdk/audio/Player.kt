package com.cjh.lib_basissdk.audio

/**
 * @author: caijianhui
 * @date: 2020/6/4 15:48
 * @description:
 */
interface Player {

    fun isStarted(): Boolean

    fun isPlaying(): Boolean

    fun isPaused(): Boolean

    fun start(): Boolean

    fun pause(): Boolean

    fun stop()

    fun setPlayerListener(listener: PlayerListener?)
}