package com.cjh.component_jetpack.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.cjh.component_jetpack.R;

/**
 * @author: caijianhui
 * @date: 2020/5/18 14:32
 * @description:
 */
public class NetImageView extends AppCompatImageView {

    public static final String TAG = "NetImageView";

    public NetImageView(Context context) {
        super(context);
    }

    public NetImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NetImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @BindingAdapter("url")
    public static void load(ImageView view, String url) {
        Log.e(TAG, "load url = " + url);
        Glide.with(view.getContext()).load(url)
                //.error(R.mipmap.ic_launcher)
                .into(view);
    }
}
