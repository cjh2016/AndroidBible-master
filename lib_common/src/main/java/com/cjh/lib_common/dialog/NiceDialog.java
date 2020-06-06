package com.cjh.lib_common.dialog;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.cjh.lib_common.dialog.holder.ViewConvertListener;
import com.cjh.lib_common.dialog.holder.ViewHolder;

public class NiceDialog extends BaseNiceDialog {
	
	private ViewConvertListener convertListener;
	
	public static NiceDialog init() {
		return new NiceDialog();
	}

	@Override
	public int getLayoutId() {
		return layoutId;
	}

	@Override
	public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
		//转移接口到抽象类
		if (convertListener != null) {
			convertListener.convertView(holder, dialog);
		}
	}
	
	public NiceDialog setLayoutId(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public NiceDialog setConvertListener(ViewConvertListener convertListener) {
        this.convertListener = convertListener;
        return this;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	if (savedInstanceState != null) {
    		convertListener = savedInstanceState.getParcelable("listener");
    	}
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	outState.putParcelable("listener", convertListener);
    }
    
    @Override
    public void onDestroyView() {
    	super.onDestroyView();
    	convertListener = null;
    }

}
