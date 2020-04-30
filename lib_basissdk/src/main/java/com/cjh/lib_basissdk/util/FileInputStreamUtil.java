package com.cjh.lib_basissdk.util;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * 改善FileInputStream自带方法的不可靠缺陷
 * 
 * java中对输入流的读取往往是不可靠的，例如inputStream.read(buffer,0,1024);并不是每次都能读取到1024个字节，而是最多能够读取1024个字节
 * 看源码可以看到当出现一次IO异常则该次读取结束，提高了性能的同时，其可靠性打了折扣，通常都会使用while循环来从读过的索引位置继续读取，但是有的
 * 时候我们要求不使用while循环而就要读到我们需要的字节数，下面通过设置可靠性系数，以牺牲发生IO异常时的性能来提高其可靠性，对jdk源码稍加改造如下：
 *
 *
 */
public class FileInputStreamUtil {

	/**
	 * 改善FileInputStream自带read方法不可靠的问题
	 * @param oFileInputStream 要读取的流
	 * @param b 接收数组
	 * @param off 从数组的哪个位置
	 * @param len 读的长度
	 * @param reReadMaxCount 可靠系数(默认同原生InputStream)
	 * @return
	 */
	public static int read(FileInputStream oFileInputStream, byte b[], int off, int len, int reReadMaxCount) throws IOException {
		if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }
		if (reReadMaxCount <= 0) {
			reReadMaxCount = 1;
		}
        int nCount = 1;
        int nTempLength = 0;
        int bytesRead = 0;
        while (bytesRead < len && nTempLength != -1) {
        	try {
        		nTempLength = oFileInputStream.read(b, off + bytesRead, len - bytesRead);
        	} catch (IOException e) {
        		nCount ++;
        		if (nCount > reReadMaxCount) {
        			throw e;
        		}
        	}
        	if (nTempLength != -1) {
        		bytesRead += nTempLength;
        	}
        }
        return bytesRead;
	}

	/**
	 * 改善FileInputStream自带skip方法不可靠的问题
	 * @param oFileInputStream 要读取的流
	 * @param n 要跳过的长度
	 * @param reReadMaxCount  可靠系数(默认同原生InputStream)
	 * @return
	 * @throws IOException
	 */
	public static long skip(FileInputStream oFileInputStream, long n, int reReadMaxCount) throws IOException {
		if (n <= 0) {
            return 0;
        }
		if (reReadMaxCount <= 0) {
			reReadMaxCount = 1;
		}
		int nCount = 1;
		long remain = n;
		long refactSkipLength = 0;
		while (remain > 0 && refactSkipLength != -1) {
			try {
				refactSkipLength = oFileInputStream.skip(remain);
			} catch (IOException e) {
				nCount ++;
				if (nCount > reReadMaxCount) {
					throw e;
				}
			}
			if (refactSkipLength != -1) {
				remain -= refactSkipLength;
			}
		}
		return n - remain;
	}
	
}
