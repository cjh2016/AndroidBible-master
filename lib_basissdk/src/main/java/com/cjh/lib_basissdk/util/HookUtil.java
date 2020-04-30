package com.cjh.lib_basissdk.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Toast;

public class HookUtil {
	
	private HookUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
	
	public static void hookClickListener(View view) {
		try {
			Class<?> viewClazz = Class.forName("android.view.View");
			Method getListenerInfoMethod = viewClazz.getDeclaredMethod("getListenerInfo");
			getListenerInfoMethod.setAccessible(true);
			Object listenerInfo = getListenerInfoMethod.invoke(view);
			Class<?> listenerInfoClass = Class.forName("android.view.View$ListenerInfo");
			Field keyListener = listenerInfoClass.getDeclaredField("mOnClickListener");
			keyListener.setAccessible(true);
			keyListener.set(listenerInfo, new HookClickListener((OnClickListener)keyListener.get(listenerInfo)));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void hookKeyListener(View view) {
		try {
			Class<?> viewClazz = Class.forName("android.view.View");
			Method getListenerInfoMethod = viewClazz.getDeclaredMethod("getListenerInfo");
			getListenerInfoMethod.setAccessible(true);
			Object listenerInfo = getListenerInfoMethod.invoke(view);
			Class<?> listenerInfoClass = Class.forName("android.view.View$ListenerInfo");
			Field keyListener = listenerInfoClass.getDeclaredField("mOnKeyListener");
			keyListener.setAccessible(true);
			keyListener.set(listenerInfo, new HookKeyListener((OnKeyListener)keyListener.get(listenerInfo)));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	
	static class HookKeyListener implements OnKeyListener {
		
		protected OnKeyListener mKeyListener;

		public HookKeyListener(OnKeyListener originalKeyListener) {
			mKeyListener = originalKeyListener;
		}
		
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			Toast.makeText(v.getContext(), "key", Toast.LENGTH_SHORT).show();
			return mKeyListener.onKey(v, keyCode, event);
		}

	}
	
	static class HookClickListener implements OnClickListener {
		
		protected OnClickListener mOnClickListener;

		public HookClickListener(OnClickListener originalClickListener) {
			mOnClickListener = originalClickListener;
		}
		
		@Override
		public void onClick(View v) {
			Toast.makeText(v.getContext(), "click", Toast.LENGTH_SHORT).show();
			mOnClickListener.onClick(v);
		}

	}


}
