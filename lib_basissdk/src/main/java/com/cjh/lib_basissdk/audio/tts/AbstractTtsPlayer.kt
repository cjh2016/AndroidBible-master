package com.cjh.lib_basissdk.audio.tts

import com.cjh.lib_basissdk.audio.PlayerListener


/**
 * @author: caijianhui
 * @date: 2020/6/5 9:24
 * @description:  抽象tts播放器
 */
abstract class AbstractTtsPlayer: TtsPlayer {

    //要播放的内容
    private var mText : CharSequence? = null

    //起始位置
    private var mTextStart  = 0

    //结束位置
    private var mTextEnd = 0

    private var mPlayerListener: PlayerListener? = null

    private var mTtsPlayerListener: TtsPlayerListener? = null

    override fun getText(): CharSequence? {
        return mText
    }

    protected open fun onTextChanged() {
    }

    override fun setText(text: CharSequence?, start: Int, end: Int) {
        stop() //先stop
        mText = text
        mTextStart = start
        mTextEnd = end
        onTextChanged()
    }

    override fun getTextStart(): Int {
        return mTextStart
    }

    override fun getTextEnd(): Int {
        return mTextEnd
    }

    override fun setPlayerListener(listener: PlayerListener?) {
        mPlayerListener = listener
        mTtsPlayerListener = if (listener is TtsPlayerListener) {
            listener
        } else {
            null
        }
    }

    /**
     * 执行onStarted回调
     */
    protected open fun performListenerOnStarted() {
        val listener = mPlayerListener
        listener?.onStarted()
    }

    /**
     * 执行onPaused回调
     */
    protected open fun performListenerOnPaused() {
        val listener = mPlayerListener
        listener?.onPaused()
    }

    /**
     * 执行onResumed回调
     */
    protected open fun performListenerOnResumed() {
        val listener = mPlayerListener
        listener?.onResumed()
    }

    /**
     * 执行onCompleted回调
     */
    protected open fun performListenerOnCompleted() {
        val listener = mPlayerListener
        listener?.onCompleted()
    }

    /**
     * 执行onStopped回调
     */
    protected open fun performListenerOnStopped() {
        val listener = mPlayerListener
        listener?.onStopped()
    }

    /**
     * 执行onError回调
     */
    protected open fun performListenerOnError() {
        val listener = mPlayerListener
        listener?.onError()
    }

    /**
     * 执行onProgress回调
     */
    protected open fun performListenerOnProgress(start: Int, end: Int) {
        val listener = mTtsPlayerListener
        listener?.onProgress(start, end)
    }


}