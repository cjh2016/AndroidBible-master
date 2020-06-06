package com.cjh.lib_basissdk.audio

/**
 * @author: caijianhui
 * @date: 2020/6/4 15:50
 * @description: 有点模仿 {@link #FilterInputStream}, 包装 PlayerListener, 从而达到不用修改 PlayerListener
 *               便可以扩展, 有点类似装饰器模式
 */
class FilterPlayerListener(private var listener: PlayerListener?) : PlayerListener {

    fun getBase(): PlayerListener? {
        return listener
    }

    override fun onStarted() {
        listener?.onStarted()
    }

    override fun onResumed() {
        listener?.onResumed()
    }

    override fun onPaused() {
        listener?.onPaused()
    }

    override fun onStopped() {
        listener?.onStopped()
    }

    override fun onCompleted() {
        listener?.onCompleted()
    }

    override fun onError() {
        listener?.onError()
    }
}