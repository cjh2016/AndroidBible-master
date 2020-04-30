package com.cjh.lib_basissdk.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import android.text.TextUtils;

/**
 * 集合工具类
 */
public final class CollectionUtils {
	
	private static final String DEFAULT_DELIMITER = ",";
	
	private CollectionUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}
	
	/**
     * 判断集合是否为空
     *
     * @param c
     * @param <E>
     * @return
     */
	
	public static <E> boolean isEmpty(Collection<E> c) {
		return (null == c || c.size() == 0);
	}
	
	/**
     * 把集合转化成字符串, 分隔符是{@link #DEFAULT_DELIMITER}
     *
     * @param iterable
     * @return
     */
	public static String join(Iterable iterable, String delimiter) {
		if (null == delimiter || "".equals(delimiter)) delimiter = DEFAULT_DELIMITER;
		return iterable == null ? "" : TextUtils.join(delimiter, iterable);
	}
	
	/**
     * 将数组转为list
     *
     * @param array
     * @param <E>
     * @return
     */
	public static <E> List<E> arrayToList(E... array) {
        return Arrays.asList(array);
	}
	
	/**
     * 将数组转为set集合
     *
     * @param array
     * @param <E>
     * @return
     */
	public static <E> Set<E> arrayToSet(E... array) {
		return new HashSet<E>(arrayToList(array));
	}
	
	/**
     * 集合转为数组
     *
     * @param c
     * @return
     */
	public static Object[] listToArray(Collection<?> c) {
		if (!isEmpty(c)) {
			return c.toArray();
		}
		return null;
	}
	
	/**
     * list转为set
     *
     * @param list
     * @param <E>
     * @return
     */
	public static <E> Set<E> listToSet(List<E> list) {
		return new HashSet<E>(list);
	}
	
	/**
     * Convert set to list
     * set转为list
     *
     * @param set
     * @param <E>
     * @return
     */
	public static <E> List<E> setToList(Set<E> set) {
		return new ArrayList<E>(set);
	}
	
	/**
     * Traverse collection
     * 遍历集合
     *
     * @param c
     * @param <E>
     * @return
     */
	public static <E> String traverseCollection(Collection<E> c) {
		if (!isEmpty(c)) {
			int len = c.size();
			StringBuilder builder = new StringBuilder(len);
			int i = 0;
			for (E t : c) {
				if (null == t) continue;
				builder.append(t.toString());
				i++;
				if (i < len) {
					builder.append(DEFAULT_DELIMITER);
				}
			}
			return builder.toString();
		}
		return null;
	}
	

}
