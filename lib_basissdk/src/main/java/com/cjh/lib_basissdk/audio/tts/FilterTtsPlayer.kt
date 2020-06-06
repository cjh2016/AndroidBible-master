package com.cjh.lib_basissdk.audio.tts

import com.cjh.lib_basissdk.audio.FilterPlayer

/**
 * @author: caijianhui
 * @date: 2020/6/4 20:47
 * @description:
 */
class FilterTtsPlayer(private var player: TtsPlayer?): FilterPlayer(player), TtsPlayer {

    override fun getText(): CharSequence? {
        return player?.getText()
    }

    override fun setText(text: CharSequence?, start: Int, end: Int) {
        player?.setText(text, start, end)
    }

    override fun getTextStart(): Int {
        player?.apply {
            return getTextStart()
        }
        return -1
    }

    override fun getTextEnd(): Int {
        player?.apply {
            return getTextEnd()
        }
        return -1
    }

    override fun seekTo(position: Int) {
        player?.seekTo(position)
    }

    override fun getSpeakingStart(): Int {
        player?.apply {
            return getSpeakingStart()
        }
        return -1
    }

    override fun getSpeakingEnd(): Int {
        player?.apply {
            return getSpeakingEnd()
        }
        return -1
    }

    override fun getSettings(): TtsSettings? {
        player?.apply {
            return getSettings()
        }
        return null
    }

}