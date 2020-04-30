package com.cjh.lib_basissdk.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.drawerlayout.widget.DrawerLayout;

/**
 * 栏相关工具类 (废弃的方法尽量不要使用,如果必须要用先弄明白在使用)
 */
public final class BarUtils {
	
	///////////////////////////////////////////////////////////////////////////
	// status bar
	///////////////////////////////////////////////////////////////////////////
	
	private static final int DEFAULT_ALPHA = 112;
	private static final String TAG_COLOR = "TAG_COLOR";
	private static final String TAG_ALPHA = "TAG_ALPHA";
	private static final int TAG_OFFSET = -123;
	
	private BarUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
	
	/**
     * 自动取状态栏高度（单位：px）,会自动判断状态栏高度是否为0
     *
     * @return 状态栏高度（单位：px）
     */
	public static int getStatusBarHeight() {
		return getStatusBarHeightDynamic();
	}
	
	
	/**
     * 静态获取状态栏高度（单位：px）, 只是获取状态栏固定的高度,和显示没显示无关
     *
     * @return 状态栏高度（单位：px）
     */
	public static int getStatusBarHeightStatic() {
		Resources resources = Utils.getApp().getResources();
		int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
		return resources.getDimensionPixelSize(resourceId);
	}
	
	
	/** 动态获取通知栏的高度,如果状态栏显示,这为显示的高度,如果状态栏不显示则为0
	 * @return
	 */
	public static int getStatusBarHeightDynamic() {
	     Rect rect = new Rect();
	     Utils.getCurrActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
	     return rect.top;
	}
	
	/**
     * 反射获取状态栏高度（单位：px）, 效率没有 {@link #getStatusBarHeight()}高
     *
     * @return 状态栏高度（单位：px）
     */
	public static int getStatusBarHeightReflect() {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = Utils.getApp().getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}
	
	/**
     * 设置状态栏是否显示
     *
     * @param activity  The activity.
     * @param isVisible True to set status bar visible, false otherwise.
     */
    public static void setStatusBarVisibility(@NonNull final Activity activity,
                                              final boolean isVisible) {
        setStatusBarVisibility(activity.getWindow(), isVisible);
    }
    
