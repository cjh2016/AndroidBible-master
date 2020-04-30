package com.cjh.lib_basissdk.util;

import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.BatteryManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.IdRes;
import androidx.viewpager.widget.ViewPager;

public final class ViewUtils {
	
	private static final String METHOD_GET_MAX_WIDTH = "getMaxWidth";
    private static final String METHOD_GET_MAX_HEIGHT = "getMaxHeight";
    private static final String METHOD_GET_MIN_WIDTH = "getMinWidth";
    private static final String METHOD_GET_MIN_HEIGHT = "getMinHeight";
    private static final String METHOD_SET_MAX_WIDTH = "setMaxWidth";
    private static final String METHOD_SET_MAX_HEIGHT = "setMaxHeight";
    
    private static final String FRAGMENT_CON = "NoSaveStateFrameLayout";

	
	private ViewUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}
	
	/**
     * activity中通过Id获取控件的方法，根据声明的控件类型强制类型转换
     *
     * @param activity 控件所在的activity
     * @param resId    控件的id
     * @param <E>      继承自View的控件类型
     * @return 控件声明的类型
     */
    public static <E extends View> E findViewById(Activity activity, @IdRes int resId) {
        //noinspection unchecked
        return (E) activity.findViewById(resId);
    }

    /**
     * activity中通过Id获取控件的方法，根据声明的控件类型强制类型转换
     *
     * @param view  控件所在的View对象
     * @param resId 控件的id
     * @param <E>   继承自View的控件类型
     * @return 控件声明的类型
     */
    public static <E extends View> E findViewById(View view, @IdRes int resId) {
        if (null == view)
            throw new IllegalArgumentException(
                    "The argument view can not be null,please check your argument!");
        //noinspection unchecked
        return (E) view.findViewById(resId);
    }
	
	
	/**
	 * 释放view上的所有图片资源
	 * @param view
	 */
	@SuppressLint("NewApi")
	public static void unbindDrawables(View view) {
		if (null == view) return;
		if (null != view.getBackground()) {
			view.getBackground().setCallback(null);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				view.setBackground(null);
			} else {
				view.setBackgroundDrawable(null);
			}
		}
		if (view instanceof ImageView) {
			ImageView imageView = (ImageView) view;
			Drawable drawable = imageView.getDrawable();
			if (null != drawable) {
				if (drawable instanceof BitmapDrawable) {
					Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
					if (null != bitmap && !bitmap.isRecycled()) {
						bitmap.recycle();
						bitmap = null;
					}
				} else if (drawable instanceof StateListDrawable) {
					Bitmap bitmap;
					Drawable layerDrawable;
					StateListDrawable stateDrawable = (StateListDrawable) drawable;
					ConstantState constantState = stateDrawable.getConstantState();
					if (null != constantState && constantState instanceof DrawableContainerState) {
						layerDrawable = ((DrawableContainerState)constantState).getChild(1);
						if (layerDrawable instanceof LayerDrawable) {
							layerDrawable = ((LayerDrawable)layerDrawable).getDrawable(1);
							if (layerDrawable instanceof BitmapDrawable) {
								bitmap = ((BitmapDrawable) layerDrawable).getBitmap();
								if (null != bitmap) {
									bitmap.recycle();
									bitmap = null;
								}
								layerDrawable = null;
							}
						}
					}
				}
				drawable = null;
				imageView.getDrawable().setCallback(null);
				imageView.setImageDrawable(null);
			}
		}
		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				unbindDrawables(((ViewGroup) view).getChildAt(i));
			}
			if (!(view instanceof AdapterView<?>)) {
				((ViewGroup) view).removeAllViews();
			}
		}
		view = null;
	}
	
	/********************************************检查是不是电视*******************************************/
	
	/**
	 * 检查当前屏幕的物理尺寸
	 * 小于6.4认为是手机,否则是电视
	 * @param ctx
	 * @return true 手机   false 电视
	 */
	private static boolean checkScreenIsPhone(Context ctx) {
		
		WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		//Math.pow(x,y)这个函数是求x的y次方
		double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
		double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
		//Math.sqrt(x)求x平方根
		double scrrenInches = Math.sqrt(x + y);
		return scrrenInches < 6.5;
	}
	
	/**
	 * 检查当前设备的布局尺寸
	 * 如果是 SIZE_LARGE 就认为是大屏幕
	 * @param ctx
	 * @return
	 */
	private static boolean checkScreenLayoutIsPhone(Context ctx) {
		return (ctx.getResources().getConfiguration().screenLayout
				& Configuration.SCREENLAYOUT_LAYOUTDIR_MASK)
				<= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
	
	/**
	 * 检查SIM卡的状态,如果没有检测到,认为是电视
	 * @param ctx
	 * @return
	 */
	private static boolean checkTelephonyIsPhone(Context ctx) {
		TelephonyManager telephony = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
		return telephony.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
	}
	
	/**
	 * 检查当前电源输入状态来判断是电视还是手机
	 * @param ctx
	 * @return
	 */
	private static boolean checkBatteryIsPhone(Context ctx) {
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = ctx.registerReceiver(null, intentFilter);
		//当前电池的状态
		int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		boolean isCharging = status == BatteryManager.BATTERY_STATUS_FULL;
		//当前充电状态
		int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
		boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
		//当前电量一定是满的 并且是AC交流电接入才认为是电视
		return !(isCharging && acCharge);
	}

	/**
	 * 判断是否运行在手机端
	 * @param ctx
	 * @return true 手机  false 电视
	 */
	public static boolean isPhoneRunning(Context ctx) {
		return checkScreenIsPhone(ctx)
				&& checkScreenLayoutIsPhone(ctx)
				&& checkTelephonyIsPhone(ctx)
				&& checkBatteryIsPhone(ctx);
	}
	
	/********************************************检查是不是电视*******************************************/
	
	
	public static void setMaxWidth(View view, int value) {
        setValue(view, METHOD_SET_MAX_WIDTH, value);
    }

    public static void setMaxHeight(View view, int value) {
        setValue(view, METHOD_SET_MAX_HEIGHT, value);
    }
    
    public static void setMinWidth(View view, int value) {
        view.setMinimumWidth(value);
    }

    public static void setMinHeight(View view, int value) {
        view.setMinimumHeight(value);
    }
    
    public static int getMaxWidth(View view) {
        return getValue(view, METHOD_GET_MAX_WIDTH);
    }

    public static int getMaxHeight(View view) {
        return getValue(view, METHOD_GET_MAX_HEIGHT);
    }
    
    @SuppressLint("NewApi")
	public static int getMinWidth(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return view.getMinimumWidth();
        } else {
            return getValue(view, METHOD_GET_MIN_WIDTH);
        }
    }

    @SuppressLint("NewApi")
	public static int getMinHeight(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return view.getMinimumHeight();
        } else {
            return getValue(view, METHOD_GET_MIN_HEIGHT);
        }
    }
	
	private static int getValue(View view, String getterMethodName) {
		int result = 0;
		try {
			Method getValueMethod = view.getClass().getMethod(getterMethodName);
			result = (int) getValueMethod.invoke(view);
		} catch (Exception e) {
			// do nothing
		}
		return result;
	}
	
	private static void setValue(View view, String setterMethodName, int value) {
        try {
            Method setValueMethod = view.getClass().getMethod(setterMethodName, Integer.class);
            setValueMethod.invoke(view, value);
        } catch (Exception e) {
            // do nothing
        }
    }
	
	
	
	public static Rect getLocationInView(View parent, View child) {
		if (child == null || parent == null) {
			throw new IllegalArgumentException("parent and child can not be null.");
		}
		
		View decorView = null;
		Context context = child.getContext();
		if (context instanceof Activity) {
			decorView = ((Activity) context).getWindow().getDecorView();
		}
		
		Rect result = new Rect();
		Rect tmpRect = new Rect();
		
		View tmp = child;
		
		if (child == parent) {
			child.getHitRect(result);
			return result;
		}
		
		while (tmp != decorView && tmp != parent) {
			tmp.getHitRect(tmpRect);
			if (!tmp.getClass().equals(FRAGMENT_CON)) {
				result.left += tmpRect.left;
				result.top += tmpRect.top;
			}
			tmp = (View) tmp.getParent();
			if (tmp == null) {
                throw new IllegalArgumentException("the view is not showing in the window!");
            }
			//added by isanwenyu@163.com fix bug #21 the wrong rect user will received in ViewPager
            if (tmp.getParent() != null && (tmp.getParent() instanceof ViewPager)) {
                tmp = (View) tmp.getParent();
            }
		}
		
		result.right = result.left + child.getMeasuredWidth();
		result.bottom = result.top + child.getMeasuredHeight();
		return result;
	}
	
	
	/**
	 *   动态设置一个View的margin值, 控件必须是在XML中定义的才有效，
	 *   如果是直接new的view可能会报空指针异常
	 * @param view
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public static void setMargins (View view, int left, int top, int right, int bottom) {
		if (null == view) return;
	    if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
	        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
	        p.setMargins(left, top, right, bottom);
	        view.requestLayout();
	    }
	}
	
	
}
