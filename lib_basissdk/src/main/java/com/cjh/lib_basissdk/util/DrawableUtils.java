package com.cjh.lib_basissdk.util;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * 
 */
public final class DrawableUtils {
	
	private DrawableUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}
	
    //------------------------------- Drawable 着色 start --------------------------------------
	
	/**
	 * 可以参考 ：http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2017/0831/8450.html
     * 对目标Drawable 进行着色
     *
     * @param drawable 目标Drawable
     * @param color    着色的颜色值
     * @return 着色处理后的Drawable
     */
	public static Drawable tintDrawable(@NonNull Drawable drawable, @ColorInt int color) {
		// 获取此drawable的共享状态实例
		Drawable wrappedDrawable = getCanTintDrawable(drawable);
		// 对 drawable 进行着色
		DrawableCompat.setTint(wrappedDrawable, color);
		return wrappedDrawable;
	}
	
	/**
     * 对目标Drawable 进行着色
     *
     * @param drawable 目标Drawable
     * @param colors   着色值
     * @return 着色处理后的Drawable
     */
    public static Drawable tintListDrawable(@NonNull Drawable drawable, ColorStateList colors) {
        Drawable wrappedDrawable = getCanTintDrawable(drawable);
        // 对 drawable 进行着色
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }
	
	/**
     * 获取可以进行tint 的Drawable
     * <p>
     * 对原drawable进行重新实例化  newDrawable()
     * 包装  warp()
     * 可变操作 mutate()
     *
     * @param drawable 原始drawable
     * @return 可着色的drawable
     */
    @NonNull
	private static Drawable getCanTintDrawable(@NonNull Drawable drawable) {
    	// 获取此drawable的共享状态实例
		Drawable.ConstantState state = drawable.getConstantState();
		// 对drawable 进行重新实例化、包装、可变操作
		return DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
	}
    
    //------------------------------------- Drawable 着色 end -------------------------------------------
    //---------------------------------- Drawable 圆角矩形 start ----------------------------------------
    
    /**
     * 获得一个指定填充色，边框宽度、颜色的圆角矩形drawable。
     * Android 中 在xml中写的"shape"标签映射对象就是GradientDrawable。
     * 通过设置solidColors 和strokeColors 可实现选择器的效果
     *
     * @param solidColors  填充色
     * @param strokeColors 描边色
     * @param strokeWidth  描边线宽度
     * @param dashWidth    虚线（破折线）的长度（以像素为单位）
     * @param dashGap      虚线（破折线）间距，当dashGap=0dp时，为实线
     * @param radius       圆角半径
     * @param shapeType    形状
     * @return GradientDrawable
     */
	@SuppressLint("NewApi")
	public static Drawable getShapeDrawable(ColorStateList solidColors, ColorStateList strokeColors,
    										int strokeWidth, float dashWidth, float dashGap, float radius, 
    										@IntRange(from = 0, to = 3) int shapeType) {
    	GradientDrawable gradientDrawable = new GradientDrawable();
    	gradientDrawable.setShape(shapeType);
    	gradientDrawable.setCornerRadius(radius);
    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    		gradientDrawable.setColor(solidColors);
            //显示一条虚线，破折线的宽度为dashWith，破折线之间的空隙的宽度为dashGap，当dashGap=0dp时，为实线
    		gradientDrawable.setStroke(strokeWidth, strokeColors, dashWidth, dashGap);
    	} else {
    		gradientDrawable.setColor(solidColors.getDefaultColor());
            //显示一条虚线，破折线的宽度为dashWith，破折线之间的空隙的宽度为dashGap，当dashGap=0dp时，为实线
    		gradientDrawable.setStroke(strokeWidth, strokeColors.getDefaultColor(), dashWidth, dashGap);
    		
    	}
    	return gradientDrawable;
    }
	
	/**
     * 获得一个指定填充色，指定描边色的圆角矩形drawable
     *
     * @param solidColor  填充色
     * @param strokeColor 描边色
     * @param strokeWidth 描边线宽度
     * @param dashWidth   虚线（破折线）宽度
     * @param dashGap     虚线（破折线）间距，当dashGap=0dp时，为实线
     * @param radius      圆角半径
     * @return GradientDrawable
     */
    public static Drawable getShapeDrawable(@ColorInt int solidColor, @ColorInt int strokeColor, int strokeWidth,  
                                            float dashWidth, float dashGap, float radius) {
        return getShapeDrawable(ColorStateList.valueOf(solidColor), ColorStateList.valueOf(strokeColor),
                 strokeWidth, dashWidth, dashGap, radius, GradientDrawable.RECTANGLE);
    }
    
    /**
     * 获得一个指定填充色，指定描边色的圆角矩形drawable
     *
     * @param solidColors  填充色
     * @param strokeColors 描边色
     * @param strokeWidth  描边线宽度
     * @param radius       圆角半径
     * @return GradientDrawable
     */
    public static Drawable getShapeDrawable(ColorStateList solidColors, ColorStateList strokeColors, 
                                            int strokeWidth, float radius) {
        return getShapeDrawable(solidColors, strokeColors, strokeWidth, 0, 0, 
        		radius, GradientDrawable.RECTANGLE);
    }

    /**
     * 获得一个指定填充色，指定描边色的圆角矩形drawable
     *
     * @param solidColor  填充色
     * @param strokeColor 描边色
     * @param strokeWidth 描边线宽度
     * @param radius      圆角半径
     * @return GradientDrawable
     */
    public static Drawable getShapeDrawable(@ColorInt int solidColor, @ColorInt int strokeColor, 
                                            int strokeWidth, float radius) {
        return getShapeDrawable(ColorStateList.valueOf(solidColor), ColorStateList.valueOf(strokeColor), 
        		strokeWidth, radius);
    }
    
    /**
     * 获得一个指定填充颜色，描边颜色的矩形drawable
     *
     * @param solidColor  填充色
     * @param strokeColor 描边色
     * @param strokeWidth 描边线宽度
     * @return GradientDrawable
     */
    public static Drawable getShapeDrawable(@ColorInt int solidColor, @ColorInt int strokeColor, 
                                            int strokeWidth) {
        return getShapeDrawable(solidColor, strokeColor, strokeWidth, 0, 0, 0);
    }
    
    /**
     * 获得一个指定描边颜色的圆角矩形drawable
     *
     * @param strokeColor 描边色
     * @param strokeWidth 描边宽度
     * @param radius      圆角半径
     * @return GradientDrawable
     */
    public static Drawable getStrokeShapeDrawable(@ColorInt int strokeColor, int strokeWidth, float radius) {
        return getShapeDrawable(0, strokeColor, strokeWidth, radius);
    }
    
    /**
     * 获得一个指定描边颜色的矩形drawable
     *
     * @param strokeColor 描边色
     * @param strokeWidth 描边宽度
     * @return GradientDrawable
     */
    public static Drawable getStrokeShapeDrawable(@ColorInt int strokeColor, int strokeWidth) {
        return getShapeDrawable(0, strokeColor, strokeWidth, 0);
    }
    
    /**
     * 获得一个指定填充色的圆角矩形drawable
     *
     * @param solidColor 填充色
     * @param radius     圆角半径
     * @return GradientDrawable
     */
    public static Drawable getSolidShapeDrawable(@ColorInt int solidColor, float radius) {
        return getShapeDrawable(solidColor, 0, 0, radius);
    }

    //--------------------------------- Drawable 圆角矩形 end -------------------------------------------

    //----------------------------------- Drawable 选择器 start-----------------------------------------


    /**
     * 获得一个选择器Drawable.
     * Android 中 在xml中写的"selector"标签映射对象就是StateListDrawable 对象
     *
     * @param defaultDrawable 默认时显示的Drawable
     * @param pressedDrawable 按下时显示的Drawable
     * @return 选择器Drawable
     */
    public static StateListDrawable getSelectorDrawable(Drawable defaultDrawable, Drawable pressedDrawable) {
    	if (defaultDrawable == null) return null;
        if (pressedDrawable == null) 
        	pressedDrawable = defaultDrawable;
        int[][] state = {{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}};
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(state[0], defaultDrawable);
        stateListDrawable.addState(state[1], pressedDrawable);
        return stateListDrawable;
    }
    
    /**
     * 获得一个选择器Drawable.
     * Android 中 在xml中写的"selector"标签映射对象就是StateListDrawable 对象
     *
     * @param defaultColor 默认时显示的颜色
     * @param pressedColor 按下时显示的颜色
     * @return 选择器Drawable
     */
    public static StateListDrawable getSelectorDrawable(int defaultColor, int pressedColor, float radius) {
        Drawable defaultDrawable = getSolidShapeDrawable(defaultColor, radius);
        Drawable pressedDrawable = getSolidShapeDrawable(pressedColor, radius);
        return getSelectorDrawable(defaultDrawable, pressedDrawable);
    }
    
    //------------------------------- Drawable 选择器 end-------------------------------------------
}