    /**
     * 通过添加或者清除{@link WindowManager.LayoutParams.FLAG_FULLSCREEN}
     * 标志来控制状态栏显示与否
     *
     * @param window    The window.
     * @param isVisible True to set status bar visible, false otherwise.
     */
    public static void setStatusBarVisibility(@NonNull final Window window,
                                              final boolean isVisible) {
        if (isVisible) {
        	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        		int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;  
                uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY; 
                uiFlags &= (~View.SYSTEM_UI_FLAG_FULLSCREEN);
                window.getDecorView().setSystemUiVisibility(uiFlags);  
        	} else {
        		window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        	}
        } else {
        	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        		int uiFlags = View.SYSTEM_UI_FLAG_FULLSCREEN;  
                uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;  
                window.getDecorView().setSystemUiVisibility(uiFlags); 
        	} else {
        		window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        	}
        }
    }
    
    /**
     * @deprecated
	 * 切换全屏
	 *  View.SYSTEM_UI_FLAG_FULLSCREEN,   //全屏，状态栏和导航栏不显示  
    	View.SYSTEM_UI_FLAG_HIDE_NAVIGATION, //隐藏导航栏  
    	View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN, //全屏，状态栏会盖在布局上  
    	View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION,   
    	View.SYSTEM_UI_FLAG_LAYOUT_STABLE,  
    	View.SYSTEM_UI_FLAG_LOW_PROFILE,  
    	View.SYSTEM_UI_FLAG_VISIBLE,  //显示状态栏和导航栏  
    	View.SYSTEM_UI_LAYOUT_FLAGS  
	 * @param activity
	 * @param enable
	 */
	public static void setStatusBarVisible2(Activity activity, boolean enable) {
		if (enable) {
			WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
			lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
			activity.getWindow().setAttributes(lp);
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		} else {
			WindowManager.LayoutParams attr = activity.getWindow().getAttributes();
			attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			activity.getWindow().setAttributes(attr);
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
	}
	
	/**
     * 判断状态栏是否显示 ,基本大部分情况下准确
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isStatusBarVisibleCompat(@NonNull final Activity activity) {
        return getStatusBarHeight() != 0;
    }
    
    /**
     * 判断状态栏是否显示 ,大部分情况是正确,但是状态栏隐藏的时候,比如电视盒子本来就是没有状态栏的情况下就不准
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isStatusBarVisible(@NonNull final Activity activity) {
        int flags = activity.getWindow().getAttributes().flags;
        int uiFlags = activity.getWindow().getDecorView().getSystemUiVisibility();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        	return ((uiFlags & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0);
        } else {
        	return ((flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0);
        }
    }
    
    /**
     * 设置状态栏为亮模式, Android23以上才有效果
     *
     * @param activity    The activity.
     * @param isLightMode True to set status bar light mode, false otherwise.
     */
    public static void setStatusBarLightMode(@NonNull final Activity activity,
                                             final boolean isLightMode) {
        setStatusBarLightMode(activity.getWindow(), isLightMode);
    }
    
    /**
     * 设置状态栏为亮模式, Android23以上才有效果
     *
     * @param window      The window.
     * @param isLightMode True to set status bar light mode, false otherwise.
     */
    public static void setStatusBarLightMode(@NonNull final Window window,
                                             final boolean isLightMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (isLightMode) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }
    
    /**
     * 给view的 MarginTop属性增加状态栏高度那么多的偏移
     *
     * @param view view
     */
    public static void addMarginTopEqualStatusBarHeight(@NonNull View view) {
    	if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
    	//通过Tag来判断是否已经增加过高度了
    	Object haveSetOffset = view.getTag(TAG_OFFSET);
    	if (haveSetOffset != null && (Boolean) haveSetOffset) return;
    	ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
    	layoutParams.setMargins(layoutParams.leftMargin, 
    			layoutParams.topMargin + getStatusBarHeight(), 
    			layoutParams.rightMargin, 
    			layoutParams.bottomMargin);
    	//最好加下面两句话,不然动态布局时会失效
    	view.setLayoutParams(layoutParams);
    	view.requestLayout();
    	view.setTag(TAG_OFFSET, true);
    }
    
    /**
     * 给 view的 MarginTop属性减少状态栏高度的偏移
     *
     * @param view view
     */
    public static void subtractMarginTopEqualStatusBarHeight(@NonNull View view) {
    	if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        Object haveSetOffset = view.getTag(TAG_OFFSET);
        if (haveSetOffset == null || !(Boolean) haveSetOffset) return;
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin,
                layoutParams.topMargin - getStatusBarHeight(),
                layoutParams.rightMargin,
                layoutParams.bottomMargin);
        //最好加下面两句话,不然动态布局时会失效
    	view.setLayoutParams(layoutParams);
    	view.requestLayout();
        view.setTag(TAG_OFFSET, false);
    }
    
    /**
     * view是否设置了 状态栏高度的偏移
     *
     * @param view
     */
    public static boolean isMarginTopEqualStatusBarHeight(@NonNull View view) {
    	Object haveSetOffset = view.getTag(TAG_OFFSET);
    	if (haveSetOffset != null && (Boolean) haveSetOffset) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    
    /**
     * 设置状态栏的颜色
     *
     * @param activity The activity.
     * @param color    The status bar's color.
     */
    public static void setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color) {
        setStatusBarColor(activity, color, DEFAULT_ALPHA, false);
    }
    
    
    /**
     * 设置状态栏的颜色
     *
     * @param activity The activity.
     * @param color    The status bar's color.
     * @param alpha    The status bar's alpha which isn't the same as alpha in the color.
     */
    public static void setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color,
                                         @IntRange(from = 0, to = 255) final int alpha) {
        setStatusBarColor(activity, color, alpha, false);
    }
    
    
    /**
     * 设置状态栏的颜色
     *
     * @param activity The activity.
     * @param color    The status bar's color.
     * @param alpha    The status bar's alpha which isn't the same as alpha in the color.
     * @param isDecor  True 添加假的状态栏到DecorView
     *                 false 添加假的状态栏到ContentView.
     */
    public static void setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color,
                                         @IntRange(from = 0, to = 255) final int alpha,
                                         final boolean isDecor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        hideAlphaView(activity);
        transparentStatusBar(activity);
        addStatusBarColor(activity, color, alpha, isDecor);
    }
    
    
    /**
     * 设置状态栏的颜色.
     *
     * @param fakeStatusBar The fake status bar view.
     * @param color         The status bar's color.
     */
    public static void setStatusBarColor(@NonNull final View fakeStatusBar,
                                         @ColorInt final int color) {
        setStatusBarColor(fakeStatusBar, color, DEFAULT_ALPHA);
    }
    
    
    /**
     * 设置状态栏的颜色
     *
     * @param fakeStatusBar The fake status bar view.
     * @param color         The status bar's color.
     * @param alpha         The status bar's alpha which isn't the same as alpha in the color.
     */
    public static void setStatusBarColor(@NonNull final View fakeStatusBar,
                                         @ColorInt final int color,
                                         @IntRange(from = 0, to = 255) final int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        fakeStatusBar.setVisibility(View.VISIBLE);
        transparentStatusBar((Activity) fakeStatusBar.getContext());
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = BarUtils.getStatusBarHeight();
        fakeStatusBar.setBackgroundColor(getStatusBarColor(color, alpha));
    }
    
    /**
     * 设置状态栏的透明度
     *
     * @param activity The activity.
     */
    public static void setStatusBarAlpha(@NonNull final Activity activity) {
        setStatusBarAlpha(activity, DEFAULT_ALPHA, false);
    }
    
    
    /**
     * 设置状态栏的透明度
     *
     * @param activity The activity.
     * @param alpha    The status bar's alpha.
     */
    public static void setStatusBarAlpha(@NonNull final Activity activity,
                                         @IntRange(from = 0, to = 255) final int alpha) {
        setStatusBarAlpha(activity, alpha, false);
    }
    
    
    /**
     * 设置状态栏的透明度
     *
     * @param activity The activity.
     * @param alpha    The status bar's alpha.
     * @param isDecor  True to add fake status bar in DecorView,
     *                 false to add fake status bar in ContentView.
     */
    public static void setStatusBarAlpha(@NonNull final Activity activity,
                                         @IntRange(from = 0, to = 255) final int alpha,
                                         final boolean isDecor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        hideColorView(activity);
        transparentStatusBar(activity);
        addStatusBarAlpha(activity, alpha, isDecor);
    }
    
    
    /**
     * 设置状态栏透明度
     *
     * @param fakeStatusBar The fake status bar view.
     */
    public static void setStatusBarAlpha(@NonNull final View fakeStatusBar) {
        setStatusBarAlpha(fakeStatusBar, DEFAULT_ALPHA);
    }
    
    
    /**
     * 设置状态栏透明度
     *
     * @param fakeStatusBar 假的状态栏
     * @param alpha         The status bar's alpha.
     */
    public static void setStatusBarAlpha(@NonNull final View fakeStatusBar,
                                         @IntRange(from = 0, to = 255) final int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        fakeStatusBar.setVisibility(View.VISIBLE);
        transparentStatusBar((Activity) fakeStatusBar.getContext());
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = BarUtils.getStatusBarHeight();
        fakeStatusBar.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
    }
    
    
    
    /**
     * 给DrawerLayout设置状态栏颜色
     * <p>DrawLayout must add {@code android:fitsSystemWindows="true"}</p>
     *
     * @param activity      The activity.
     * @param drawer        The DrawLayout.
     * @param fakeStatusBar The fake status bar view.
     * @param color         The status bar's color.
     * @param isTop         True to set DrawerLayout at the top layer, false otherwise.
     */
    public static void setStatusBarColor4Drawer(@NonNull final Activity activity,
                                                @NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                @ColorInt final int color,
                                                final boolean isTop) {
        setStatusBarColor4Drawer(activity, drawer, fakeStatusBar, color, DEFAULT_ALPHA, isTop);
    }

    /**
     * 给DrawerLayout设置状态栏颜色
     * <p>DrawLayout must add {@code android:fitsSystemWindows="true"}</p>
     *
     * @param activity      The activity.
     * @param drawer        The DrawLayout.
     * @param fakeStatusBar The fake status bar view.
     * @param color         The status bar's color.
     * @param alpha         The status bar's alpha which isn't the same as alpha in the color.
     * @param isTop         True to set DrawerLayout at the top layer, false otherwise.
     */
    public static void setStatusBarColor4Drawer(@NonNull final Activity activity,
                                                @NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                @ColorInt final int color,
                                                @IntRange(from = 0, to = 255) final int alpha,
                                                final boolean isTop) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        drawer.setFitsSystemWindows(false);
        transparentStatusBar(activity);
        setStatusBarColor(fakeStatusBar, color, isTop ? alpha : 0);
        for (int i = 0, len = drawer.getChildCount(); i < len; i++) {
            drawer.getChildAt(i).setFitsSystemWindows(false);
        }
        if (isTop) {
            hideAlphaView(activity);
        } else {
            addStatusBarAlpha(activity, alpha, false);
        }
    }
    
    /**
     * 给DrawerLayout设置状态栏透明
     * <p>DrawLayout must add {@code android:fitsSystemWindows="true"}</p>
     *
     * @param activity      The activity.
     * @param drawer        drawerLayout
     * @param fakeStatusBar The fake status bar view.
     * @param isTop         True to set DrawerLayout at the top layer, false otherwise.
     */
    public static void setStatusBarAlpha4Drawer(@NonNull final Activity activity,
                                                @NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                final boolean isTop) {
        setStatusBarAlpha4Drawer(activity, drawer, fakeStatusBar, DEFAULT_ALPHA, isTop);
    }
    
    
    /**
     * 给DrawerLayout设置状态栏透明.
     * <p>DrawLayout must add {@code android:fitsSystemWindows="true"}</p>
     *
     * @param activity      The activity.
     * @param drawer        drawerLayout
     * @param fakeStatusBar The fake status bar view.
     * @param alpha         The status bar's alpha.
     * @param isTop         True to set DrawerLayout at the top layer, false otherwise.
     */
    public static void setStatusBarAlpha4Drawer(@NonNull final Activity activity,
                                                @NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar,
                                                @IntRange(from = 0, to = 255) final int alpha,
                                                final boolean isTop) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        drawer.setFitsSystemWindows(false);
        transparentStatusBar(activity);
        setStatusBarAlpha(fakeStatusBar, isTop ? alpha : 0);
        for (int i = 0, len = drawer.getChildCount(); i < len; i++) {
            drawer.getChildAt(i).setFitsSystemWindows(false);
        }
        if (isTop) {
            hideAlphaView(activity);
        } else {
            addStatusBarAlpha(activity, alpha, false);
        }
    }
    
	/**
	 * 添加颜色状态栏
	 * @param activity
	 * @param color
	 * @param alpha
	 * @param isDecor
	 */
	private static void addStatusBarColor(final Activity activity, final int color, 
			final int alpha, boolean isDecor) {
		ViewGroup parent = isDecor ? (ViewGroup) activity.getWindow().getDecorView() : 
			(ViewGroup) activity.findViewById(android.R.id.content);
		View fakeStatusBarView = parent.findViewWithTag(TAG_COLOR);
		if (fakeStatusBarView != null) {
			if (fakeStatusBarView.getVisibility() == View.GONE) {
				fakeStatusBarView.setVisibility(View.VISIBLE);
			}
			fakeStatusBarView.setBackgroundColor(getStatusBarColor(color, alpha));
		} else {
			//因为无论是DecorView还是ContentView都是FrameLayout,如果默认添加到左上角
			parent.addView(createColorStatusBarView(parent.getContext(), color, alpha));
		}
	}
	
	/**
	 * 添加透明状态栏
	 * @param activity
	 * @param alpha
	 * @param isDecor
	 */
	private static void addStatusBarAlpha(final Activity activity, final int alpha, boolean isDecor) {
	    ViewGroup parent = isDecor ? (ViewGroup) activity.getWindow().getDecorView() : 
	    	(ViewGroup) activity.findViewById(android.R.id.content);
	    View fakeStatusBarView = parent.findViewWithTag(TAG_ALPHA);
	    if (fakeStatusBarView != null) {
	    	if (fakeStatusBarView.getVisibility() == View.GONE) {
	    		fakeStatusBarView.setVisibility(View.VISIBLE);
	    	}
	    	fakeStatusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
	    } else {
	    	parent.addView(createAlphaStatusBarView(parent.getContext(), alpha));
	    }
	}
	
	/**
	 * 隐藏DecorView下的颜色视图
	 * @param activity
	 */
	private static void hideColorView(final Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_COLOR);
        if (fakeStatusBarView == null) return;
        fakeStatusBarView.setVisibility(View.GONE);
    }

    /**
     * 隐藏DecorView下的透明视图
     * @param activity
     */
    private static void hideAlphaView(final Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_ALPHA);
        if (fakeStatusBarView == null) return;
        fakeStatusBarView.setVisibility(View.GONE);
    }
    
    /**
     * 重新计算状态栏的颜色值
     * @param color
     * @param alpha
     * @return
     */
    private static int getStatusBarColor(final int color, final int alpha) {
        if (alpha == 0) return color;
        float a = 1 - alpha / 255f;
        int red = (color >> 16) & 0xff;
        int green = (color >> 8) & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return Color.argb(255, red, green, blue);
    }
    
    /**
     * 绘制一个和状态栏一样高的颜色矩形视图
     */
    private static View createColorStatusBarView(final Context context, final int color, final int alpha) {
    	View statusBarView = new View(context);
    	statusBarView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
    	statusBarView.setBackgroundColor(getStatusBarColor(color, alpha));
    	statusBarView.setTag(TAG_COLOR);
    	return statusBarView;
    }
    
    /**
     * 绘制一个和状态栏一样高的黑色透明度矩形
     */
    private static View createAlphaStatusBarView(final Context context, final int alpha) {
    	View statusBarView = new View(context);
    	statusBarView.setLayoutParams(new LinearLayout.LayoutParams(
    			ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
    	statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        statusBarView.setTag(TAG_ALPHA);
        return statusBarView;
    }
    
    /**
     * 使状态栏透明,才能显示我们添加的view
     * @param activity
     */
    @SuppressLint("NewApi")
	private static void transparentStatusBar(final Activity activity) {
    	if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
    	Window window = activity.getWindow();
    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    		int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN;
    		window.getDecorView().setSystemUiVisibility(option);
    		window.setStatusBarColor(Color.TRANSPARENT);
    	} else {
    		window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    	}
    }
    
    
    
    
    
    ///////////////////////////////////////////////////////////////////////////
    // action bar
    ///////////////////////////////////////////////////////////////////////////
    
    /**
     * 获取 ActionBar 高度
     *
     * @param application application
     * @return ActionBar 高度
     */
    public static int getActionBarHeight(@NonNull final Application application) {
    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
    		TypedValue tv = new TypedValue();
        	if (application.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        		return TypedValue.complexToDimensionPixelSize(tv.data, application.getResources().getDisplayMetrics());
        	}
    	}
    	return 0;
    }
    
    /**
     * 获取 ActionBar 高度
     *
     * @return ActionBar 高度
     */
    public static int getActionBarHeight() {
    	return getActionBarHeight(Utils.getApp());
    }
    
    
    ///////////////////////////////////////////////////////////////////////////
    // notification bar
    ///////////////////////////////////////////////////////////////////////////
    
    
    /**
     * 控制通知Panel显示隐藏
     * <p>Must hold
     * {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR" />}</p>
     *
     * @param isVisible True to set notification bar visible, false otherwise.
     */
    @RequiresPermission(permission.EXPAND_STATUS_BAR)
    public static void setNotificationBarVisibility(final boolean isVisible) {
        String methodName;
        if (isVisible) {
            methodName = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) 
            		? "expand" : "expandNotificationsPanel";
        } else {
            methodName = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) 
            		? "collapse" : "collapsePanels";
        }
        invokePanels(methodName);
    }
    
    /**
     * 显示通知栏
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>}</p>
     *
     * @param context        上下文
     * @param isSettingPanel {@code true}: 打开设置Panel<br>{@code false}: 打开通知Panel
     */
    public static void showNotificationBar(@NonNull final Context context, final boolean isSettingPanel) {
        String methodName = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) ? "expand"
                : (isSettingPanel ? "expandSettingsPanel" : "expandNotificationsPanel");
        invokePanels(context, methodName);
    }
    
    /**
     * 隐藏通知栏
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>}</p>
     *
     * @param context 上下文
     */
    public static void hideNotificationBar(@NonNull final Context context) {
        String methodName = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) 
        		? "collapse" : "collapsePanels";
        invokePanels(context, methodName);
    }
    
    /**
     * 反射唤醒通知栏
     *
     * @param context    上下文
     * @param methodName 方法名
     */
    private static void invokePanels(@NonNull final Context context, final String methodName) {
    	try {
    		@SuppressLint("WrongConstant")
    		Object service = context.getSystemService("statusbar");
    		@SuppressLint("PrivateApi")
			Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
			Method expand = statusBarManager.getMethod(methodName);
			expand.invoke(service);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 反射唤醒通知栏
     *
     * @param methodName 方法名
     */
    private static void invokePanels(final String methodName) {
        invokePanels(Utils.getApp(), methodName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // navigation bar
    ///////////////////////////////////////////////////////////////////////////
    
    /**
     * 获取导航栏高度
     * <p>0 代表不存在</p>
     *
     * @return 导航栏高度
     */
    public static int getNavBarHeight() {
    	Resources res = Utils.getApp().getResources();
    	boolean isPortrait = res.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    	String name = "navigation_bar_height_landscape";
    	if (isPortrait)
    		name = "navigation_bar_height";
    	int resourceId = res.getIdentifier(name, "dimen", "android");
    	if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }
    
    /**
     * 设置是否显示导航栏
     *
     * @param activity  The activity.
     * @param isVisible True to set notification bar visible, false otherwise.
     */
    public static void setNavBarVisibility(@NonNull final Activity activity, boolean isVisible) {
        setNavBarVisibility(activity.getWindow(), isVisible);
    }
    
    
    /**
     * 设置是否显示导航栏
     *
     * @param window    The window.
     * @param isVisible True to set notification bar visible, false otherwise.
     */
    public static void setNavBarVisibility(@NonNull final Window window, boolean isVisible) {
    	int visibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    			| View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
    	View decorView = window.getDecorView();
        if (isVisible) {
            //window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                visibility &= (~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }  
        } else {
            //window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                visibility |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
        }
        decorView.setSystemUiVisibility(visibility);
    }
    
    /**
     * 设置导航栏沉浸
     *
     * @param activity The activity.
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    public static void setNavBarImmersive(@NonNull final Activity activity) {
        setNavBarImmersive(activity.getWindow());
    }
    
    /**
     * 设置导航栏沉浸
     *
     * @param window The window.
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    public static void setNavBarImmersive(@NonNull final Window window) {
        View decorView = window.getDecorView();
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
    
    /**
     * 设置导航栏颜色
     *
     * @param activity The activity.
     * @param color    The navigation bar's color.
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setNavBarColor(@NonNull final Activity activity, @ColorInt final int color) {
        setNavBarColor(activity.getWindow(), color);
    }
    
    /**
     * 设置导航栏颜色
     *
     * @param window The window.
     * @param color  The navigation bar's color.
     */
    @SuppressLint("NewApi")
	@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setNavBarColor(@NonNull final Window window, @ColorInt final int color) {
        window.setNavigationBarColor(color);
    }
    
    /**
     * 获取导航栏颜色
     *
     * @param activity The activity.
     * @return the color of navigation bar
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static int getNavBarColor(@NonNull final Activity activity) {
        return getNavBarColor(activity.getWindow());
    }
    
    /**
     * 获取导航栏颜色
     *
     * @param window The window.
     * @return the color of navigation bar
     */
    @SuppressLint("NewApi")
	@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static int getNavBarColor(@NonNull final Window window) {
        return window.getNavigationBarColor();
    }
    
    /**
     * 导航栏是否显示
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isNavBarVisible(@NonNull final Activity activity) {
        return isNavBarVisible(activity.getWindow());
    }
    
    /**
     * 导航栏是否显示
     *
     * @param window The window.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isNavBarVisible(@NonNull final Window window) {
        /*boolean isNoLimits = (window.getAttributes().flags
                & WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) != 0;
        if (isNoLimits) return false;*/
        View decorView = window.getDecorView();
        int visibility = decorView.getSystemUiVisibility();
        return (visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
    }
    
    /**
     * 隐藏导航栏
     *
     * @param activity activity
     */
    public static void hideNavBar(@NonNull final Activity activity) {
    	if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) return;
    	if (getNavBarHeight() > 0) {
    		View decorView = activity.getWindow().getDecorView();
    		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    		decorView.setSystemUiVisibility(uiOptions);
    	}
    }
    
    /**
     * 隐藏导航栏
     * @deprecated
     * @param activity
     */
    public static void hideNavBar2(@NonNull final Activity activity) {
    	int flags;
    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    		flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    				| View.SYSTEM_UI_FLAG_IMMERSIVE
    				| View.SYSTEM_UI_FLAG_FULLSCREEN;
    	} else {
    		flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    	}
    	activity.getWindow().getDecorView().setSystemUiVisibility(flags);
    }
    
    /**
     * 显示导航栏
     * @param activity
     */
    public static void showNavBar(@NonNull final Activity activity) {  
        View decorView = activity.getWindow().getDecorView();  
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;  
        decorView.setSystemUiVisibility(uiOptions);  
    }
    
    /**
	 *  安卓系统允许修改系统的属性来控制navigation bar的显示和隐藏，此方法用来判断是否有修改过相关属性。
	 *	(修改系统文件，在build.prop最后加入qemu.hw.mainkeys=1即可隐藏navigation bar), 相关属性模拟器中有使用。
	 * @return  当返回值等于"1"表示隐藏navigation bar，等于"0"表示显示navigation bar。
	 */
	private static String getNavBarOverride() {
		String isNavBarOverride = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			try {
				Class<?> clazz = Class.forName("android.os.SystemProperties");
				Method method = clazz.getDeclaredMethod("get", String.class);
				method.setAccessible(true);
				isNavBarOverride = (String) method.invoke(null, "qemu.hw.mainkeys");
			} catch (Throwable e) {
				isNavBarOverride = null;
			}
		}
		return isNavBarOverride;
	}
	
	/**
	 * 判断是否存在navigation bar, 不受隐藏和显示导航栏影响,只是判断其存不存在, 即使是隐藏只要是存在都是返回true
	 * @return
	 */
	public static boolean hasNavBar() {
		Resources resources = Utils.getApp().getResources();
		int resourceId = resources.getIdentifier("config_showNavigationBar", "bool", "android");
		if (resourceId != 0) {
			boolean hasNav = resources.getBoolean(resourceId);
			if (!hasNav) {
				if ("0".equals(getNavBarOverride())) {
					hasNav = true;
				}
			}
			return hasNav;
		} else {
			//可通过此方法来查看设备是否存在物理按键(menu,back,home键)
			return ViewConfiguration.get(Utils.getApp()).hasPermanentMenuKey();
		}
	}
	
	/**
     * @deprecated 好像有问题,看不懂,有空再研究
     * //通过此方法获取navigation bar的宽度, 获取navigation bar虚拟按键栏的宽度（当navigation bar虚拟按键栏垂直显示在右侧时使用）
     * @return
     */
    public static int getNavigationBarWidth() {
        Resources res = Utils.getApp().getResources();
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (hasNavBar())  {
            	int resourceId = res.getIdentifier("navigation_bar_width", "dimen", "android");
                if (resourceId > 0) {
                    result = res.getDimensionPixelSize(resourceId);
                }
                return result;
            }
        }
        return result;
    }
    
    
    ///////////////////////////////////////////////////////////////////////////
    // window
    ///////////////////////////////////////////////////////////////////////////
    
    
    /**
     * 获取屏幕宽度
     * @return
     */
    public static int getWindowWidth(){
		// 获取屏幕宽度
		WindowManager wm = (WindowManager) (Utils.getApp().getSystemService(Context.WINDOW_SERVICE));
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		int mScreenWidth = dm.widthPixels;
		return mScreenWidth;
	}
    
    /**
     * 获取屏幕高度, 包括状态栏, 标题栏和内容区, 但是不包括底部导航栏(如果有的话)
     * @return
     */
    public static int getWindowHeigh(){
		// 获取屏幕高度
		WindowManager wm = (WindowManager) (Utils.getApp().getSystemService(Context.WINDOW_SERVICE));
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		int mScreenHeigh = dm.heightPixels;
		return mScreenHeigh;
	}
    
    /**
     * 实际内容编辑区为  getWindowHeigh() - getStatusBarHeight() - getActionBarHeight()
     * @return
     */
    public static int getRealContentHeight() {
    	return getWindowHeigh() - getStatusBarHeight() - getActionBarHeight();
    }
    
    /**
     * 实际屏幕总高度为 getWindowHeigh() + getNavBarHeight()
     * @return
     */
    public static int getRealHeight() {
    	return getWindowHeigh() + getNavBarHeight();
    }
    
    /**
     * 屏幕真实高度,包括导航栏
     * @return
     */
    @SuppressLint("NewApi")
	public static int getRealHeightSystem() {
    	WindowManager wm = (WindowManager) (Utils.getApp().getSystemService(Context.WINDOW_SERVICE));
    	Display display = wm.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			display.getRealMetrics(displayMetrics);
			return displayMetrics.heightPixels;
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH 
				&& Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) { 
			try {
				return (Integer) Display.class.getMethod("getRawHeight").invoke(display);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		} else {
			Class<?> clazz;
			try {
				clazz = Class.forName("android.view.Display");
				Method method = clazz.getMethod("getRealMetrics", DisplayMetrics.class);
				method.invoke(display, displayMetrics);
				return displayMetrics.heightPixels;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return 0;
    }
    
    /**
     * 屏幕真实高度,包括导航栏
     * @deprecated use {@link #getRealHeightSystem()} instead
     * @return
     */
    public static int getRealHeightReflect() {
    	WindowManager wm = (WindowManager) (Utils.getApp().getSystemService(Context.WINDOW_SERVICE));
    	Display display = wm.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		Class<?> clazz;
		try {
			clazz = Class.forName("android.view.Display");
			Method method = clazz.getMethod("getRealMetrics", DisplayMetrics.class);
			method.invoke(display, displayMetrics);
			return displayMetrics.heightPixels;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
    }
    
    @SuppressLint("NewApi")
	public static float getSmallestWidthDp(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        float widthDp;
        float heightDp;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //API17 之后使用，获取的像素宽高包含虚拟键所占空间，在API17 之前通过反射获取，
            //获取的屏幕高度包含status bar和navigation bar
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            widthDp = metrics.widthPixels / metrics.density;
            heightDp = metrics.heightPixels / metrics.density;
        } else {
            //获取的屏幕高度包含status bar,但不包含navigation bar
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            widthDp = metrics.widthPixels / metrics.density;
            heightDp = (metrics.heightPixels + getNavBarHeight()) / metrics.density;
        }
        return Math.min(widthDp, heightDp);
    }
    
    /**
     * 
     * 隐藏状态栏和标题栏,有待开发
     * @deprecated
     * @param activity
     * @param show
     */
    private static void setStatusAndActionBarVisible(Activity activity, boolean show) {  
        if (show) {  
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;  
            uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;  
            uiFlags &= (~View.SYSTEM_UI_FLAG_FULLSCREEN);
            activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);  
        } else {  
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE  
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;  
            uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;  
            activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);  
        }  
    }  
    
    /**
     * 
     * SYSTEM_UI_FLAG_VISIBLE——显示状态栏和导航栏

		SYSTEM_UI_FLAG_LOW_PROFILE——此模式下，状态栏的图标可能是暗的

		SYSTEM_UI_FLAG_HIDE_NAVIGATION——隐藏导航栏

		SYSTEM_UI_FLAG_FULLSCREEN——隐藏状态栏

		SYSTEM_UI_FLAG_LAYOUT_STABLE——稳定布局,控件的位置不会变化，要搭配android:fitsSystemWindows="true"使用才有效果

		SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION——导航栏浮在布局上

		SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN——状态栏浮在布局上。

		SYSTEM_UI_FLAG_IMMERSIVE——沉浸式：半透明的状态栏和导航栏

		SYSTEM_UI_FLAG_IMMERSIVE_STICKY——粘性沉浸式
     * @param activity
     * @param show
     */
    private static void setStatusAndActionAndNavigationBarVisible(Activity activity, boolean show) {  
        if (show) {  
            int uiFlags = View.SYSTEM_UI_FLAG_VISIBLE;  
            uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;  
            uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;  
            activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);  
        } else {  
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE  
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION  
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;  
            uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;  
            activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);  
        }  
    }  
    
    
    /**
     * 切换全屏模式
     * @param activity
     * @param fullScreen
     */
    public static void switchFullScreen(Activity activity, boolean fullScreen) {
    	setStatusAndActionAndNavigationBarVisible(activity, fullScreen);
    }
    
    /**
     * 是否全屏
     * @param activity
     * @return
     */
    public static boolean isFullScreen(@NonNull Activity activity) {
    	int flags = activity.getWindow().getAttributes().flags;
        int uiFlags = activity.getWindow().getDecorView().getSystemUiVisibility();
        boolean isShowStatusBar, isShowNavBar;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        	isShowStatusBar = ((uiFlags & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0);
        } else {
        	isShowStatusBar = ((flags&WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0);
        }
        isShowNavBar = ((uiFlags & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0);
		return (!isShowStatusBar) && (!isShowNavBar);
    }
   
}
