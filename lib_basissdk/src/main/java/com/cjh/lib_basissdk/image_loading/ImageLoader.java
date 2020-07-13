package com.cjh.lib_basissdk.image_loading;

import android.graphics.Bitmap;

/**
 * @author: caijianhui
 * @date: 2020/5/4 20:08
 * @description:
 */
public interface ImageLoader {

    /**
     * 根据uri加载图片
     * @param uri
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    Bitmap loadBitmap(String uri, int reqWidth, int reqHeight);

}
