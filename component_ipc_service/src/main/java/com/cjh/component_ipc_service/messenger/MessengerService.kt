package com.cjh.component_ipc_service.messenger

import android.app.Service
import android.content.Intent
import android.os.*

/**
 * @author: caijianhui
 * @date: 2020/1/15 11:18
 * @description:
 */
class MessengerService: Service() {

    companion object {
        //国际惯例，打印TAG
        var TAG = MessengerService::class.java.simpleName

        const val MSG_FROM_CLIENT = 0
        const val MSG_FROM_SERVICE = 1

        const val MSG_KEY = "msg_key"

    }

    inner class MessengerHandler: Handler() {

        override fun handleMessage(msg: Message?) {
            when(msg?.what) {
                MSG_FROM_CLIENT -> {
                    val client = msg?.replyTo
                    val replyMsg = Message.obtain(null, MSG_FROM_SERVICE)
                    val bundle = Bundle()
                    bundle.putString(MSG_KEY, "服务器已经收到你的消息，稍后回复你！")
                    replyMsg.data = bundle

                    try {
                        client.send(replyMsg)
                    } catch (e: RemoteException) {
                        e.printStackTrace()
                    }
                }

                else -> super.handleMessage(msg)
            }
        }
    }

    private val mMessenger: Messenger? = Messenger(MessengerHandler())

    override fun onBind(intent: Intent?): IBinder? {
        return mMessenger?.binder
    }

}