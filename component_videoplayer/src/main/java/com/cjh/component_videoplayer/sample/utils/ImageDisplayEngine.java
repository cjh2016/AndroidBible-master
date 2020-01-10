package com.cjh.component_videoplayer.sample.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * @author: caijianhui
 * @date: 2019/9/5 15:17
 * @description:
 */
public class ImageDisplayEngine {

    public static void display(Context context, ImageView view, String path, int defaultHolder){
        /*GlideApp.with(context)
                .load(path)
                .centerCrop()
                .placeholder(defaultHolder)
                .into(view);*/
    }

    public static void displayNoCenterCrop(Context context, ImageView view, String path, int defaultHolder){
        /*GlideApp.with(context)
                .load(path)
                .placeholder(defaultHolder)
                .into(view);*/
    }

    public static void displayAsBitmap(Context context, ImageView view, String path, int defaultHolder){
        /*GlideApp.with(context)
                .load(path)
                .centerCrop()
                .placeholder(defaultHolder)
                .into(view);*/
    }

    public static void displayAsBitmap(Context context, String path, final OnBitmapResourceCallBack callBack){
        /*GlideApp.with(context)
                .load(path)
                .dontAnimate()
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if(callBack!=null){
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) resource;
                            Bitmap bitmap = null;
                            if(bitmapDrawable!=null){
                                bitmap = bitmapDrawable.getBitmap();
                            }
                            callBack.onResourceReady(bitmap);
                        }
                    }
                });*/
    }

    public interface OnBitmapResourceCallBack{
        void onResourceReady(Bitmap bitmap);
    }

}

