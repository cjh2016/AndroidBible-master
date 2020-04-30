package com.cjh.lib_basissdk.util;

import java.math.BigDecimal;
import android.text.TextUtils;

/**
 * Double数据的操作 使用Java，double 进行运算时，经常出现精度丢失的问题，总是在一个正确的结果左右偏0.0000**1。
 * 特别在实际项目中，通过一个公式校验该值是否大于0，如果大于0我们会做一件事情，小于0我们又处理其他事情。
 * 这样的情况通过double计算出来的结果去和0比较大小，尤其是有小数点的时候，经常会因为精度丢失而导致程序处理流程出错。
 */
public class DoubleUtils {
	
	/**
     * 获取一个double类型的数字的小数位有多长
     * @param dd
     * @return
     */
	public static int doubleDecimalCount(double dd) {
		String temp = String.valueOf(dd);
		int i = temp.indexOf(".");
		if (i > -1) {
			return temp.length() - i - 1;
		}
		return 0;
	}
	
	/**
     * 获取一个double[]类型的数字的小数位有多长
     * @param dds
     * @return
     */
	public static Integer[] doubleDecimalCounts(double[] dds) {
		Integer[] len = new Integer[dds.length];
		for (int i = 0; i < dds.length; i++) {
			len[i] = doubleDecimalCount(dds[i]);
		}
		return len;
	}
	
	/**
	 * 提供精确的加法运算。
	 * 
	 * @param d1
	 *            被加数
	 * @param d2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(d1);
		BigDecimal bd2 = new BigDecimal(d2);
		try {
			return bd1.add(bd2).doubleValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 提供精确的减法运算。
	 * 
	 * @param d1
	 *            被减数
	 * @param d2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(d1);
		BigDecimal bd2 = new BigDecimal(d2);
		try {
			return bd1.subtract(bd2).doubleValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	/**
     * double 乘法
     * 
     * @param d1
     * @param d2
     * @return
     */
    public static double mul(double d1, double d2) {
    	BigDecimal bd1 = new BigDecimal(d1);
    	BigDecimal bd2 = new BigDecimal(d2);
    	try {
			return bd1.multiply(bd2).doubleValue();
		} catch (Exception e) {
			// 根据bugly观测，在进入GTOpMulPerfActivity页时有极小概率crash，故加上异常保护
            // @see http://bugly.qq.com/detail?app=900010910&pid=1&ii=152#stack
            e.printStackTrace();
            return 0;
		}
    }
    
    /**
     * double 除法
     * 
     * @param d1
     * @param d2
     * @param scale
     *            四舍五入 小数点位数
     * @return
     */
    public static double div(double d1, double d2, int scale) {
    	// 当然在此之前，你要判断分母是否为0，
        // 为0你可以根据实际需求做相应的处理
    	
    	BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        
        try {
			return bd1.divide(bd2, scale, BigDecimal.ROUND_DOWN).doubleValue();
		} catch (Exception e) {
			e.printStackTrace();
            return 0;
		}
    }
    
    /**
     * 
     * 提供精确的小数位四舍五入处理。
     * 
     * @param d
     *            需要四舍五入的数字
     * 
     * @param scale
     *            小数点后保留几位
     * 
     * @return 四舍五入后的结果
     * 
     */
    public static double round(double d, int scale) {
    	if (scale < 0) {
    		throw new IllegalArgumentException("The scale must be a positive integer or zero");
    	}
    	BigDecimal bd = new BigDecimal(d);
    	BigDecimal one = new BigDecimal(1);
    	return bd.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    public static Double getDouble(String value) throws NumberFormatException {
    	try {
			return Double.valueOf(value);
		} catch (NumberFormatException e) {
			throw new NumberFormatException();
		}
    }
    
    public static Double getDouble(String value, Double defaultDouble) {
    	try {
    		if (!TextUtils.isEmpty(value)) {
    			return Double.valueOf(value);
    		}
		} catch (NumberFormatException e) {
		}
    	return defaultDouble;
    }
    
    public static Double getDouble(Object value, Double defaultDouble) {
    	try {
    		if (!TextUtils.isEmpty(value.toString())) {
    			return Double.valueOf(value.toString());
    		}
		} catch (NumberFormatException e) {
		}
    	return defaultDouble;
    }

}
