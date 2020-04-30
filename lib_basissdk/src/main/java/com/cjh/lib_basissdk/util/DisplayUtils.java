package com.cjh.lib_basissdk.util;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * 屏幕密度工具类
 */
public class DisplayUtils {
	
	private DisplayUtils() {  
        /* cannot be instantiated */
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
	
	/**
     * dp转px
     * 注意 applyDimension主要是把其他单位转换为px, 所以要转化为px时才用
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
    
    /**
     * sp转px
     * 注意 applyDimension主要是把其他单位转换为px, 所以要转化为px时才用
     * @param context
     * @param spVal
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     * 注意 applyDimension主要是把其他单位转换为px, 所以要转化为px时才用
     * 这里不是转化为px, 所以不能用applyDimension
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     * 注意 applyDimension主要是把其他单位转换为px, 所以要转化为px时才用
     * 这里不是转化为px, 所以不能用applyDimension
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }
    
    
    //*******************************************************************
    
    
    /**
     * 将dp转换成px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dipTopx(Context context,float dpValue){
        final float scale = context.getResources ().getDisplayMetrics ().density;
		//四舍五入的时候保持正确
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将像素转换成dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int pxTodip(Context context,float pxValue){
        final float scale = context.getResources ().getDisplayMetrics ().density;
		//四舍五入的时候保持正确
        return (int) (pxValue / scale + 0.5f);
    }
    
    
    /**
     * convert px to its equivalent sp 
     * 
     * 将px转换为sp
     */
    public static int pxTosp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * convert sp to its equivalent px
     * 
     * 将sp转换为px
     */
    public static int spTopx(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    
    
    public static int dip2px(float density, float dpValue) {
        return (int) (dpValue * density + 0.5f);
    }

    public static int px2dip(float density, float pxValue) {
        return (int) (pxValue / density + 0.5f);
    }
    
    
    
    /**
	 * 将px值转换为dip或dp值，保证尺寸大小不变（无精度损失）
	 */
	public static float px2dipByFloat(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (pxValue / scale);
	}
	
	
	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变（无精度损失），类似Context.getDimension方法
	 */
	public static float dip2pxByFloat(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (dipValue * scale);
	}
    
	
	/**
	 * 根据之前适配好的机器的宽度和高度来计算要适配现在机器所需要的dpi
	 * @param layoutWidthDp 之前适配好的机器的宽度
	 * @param layoutHeightDp 之前适配好的机器的高度
	 * @param screenWidthPixels 要适配的机器的宽度
	 * @param screenHeightPixels 要适配的机器的高度
	 * @return
	 */
	public static int computeDensityDpi(int layoutWidthDp, int layoutHeightDp,
			int screenWidthPixels, int screenHeightPixels) {
		return (int) (160 * Math.min(screenWidthPixels / (float) layoutWidthDp, 
				screenHeightPixels / (float) layoutHeightDp));
	}
	
	private static void getDisplayRawDimensions(Display display, Point outSize) {
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH 
				&& Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
			try {
				outSize.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
				outSize.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
				return;
			} catch (Exception e) {
			}
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			try {
				Display.class.getMethod("getRealSize", Point.class).invoke(display, outSize);
				return;
			} catch (Exception e) {
			}
		}
		display.getSize(outSize);
	}
	
	/**
	 * 
	 * 计算最佳缩放指布局尺寸的屏幕dpi
	 * @param context 上下文
	 * @param layoutWidthDp 之前适配好的机器的宽度
	 * @param layoutHeightDp 之前适配好的机器的高度
	 * @return dpi
	 */
	public static int computeCompatiableDensityDpi(Context context, int layoutWidthDp, int layoutHeightDp) {
		final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		final Display display = windowManager.getDefaultDisplay();
		final Point size = new Point();
		display.getSize(size);
		
		final int widthPixels = size.x;
		final int heightPixels = size.y;
		
		final boolean adjustOrientation = (layoutWidthDp < layoutHeightDp) 
				!= (widthPixels < heightPixels);
		
		if (!adjustOrientation) {
			return computeDensityDpi(layoutWidthDp, layoutHeightDp, widthPixels, heightPixels);
		}
		
		getDisplayRawDimensions(display, size);
		
		final int rawWidthPixels = size.x;
		final int rawHeightPixels = size.y;
		final int deltaX = rawWidthPixels - widthPixels;
		final int deltaY = rawHeightPixels - heightPixels;
		
		// assume navagation's height is fixed
		return computeDensityDpi(layoutWidthDp, layoutHeightDp, 
				rawHeightPixels - deltaX, rawWidthPixels - deltaY);
		
	}
	
	
	public static DisplayMetrics getCompatibleDisplayMetrics(Context context, 
			int layoutWidthDp, int layoutHeightDp) {
		final DisplayMetrics displayMetrics = new DisplayMetrics();
		final int densityDpi = DisplayUtils.computeCompatiableDensityDpi(
				context, layoutWidthDp, layoutHeightDp);
		displayMetrics.density = densityDpi / 160.0f;
		displayMetrics.densityDpi = densityDpi;
		displayMetrics.scaledDensity = displayMetrics.density;
		return displayMetrics;
	}
	
	public static DisplayMetrics getDisplayMetricsForDensityDpi(Context context, int densityDpi) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		displayMetrics.setTo(context.getResources().getDisplayMetrics());
		displayMetrics.density = (float)densityDpi / 160.0f;
		displayMetrics.densityDpi = densityDpi;
		displayMetrics.scaledDensity = displayMetrics.density;
		return displayMetrics;		
	}
}
