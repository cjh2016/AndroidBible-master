package com.cjh.lib_basissdk.audio.tts

import com.cjh.lib_basissdk.audio.Player

/**
 * @author: caijianhui
 * @date: 2020/6/4 16:07
 * @description:
 */
interface TtsPlayer: Player {

    fun getText(): CharSequence?

    fun setText(text: CharSequence?, start: Int, end: Int)

    fun getTextStart(): Int

    fun getTextEnd(): Int

    /**
     * call this method after [.setText]
     * @param position ([.getTextStart], [.getTextEnd]]
     */
    fun seekTo(position: Int)

    fun getSpeakingStart(): Int

    fun getSpeakingEnd(): Int

    fun getSettings(): TtsSettings?
}