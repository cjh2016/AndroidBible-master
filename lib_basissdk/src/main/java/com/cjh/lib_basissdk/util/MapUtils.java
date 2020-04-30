package com.cjh.lib_basissdk.util;

import java.util.Map;

/**
 * Map相关操作
 *
 */
public final class MapUtils {
	
	private static final String DEFAULT_DELIMITER = ",";

	private MapUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}
	
	/**
     * Judge whether a map is null or size is 0
     * 判断是否为空或者为零
     *
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return (map == null || map.size() == 0);
    }
    
    /**
     * Map遍历
     *
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> String traverseMap(Map<K, V> map) {
        if (!isEmpty(map)) {
            int len = map.size();
            StringBuilder builder = new StringBuilder(len);
            int i = 0;
            for (Map.Entry<K, V> entry : map.entrySet()) {
                if (entry == null)  continue;
                builder.append(entry.getKey().toString() + ":" + entry.getValue().toString());
                i++;
                if (i < len) {
                    builder.append(DEFAULT_DELIMITER);
                }
            }
            return builder.toString();
        }
        return null;
    }
    
    /**
     * 根据值获取键
     *
     * @param map
     * @param value
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
    	if (isEmpty(map)) return null;
    	for (Map.Entry<K, V> entry : map.entrySet()) {
            if (ObjectUtils.equals(entry.getValue(), value)) {
                return entry.getKey();
            }
        }
    	return null;
    }
    
}
