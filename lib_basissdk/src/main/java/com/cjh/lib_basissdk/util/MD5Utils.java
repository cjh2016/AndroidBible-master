package com.cjh.lib_basissdk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5相关操作
 */
public final class MD5Utils {
	
	public static String encodeMD5(String source) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(source.getBytes());// 更新摘要
			byte messageDigest[] = digest.digest();
			return toHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String toHexString(byte keyData[]) {
		if (keyData == null) return null;
		int expectedStringLen = keyData.length * 2; //16进制用两位表示
		StringBuilder sb = new StringBuilder(expectedStringLen);
		for (int i = 0; i < keyData.length; i++) {
			//int是32字节的，而byte是8字节的,和0xff相与后,高24位就会被清0了, 16代表16进制
			String hexStr = Integer.toString(keyData[i] & 0xFF, 16);  
			if (hexStr.length() == 1) {
                hexStr = "0" + hexStr;
            }
			sb.append(hexStr);
		}
		return sb.toString();
	}
	
	public final static String encodeMD52(String s) {
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		try {
			byte[] btInput = s.getBytes();
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(btInput);// 更新摘要
            byte[] md = digest.digest(); //获取摘要
            int size = md.length;
            char str[] = new char[size * 2];
            int k = 0;
            for (int i = 0; i < size; i++) {
            	byte byte0 = md[i];
            	str[k++] = hexDigits[byte0 >>> 4 & 0xf]; //获取高四位
            	str[k++] = hexDigits[byte0 & 0xf];       //获取低四位
            }
            return new String(hexDigits);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String encodeMD5(File file) {
		if (!file.isFile()) return null;
		MessageDigest digest = null;
		FileInputStream fis = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(file);
			while ((len = fis.read(buffer, 0, buffer.length)) != -1) {
				digest.update(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//-1 for negative, 0 for zero, 1 for positive
		//用BigInteger来表示大数据
		BigInteger bigInteger = new BigInteger(1, digest.digest());
		return bigInteger.toString(16);
	}

}
