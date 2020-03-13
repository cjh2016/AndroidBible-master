package com.cjh.component_materialdesign

import android.app.Application
import android.content.Context
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import java.io.File

class MaterialDesignSimpleApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        val displayImageOptions = DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .build()

        val imageLoaderConfiguration = ImageLoaderConfiguration
            .Builder(this@MaterialDesignSimpleApplication)
            .memoryCache(LruMemoryCache(MEMORY_SIZE))
            .diskCache(UnlimitedDiskCache(File(cacheDir, "caches")))
            .diskCacheSize(DISK_SIZE)
            .defaultDisplayImageOptions(displayImageOptions)
            .build()

        ImageLoader.getInstance().init(imageLoaderConfiguration)

        sContext = applicationContext
    }

    companion object {
        private const val MEMORY_SIZE = 5 * 1024 * 1024
        private const val DISK_SIZE = 20 * 1024 * 1024

        var sContext: Context? = null
    }
}