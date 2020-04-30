package com.cjh.lib_basissdk.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import android.annotation.SuppressLint;
import android.os.Build;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import androidx.collection.LongSparseArray;
import androidx.collection.SimpleArrayMap;

/**
 * 判空处理
 */
public final class EmptyUtils {

	private EmptyUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}
	
	/**
     * 判断对象是否为空
     *
     * @param obj 对象
     * @return {@code true}: 为空<br>{@code false}: 不为空
     */
	@SuppressLint("NewApi")
	public static boolean isEmpty(final Object obj) {
		
		if (null == obj) {
			return true;
		}
		
		if (obj instanceof CharSequence && obj.toString().length() == 0) {
			return true;
		}
		
		if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
			return true;
		}
		
		if (obj instanceof Collection && ((Collection)obj).isEmpty()) {
			return true;
		}
		
		if (obj instanceof Map && ((Map) obj).isEmpty()) {
			return true;
		}
		
		if (obj instanceof SimpleArrayMap && ((SimpleArrayMap) obj).isEmpty()) {
			return true;
		}
		
		if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0) {
			return true;
		}
		
		if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0) {
			return true;
		}
		
		if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0) {
			return true;
		}
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			if (obj instanceof SparseLongArray && ((SparseLongArray) obj).size() == 0) {
				return true;
			}
		}
		
		if (obj instanceof LongSparseArray && ((LongSparseArray) obj).size() == 0) {
			return true;
		}
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			if (obj instanceof android.util.LongSparseArray && ((android.util.LongSparseArray) obj).size() == 0) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
     * 判断对象是否非空
     *
     * @param obj 对象
     * @return {@code true}: 非空<br>{@code false}: 空
     */
	public static boolean isNotEmpty(final Object obj) {
		return !isEmpty(obj);
	}
}
