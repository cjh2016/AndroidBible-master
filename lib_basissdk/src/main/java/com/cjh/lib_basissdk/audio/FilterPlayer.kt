package com.cjh.lib_basissdk.audio

/**
 * @author: caijianhui
 * @date: 2020/6/4 15:57
 * @description: 有点模仿 {@link #FilterInputStream}, 包装 Player, 从而达到不用修改 Player便可以扩展,
 *               有点类似装饰器模式
 */
open class FilterPlayer(private var player: Player?) : Player {

    fun getBase(): Player? {
        //实际执行操作的对象
        return player
    }

    override fun isStarted(): Boolean {
        return player?.isStarted()!!
    }

    override fun isPlaying(): Boolean {
        return player?.isPlaying()!!
    }

    override fun isPaused(): Boolean {
        return player?.isPaused()!!
    }

    override fun start(): Boolean {
        return player?.start()!!
    }

    override fun pause(): Boolean {
        return player?.pause()!!
    }

    override fun stop() {
        player?.stop()
    }

    override fun setPlayerListener(listener: PlayerListener?) {
        player?.setPlayerListener(listener)
    }
}