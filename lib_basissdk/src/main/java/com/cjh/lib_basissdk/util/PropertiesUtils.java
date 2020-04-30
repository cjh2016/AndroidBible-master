package com.cjh.lib_basissdk.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 提供一些常用的属性文件相关的方法
 */
public final class PropertiesUtils {

	/**
     * 从系统属性文件中获取相应的值
     *
     * @param key key
     * @return 返回value
     */
    public final static String key(String key) {
        return System.getProperty(key);
    }
    
    /**
     * 根据Key读取Value
     *
     * @param filePath 属性文件
     * @param key      需要读取的属性
     */
    public final static String getValueByKey(String filePath, String key) {
        Properties pps = new Properties();
        InputStream in = null;
        try {
        	in = new BufferedInputStream(new FileInputStream(filePath));
        	pps.load(in);
            return pps.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
        	if (null != in) {
        		try {
					in.close();
				} catch (IOException e) {
				}
        	}
        }
    }
    
    
    public final static Map<String,String> properties(InputStream in){
        Map<String,String> map = new HashMap<>();
        Properties pps = new Properties();
        try {
            pps.load(in);
        } catch (IOException e) {
        }
        Enumeration en = pps.propertyNames();
        while (en.hasMoreElements()) {
            String strKey = (String) en.nextElement();
            String strValue = pps.getProperty(strKey);
            map.put(strKey,strValue);
        }
        return map;
    }
    
    /**
     * 读取Properties的全部信息
     *
     * @param filePath 读取的属性文件
     * @return 返回所有的属性 key:value<>key:value
     */
    public final static Map<String,String> getAllProperties(String filePath) {
    	InputStream in = null;
        try {
        	in = new BufferedInputStream(new FileInputStream(filePath));
            return properties(in);
        } catch (IOException e){
        } finally {
        	if (null != in) {
        		try {
					in.close();
				} catch (IOException e) {
				}
        	}
        }
        return Collections.EMPTY_MAP;
    }
    
    /**
     * 写入Properties信息
     *
     * @param filePath 写入的属性文件
     * @param pKey     属性名称
     * @param pValue   属性值
     */
    public final static void writeProperties(String filePath, String pKey, String pValue) {
        Properties props = new Properties();
        try {
			props.load(new FileInputStream(filePath));
			// 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
	        // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
	        OutputStream fos = new FileOutputStream(filePath);
	        props.setProperty(pKey, pValue);
	        // 以适合使用 load 方法加载到 Properties 表中的格式，
	        // 将此 Properties 表中的属性列表（键和元素对）写入输出流
	        props.store(fos, "Update '" + pKey + "' value");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    
}
