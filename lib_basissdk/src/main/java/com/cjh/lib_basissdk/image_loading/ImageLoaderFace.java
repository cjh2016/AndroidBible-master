package com.cjh.lib_basissdk.image_loading;

import android.graphics.Bitmap;
import android.media.Image;

import com.cjh.lib_basissdk.util.Utils;

/**
 * @author: caijianhui
 * @date: 2020/5/4 20:11
 * @description: 加载图片门面类
 */
public final class ImageLoaderFace implements ImageLoader {

    //默认原始三层缓存
    private ImageLoader mEngine = new OriginalImageLoader(Utils.getApp());

    public void setImageLoader(ImageLoader imageLoader) {
        this.mEngine = imageLoader;
    }

    @Override
    public Bitmap loadBitmap(String uri, int reqWidth, int reqHeight) {
        if (null == mEngine) {
            return null;
        }
        return mEngine.loadBitmap(uri, reqWidth, reqHeight);
    }
}
