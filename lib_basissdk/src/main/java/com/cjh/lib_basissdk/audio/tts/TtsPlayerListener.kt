package com.cjh.lib_basissdk.audio.tts

import com.cjh.lib_basissdk.audio.PlayerListener

/**
 * @author: caijianhui
 * @date: 2020/6/4 16:05
 * @description: tts播放监听器，增加了播放进度的功能
 */
interface TtsPlayerListener : PlayerListener {

    fun onProgress(start: Int, end: Int)

}