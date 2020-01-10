package com.cjh.lib_common.base

import android.app.Application

interface IBaseApp {

    fun onInitSpeed(application: Application): Boolean

    fun onInitLow(application: Application): Boolean
}