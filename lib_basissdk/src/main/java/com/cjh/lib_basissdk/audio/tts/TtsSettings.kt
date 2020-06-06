package com.cjh.lib_basissdk.audio.tts

/**
 * @author: caijianhui
 * @date: 2020/6/4 16:11
 * @description:
 */
abstract class TtsSettings : Cloneable {

    open fun isSpeechSpeedSupported(): Boolean {
        return false
    }

    open fun set(settings: TtsSettings?): TtsSettings? {
        return this
    }

    open fun getSpeechSpeed(): Float {
        return 0.0f
    }

    /**
     *
     * @param speechSpeed [-1, 1], -1 slowest, 0 normal, 1 fastest
     * @return
     */
    open fun setSpeechSpeed(speechSpeed: Float): TtsSettings? {
        return this
    }

    open fun reset() {

    }

    override fun clone(): TtsSettings {
        return try {
            (super.clone() as TtsSettings)
        } catch (e: Throwable) {
            throw RuntimeException(e)
        }
    }

}