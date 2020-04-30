package com.cjh.lib_basissdk.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.view.View;
import androidx.annotation.DrawableRes;

public class SelectorUtils {
	
	private SelectorUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
	
	/**
     * 通过图片 id 给 view 添加 selector
     * @param context    上下文
     * @param normalId 	    默认图片的 id
     * @param pressedId  点击图片的 id
     * @param selectedId 选择图片的 id
     * @param focusedId  聚焦图片的 id
     * @param view       设置的 view
     */
	public static void addSelectorFromDrawable(Context context, View view, @DrawableRes int normalId,
                                               @DrawableRes int pressedId, @DrawableRes int selectedId, @DrawableRes int focusedId) {
		
		if (null == context || null == view)
			return;
		StateListDrawable stateListDrawable = new StateListDrawable();
		if (pressedId != 0) {
			Drawable pressedDrawable = context.getResources().getDrawable(pressedId);
			stateListDrawable.addState(new int[] {android.R.attr.state_pressed}, pressedDrawable);
		}
		if (selectedId != 0) {
			Drawable selectedDrawable = context.getResources().getDrawable(selectedId);
			stateListDrawable.addState(new int[] {android.R.attr.state_selected}, selectedDrawable);
		}
		if (focusedId != 0) {
			Drawable focusedDrawable = context.getResources().getDrawable(focusedId);
			stateListDrawable.addState(new int[] {android.R.attr.state_focused}, focusedDrawable);
		}
		Drawable normalDrawable = context.getResources().getDrawable(normalId);
		stateListDrawable.addState(new int[] {}, normalDrawable);   //selector前面某一个符合条件了,则后面的都不会执行,所以这个必须放最后,不然它会屏蔽掉其他的
		
		//有时候设置selector会失效,比如LinearLayout,可以通过设置setClickable(true)解决
		if (!view.isClickable())
			view.setClickable(true);
		
		view.setBackground(stateListDrawable);
	}
	
	/**
     * 创建一个按下和正常状态的选择器
     *
     * @param normal  正常状态的Drawable对象
     * @param pressed 按下状态的Drawable对象
     * @return 按下和转产状态的状态选择器
     */
    public static StateListDrawable createNormalAndPressedSelector(Drawable normal, Drawable pressed) {
        LinkedHashMap<int[], Drawable> states = new LinkedHashMap<>();
        states.put(new int[]{android.R.attr.state_pressed}, pressed);
        states.put(new int[]{}, normal);   //selector前面某一个符合条件了,则后面的都不会执行,所以这个必须放最后,不然它会屏蔽掉其他的
        return createSelectorByStates(states);
    }
	
	
	/**
     * 根据map里面创建选择器
     *
     * @param states 封装了状态对应的Drawable对象的集合，状态选择器是有前后顺序的，所以请使用LinkedHashMap
     * @return map中的选择器
     */
    public static StateListDrawable createSelectorByStates(LinkedHashMap<int[], Drawable> states) {
        //如果map里面的状态小于2个，就不能生成选择器
        if (null == states || states.size() < 2) {
            throw new IllegalStateException("You must make 2 state to create seletor!");
        }
        //创建选择器对象
        StateListDrawable stateListDrawable = new StateListDrawable();
        //遍历集合
        Set<Map.Entry<int[], Drawable>> set = states.entrySet();
        for (Map.Entry<int[], Drawable> next : set) {
            int[] state = next.getKey();
            Drawable drawable = next.getValue();
            //给选择器添加对应的状态
            stateListDrawable.addState(state, drawable);
        }
        return stateListDrawable;
    }
	
	
	/**
	 * 从网络获取图片 给view设置 selector
	 * @param context       上下文
	 * @param view          设置的 view
	 * @param normalUrl     默认图片
	 * @param pressedUrl    点击图片
	 * @param selectedUrl   选择图片
	 * @param focusedUrl    聚焦图片
	 */
	public static void addSelectorFromNetwork(final Context context, final View view, final String normalUrl, 
			final String pressedUrl, final String selectedUrl, final String focusedUrl) {
		
		new AsyncTask<Void, Void, Drawable>() {
			@Override
			protected Drawable doInBackground(Void... params) {
				StateListDrawable stateListDrawable = new StateListDrawable();
				Drawable normal = loadImageFromNetworkCompat(context, normalUrl);
				Drawable pressed = loadImageFromNetworkCompat(context, pressedUrl);
				Drawable selected = loadImageFromNetworkCompat(context, selectedUrl);
				Drawable focused = loadImageFromNetworkCompat(context, focusedUrl);
				stateListDrawable.addState(new int[] {android.R.attr.state_pressed}, pressed);
				stateListDrawable.addState(new int[] {android.R.attr.state_selected}, selected);
				stateListDrawable.addState(new int[] {android.R.attr.state_focused}, focused);
				stateListDrawable.addState(new int[] {}, normal); //selector前面某一个符合条件了,则后面的都不会执行,所以这个必须放最后,不然它会屏蔽掉其他的
				return stateListDrawable;
			}
			
			@Override
			protected void onPostExecute(Drawable drawable) {
				super.onPostExecute(drawable);
				view.setBackground(drawable);
			}
		}.execute();
	}
	
	/**
	 * 这个方法已经废弃,有bug, 图片会变小。 详情参看 ：https://blog.csdn.net/xmobile/article/details/8912075
	 * use {@link loadImageFromNetworkCompat} instanceof
	 * @deprecated
	 * @param networkUrl
	 * @return
	 */
	private static Drawable loadImageFromNetwork(String networkUrl) {
		
		Drawable drawable = null;
		try {
			//第二个参数是用来调试用的, 没什么实际意义,可以设为null
			drawable = Drawable.createFromStream(new URL(networkUrl).openStream(), "netUrl.jpg");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return drawable;
	}
	
	/**
	 * 加载网络图片
	 * @param context     上下文
	 * @param networkUrl  图片url
	 * @return            drawable
	 */
	private static Drawable loadImageFromNetworkCompat(Context context, String networkUrl) {
		Drawable drawable = null;
		try {
			//第四个参数是用来调试用的, 没什么实际意义,可以设为null
			drawable = Drawable.createFromResourceStream(context.getResources(), null, 
					new URL(networkUrl).openStream(), "netUrl.jpg", null);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return drawable;
	}

}
