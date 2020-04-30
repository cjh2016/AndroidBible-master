package com.cjh.lib_basissdk.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * 改善InputStream自带方法的不可靠缺陷
 * 
 * java中对输入流的读取往往是不可靠的，例如inputStream.read(buffer,0,1024);并不是每次都能读取到1024个字节，而是最多能够读取1024个字节
 * 看源码可以看到当出现一次IO异常则该次读取结束，提高了性能的同时，其可靠性打了折扣，通常都会使用while循环来从读过的索引位置继续读取，但是有的
 * 时候我们要求不使用while循环而就要读到我们需要的字节数，下面通过设置可靠性系数，以牺牲发生IO异常时的性能来提高其可靠性，对jdk源码稍加改造如下：
 *
 *
 */
public class InputStreamUtil {

	private static native int readBytes(byte b[], int off, int len) throws IOException;

	/**
	 * 改善InputStream自带read方法不可靠的问题
	 * @param oInputStream 要读取的流
	 * @param b 接收数组
	 * @param off 从数组的哪个位置
	 * @param len 读的长度
	 * @param reReadMaxCount 可靠系数(默认同原生InputStream)
	 * @return
	 */
	public static int read(InputStream oInputStream, byte b[], int off, int len, int reReadMaxCount) throws IOException {
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
        int c = -1;
        try {
        	c = oInputStream.read();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        if (c == -1) {
            return -1;
        }
        b[off] = (byte)c;
        int i = 1;
        int nCount = 1;
        while (i < len && nCount <= reReadMaxCount) {
        	try {
        		c = oInputStream.read();
        		if (c == -1) {
        			break;
        		}
        		b[off + i] = (byte)c;
        		i++;
        	} catch (IOException e) {
        		nCount++;
        	}
        }
        if (nCount > reReadMaxCount && reReadMaxCount != 1) {
        	throw new IOException();
        }
        return i;
	}

	/**
	 * 改善InputStream自带skip方法不可靠的问题
	 * @param oInputStream 要读取的流
	 * @param n 要跳过的长度
	 * @param reReadMaxCount  可靠系数(默认同原生InputStream)
	 * @return
	 * @throws IOException
	 */
	public static long skip(InputStream oInputStream, long n, int reReadMaxCount) throws IOException {
		long remaining = n;
        int nr = 0;
        if (n <= 0) {
            return 0;
        }
        int nCount = 1;
        if (reReadMaxCount <= 0) {
        	reReadMaxCount = 1;
        }
        int size = (int)remaining;
        byte[] skipBuffer = new byte[size];
        while (remaining > 0 && nCount <= reReadMaxCount) {
            try {
            	nr = oInputStream.read(skipBuffer, nr, (int)Math.min(size, remaining));
            } catch (IOException e) {
            	nCount++;
            }
            if (nr < 0) {
                break;
            }
            remaining -= nr;
        }
        if (nCount > reReadMaxCount && reReadMaxCount != 1) {
        	throw new IOException();
        }
        return n - remaining;
	}
	
	
	/**
	 * 还没测试过
	 * 重写了Inpustream 中的skip(long n)方法，将数据流中起始的n个字节跳过
	 * 思路就是从头开始读取，等到读到n时，再使用，这恰恰就是Java源码中InputStream.skip的思路
	 * 其实这个方法和Java官方源码的skip方法完全一样，只不过多加了一个InputStream的参数，当使用
 	 * inputStream来跳过n个字节时，可以使用这个方法。唯一不明白的就是不知道为什么同样一个方法，
 	 * 放在java源码中调用会失败，但是自己写的却完全正常
	 */  
	public static long skipBytesFromStream(InputStream inputStream, long n) {  
		long remaining = n;  
		// SKIP_BUFFER_SIZE is used to determine the size of skipBuffer  
		int SKIP_BUFFER_SIZE = 2048;  
		// skipBuffer is initialized in skip(long), if needed.  
		byte[] skipBuffer = null;  
		int nr = 0;  
		if (skipBuffer == null) {  
			skipBuffer = new byte[SKIP_BUFFER_SIZE];  
		}  
		byte[] localSkipBuffer = skipBuffer;  
		if (n <= 0) {  
			return 0;  
		}  
		while (remaining > 0) {  
			try {  
				nr = inputStream.read(localSkipBuffer, 0,  
				(int) Math.min(SKIP_BUFFER_SIZE, remaining));  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
			if (nr < 0) {  
				break;  
			}  
			remaining -= nr;  
		}  
		return n - remaining;  
	}  
	
	
	/**
	 * 还没测试过
	 * @param oInputStream
	 * @param n
	 * @return
	 * @throws IOException
	 */
	public static long skip(InputStream oInputStream, long n) throws IOException {
		long remaining = n;  
	    while(remaining > 0) {  
	    	long amt = oInputStream.skip(remaining);  
	        if (amt == -1) {  
	        	throw new IOException(": unexpected EOF");  
	        }  
	        remaining -= amt;  
	    }
	    return n - remaining;
	}
	
}
