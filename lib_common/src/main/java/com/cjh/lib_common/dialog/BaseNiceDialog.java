package com.cjh.lib_common.dialog;


import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cjh.lib_basissdk.util.ScreenUtils;
import com.cjh.lib_basissdk.util.SizeUtils;
import com.cjh.lib_common.R;
import com.cjh.lib_common.dialog.holder.ViewHolder;

public abstract class BaseNiceDialog extends DialogFragment {

	private int margin;//左右边距
	private int width;//宽度
	private int height;//高度
	private float dimAmount = 0.5f;//灰度深浅
	private boolean showBottom;//是否底部显示
	private boolean outCancel = true;//是否点击外部取消
	@StyleRes
	private int animStyle;
	@LayoutRes
	protected int layoutId;
	
	public abstract int getLayoutId();
	
	public abstract void convertView(ViewHolder holder, BaseNiceDialog dialog);
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, R.style.NiceDialog);
		layoutId = getLayoutId();
		
		//恢复保存的数据
		if (null != savedInstanceState) {
			
			margin = savedInstanceState.getInt(DialogProperty.MARGIN);
			width = savedInstanceState.getInt(DialogProperty.WIDTH);
			height = savedInstanceState.getInt(DialogProperty.HEIGHT);
			dimAmount = savedInstanceState.getFloat(DialogProperty.DIM);
			showBottom = savedInstanceState.getBoolean(DialogProperty.BOTTOM);
			outCancel = savedInstanceState.getBoolean(DialogProperty.CANCEL);
			animStyle = savedInstanceState.getInt(DialogProperty.ANIM);
			layoutId = savedInstanceState.getInt(DialogProperty.LAYOUT);
		}
	}
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId, container, false);
        convertView(ViewHolder.create(view), this);
        return view;
    }
	
	@Override
	public void onStart() {
		super.onStart();
		initParams();
	}
	
	/**
     * 屏幕旋转等导致DialogFragment销毁后重建时保存数据
     *
     * @param outState
     */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(DialogProperty.MARGIN, margin);
		outState.putInt(DialogProperty.WIDTH, width);
		outState.putInt(DialogProperty.HEIGHT, height);
		outState.putFloat(DialogProperty.DIM, dimAmount);
		outState.putBoolean(DialogProperty.BOTTOM, showBottom);
		outState.putBoolean(DialogProperty.CANCEL, outCancel);
		outState.putInt(DialogProperty.ANIM, animStyle);
		outState.putInt(DialogProperty.LAYOUT, layoutId);
	}
	
	private void initParams() {
		
		Window window = getDialog().getWindow();
		if (null != window) {
			WindowManager.LayoutParams lp = window.getAttributes();
			//调节灰色背景透明度[0-1]，默认0.5f
			lp.dimAmount = dimAmount;
			//是否在底部显示
			if (showBottom) {
				lp.gravity = Gravity.BOTTOM;
				
				if (animStyle == 0) {
					animStyle = R.style.DefaultAnimation;
				}
			}
			
			//设置dialog宽度
			if (width == 0) {
				
				lp.width = ScreenUtils.getScreenWidth() - 2 * SizeUtils.dp2px(margin);
			} else if (width == -1) {
				lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
			} else {
				lp.width = SizeUtils.dp2px(width);
			}
			
			//设置dialog高度
            if (height == 0) {
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                lp.height = SizeUtils.dp2px(height);
            }
			
            //设置dialog进入、退出的动画
            window.setWindowAnimations(animStyle);
            window.setAttributes(lp);
		}
		setCancelable(outCancel);
	}
	
	public BaseNiceDialog setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    public BaseNiceDialog setWidth(int width) {
        this.width = width;
        return this;
    }

    public BaseNiceDialog setHeight(int height) {
        this.height = height;
        return this;
    }

    public BaseNiceDialog setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public BaseNiceDialog setShowBottom(boolean showBottom) {
        this.showBottom = showBottom;
        return this;
    }

    public BaseNiceDialog setOutCancel(boolean outCancel) {
        this.outCancel = outCancel;
        return this;
    }
    
    public BaseNiceDialog setAnimStyle(@StyleRes int animStyle) {
    	this.animStyle = animStyle;
    	return this;
    }
    
    /**
     * 显示弹框{@link #show(FragmentTransaction, String)}
     * @param manager
     * @return
     */
    public BaseNiceDialog show(FragmentManager manager) {
    	FragmentTransaction ft = manager.beginTransaction();
    	if (this.isAdded()) {
    		ft.remove(this).commit();
    	}
    	ft.add(this, String.valueOf(System.currentTimeMillis()));
    	ft.commitAllowingStateLoss();
    	return this;
    }
    
}
