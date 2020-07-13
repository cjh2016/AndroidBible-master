package com.cjh.lib_common.dialog.holder;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

public class ViewHolder {

	private SparseArray<View> views;
	private View convertView;
	
	private ViewHolder(View view) {
		convertView = view;
		views = new SparseArray<View>();
	}
	
	public static ViewHolder create(View view) {
		return new ViewHolder(view);
	}
	
	public <T extends View> T getView(int viewId) {
		
		View view = views.get(viewId);
		if (null == view) {
			view = convertView.findViewById(viewId);
			views.put(viewId, view);
		}
		return (T) view;
	}
	
	public View getConvertView() {
        return convertView;
    }
	
	public void setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
    }
	
	public void setText(int viewId, int textId) {
        TextView textView = getView(viewId);
        textView.setText(textId);
    }
	
	public void setTextColor(int viewId, int colorId) {
        TextView textView = getView(viewId);
        textView.setTextColor(colorId);
    }
	
	public void setOnClickListener(int viewId, View.OnClickListener clickListener) {
        View view = getView(viewId);
        view.setOnClickListener(clickListener);
    }
	
	/**
	 * Add by cjh Date 2017-12-19 09:43:30
	 * @param viewId
	 * @param onFocusChangeListener
	 */
	public void setOnFocusChangeListener(int viewId, View.OnFocusChangeListener onFocusChangeListener) {
		View view =  getView(viewId);
		view.setOnFocusChangeListener(onFocusChangeListener);
	}
	
	public void setBackgroundResource(int viewId, int resId) {
        View view = getView(viewId);
        view.setBackgroundResource(resId);
    }
	
	public void setBackgroundColor(int viewId, int colorId) {
        View view = getView(viewId);
        view.setBackgroundColor(colorId);
    }
}
