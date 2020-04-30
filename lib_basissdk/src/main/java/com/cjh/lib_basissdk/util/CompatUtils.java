package com.cjh.lib_basissdk.util;

import java.io.File;
import java.lang.reflect.Method;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.StatFs;
import android.view.View;

import androidx.core.content.ContextCompat;

/**
 * 版本适配工具类 
 */
public final class CompatUtils {
	
	private CompatUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}
	
	/**
	 * 谷歌推荐使用
	 */
	public static Drawable getDrawableCompat(Context context, int id) {
		return ContextCompat.getDrawable(context, id);
	}
	
	/**
	 * 谷歌推荐使用
	 */
	public static int getColorCompat(Context context, int id) {
		return ContextCompat.getColor(context, id);
	}
	
	@SuppressLint("NewApi")
	public static void setGradientDrawableColor(GradientDrawable gradientDrawable, ColorStateList solidColors) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			gradientDrawable.setColor(solidColors);
		} else {
			gradientDrawable.setColor(solidColors.getDefaultColor());
	        
		}
	}
	
	@SuppressLint("NewApi")
	public static void setGradientDrawableStrokeColor(GradientDrawable gradientDrawable, ColorStateList strokeColors,
											   int strokeWidth, float dashWidth, float dashGap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			//显示一条虚线，破折线的宽度为dashWith，破折线之间的空隙的宽度为dashGap，当dashGap=0dp时，为实线
    		gradientDrawable.setStroke(strokeWidth, strokeColors, dashWidth, dashGap);
		} else {
            //显示一条虚线，破折线的宽度为dashWith，破折线之间的空隙的宽度为dashGap，当dashGap=0dp时，为实线
    		gradientDrawable.setStroke(strokeWidth, strokeColors.getDefaultColor(), dashWidth, dashGap);
	    }
	}
	
	
	@SuppressLint("NewApi")
	public static int getMinWidth(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return view.getMinimumWidth();
        } else {
            return getValue(view, "getMinWidth");
        }
    }

    @SuppressLint("NewApi")
	public static int getMinHeight(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return view.getMinimumHeight();
        } else {
            return getValue(view, "getMinHeight");
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
    
    
    /**
     * Returns the total size in bytes of the partition containing this path.
     * Returns 0 if this path does not exist.
     * 
     * @param file
     * @return -1 means path is null, 0 means path is not exist.
     */
    @SuppressWarnings("deprecation")
	public static long getTotalSpace(File file) {
    	if (file == null) {
            return -1;
        }
    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
    		return file.getTotalSpace();
    	} else {
    		if (!file.exists()) {
    			return 0;
    		} else {
    			final StatFs statFs = new StatFs(file.getPath());
    			return (long) statFs.getBlockSize() * (long) statFs.getBlockCount();
    		}
    	}
    }

}
