package com.cjh.lib_common.popupwindow;

import android.content.Context;
import android.view.View;

public abstract class AbsCustomPopup extends EasyPopup {

	private static final String TAG = AbsCustomPopup.class.getSimpleName();
	
	protected AbsCustomPopup(Context context) {
		super(context);
	}
	
	@Override
	public void onPopupWindowCreated() {
		super.onPopupWindowCreated();
		//执行设置PopupWindow属性也可以通过Builder中设置
        //setContentView(x,x,x);
        //...
		initAttributes();
	}
	
	@Override
	protected void onPopupWindowViewCreated(View contentView) {
		super.onPopupWindowViewCreated(contentView);
		initViews(contentView);
	}
	
	@Override
	protected void onPopupWindowDismiss() {
		super.onPopupWindowDismiss();
	}
	
	/***************************************************/
	
	/**
	 * 可以在此方法中设置PopupWindow需要的属性
	 */
	protected abstract void initAttributes();
	
	/**
     * 初始化view {@see getView}
     *
     * @param view
     */
	protected abstract void initViews(View view);

}
