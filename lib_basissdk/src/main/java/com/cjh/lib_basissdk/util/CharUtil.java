package com.cjh.lib_basissdk.util;

import java.io.UnsupportedEncodingException;

/**
 * 各种字符和字符序列实用工具，包括char[]-byte[]之间的转换
 */
public class CharUtil {
	
	/**
     * 单个byte转化成单个char
     */
	public static char toChar(byte b) {
		return (char) (b & 0xFF);
	}
	
	/**
     * 通过剥离每个字符的高字节，将char数组转换成byte数组, char两个字节,占16位? byte一个字节, 占8位, 要处理高8位
     */
	public final static byte[] toSimpleByteArray(char[] carr) {
		byte[] barr = new byte[carr.length];
		for (int i = 0; i < carr.length; i++) {
			barr[i] = (byte) carr[i];
		}
		return barr;
	}
	
	/**
	 * 将char序列转换成字节数组
	 * @see #toSimpleByteArray(char[])
	 * @param charSequence
	 * @return
	 */
	public final static byte[] toSimpleByteArray(CharSequence charSequence) {
        byte[] barr = new byte[charSequence.length()];
        for (int i = 0; i < barr.length; i++) {
            barr[i] = (byte) charSequence.charAt(i);
        }
        return barr;
    }
	
    // ---------------------------------------------------------------- ascii
	
	/**
     * 将byte数组转换成char数组，只需将字节扩展到chars
     */
	public final static char[] toSimpleCharArray(byte[] barr) {
        char[] carr = new char[barr.length];
        for (int i = 0; i < barr.length; i++) {
            carr[i] = (char) (barr[i] & 0xFF);
        }
        return carr;
    }
	
	/**
     * 返回一个char的ASCII值。如果发生过载，则返回0x3F
     */
    public final static int toAscii(char c) {
        if (c <= 0xFF) {
            return c;
        } else {
            return 0x3F;
        }
    }
    
    /**
     * 将char数组转换成ASCII数组
     * @see #toAscii(char)
     */
    public final static byte[] toAsciiByteArray(char[] carr) {
        byte[] barr = new byte[carr.length];
        for (int i = 0; i < carr.length; i++) {
            barr[i] = (byte) ((int) (carr[i] <= 0xFF ? carr[i] : 0x3F));
        }
        return barr;
    }
    
    // ---------------------------------------------------------------- raw arrays
    
    /**
     * 将charSequence转换成ASCII数组
     */
    public final static byte[] toAsciiByteArray(CharSequence charSequence) {
        byte[] barr = new byte[charSequence.length()];
        for (int i = 0; i < barr.length; i++) {
            char c = charSequence.charAt(i);
            barr[i] = (byte) ((int) (c <= 0xFF ? c : 0x3F));
        }
        return barr;
    }
    
    /**
     * 通过用两个字节替换每个字符，将char数组转换成字节数组
     */
    public final static byte[] toRawByteArray(char[] carr) {
        //左移1位表示乘以2
        byte[] barr = new byte[carr.length << 1];
        for (int i = 0, bpos = 0; i < carr.length; i++) {
            char c = carr[i];
            //高8位
            barr[bpos++] = (byte) ((c & 0xFF00) >> 8);
            //低8位
            barr[bpos++] = (byte) (c & 0x00FF);
        }
        return barr;
    }
    
    // ---------------------------------------------------------------- encoding
    
    public final static char[] toRawCharArray(byte[] barr) {
        //右移1位表示除以2
    	int carrLen = barr.length >> 1;
        if (carrLen << 1 < barr.length) {
            carrLen++;
        }
        char[] carr = new char[carrLen];
        int i = 0, j = 0;
        while (i < barr.length) {
            //高8位
			char c = (char) (barr[i] << 8);
			i++;
			if (i != barr.length) {
                //设置低8位
				c += barr[i] & 0xFF;
				i++;
			}
			carr[j++] = c;
		}
        return carr;
    }
    
    /**
     * 使用默认的UTF-8编码将char数组转换成字节数组
     */
    public final static byte[] toByteArray(char[] carr) throws UnsupportedEncodingException {
    	return new String(carr).getBytes(CharsetUtil.UTF_8);
    }
    
    /**
     * 使用提供的编码将char数组转换成字节数组
     */
    public final static byte[] toByteArray(char[] carr, String charset) throws UnsupportedEncodingException {
        return new String(carr).getBytes(charset);
    }
    
    /**
     * 将默认的UTF-8编码的字节数组转换成char数组
     */
    public final static char[] toCharArray(byte[] barr) throws UnsupportedEncodingException {
        return new String(barr, CharsetUtil.UTF_8).toCharArray();
    }
    
    
    // ---------------------------------------------------------------- find

    /**
     * 将特定编码的字节数组转换成char数组
     */
    public final static char[] toCharArray(byte[] barr, String charset) throws UnsupportedEncodingException {
        return new String(barr, charset).toCharArray();
    }
    
    
    /**
     * 判断字符数组中是否有指定的字符
     * @param c
     * @param match
     * @return
     */
    public final static boolean equalsOne(char c, char[] match) {
    	for (char aMatch : match) {
            if (c == aMatch) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 在给定的字符数组中找到第一个匹配给定字符数组的字符的索引，从指定的位置开始匹配
     *
     * @return index of matched character or -1
     */
    public final static int findFirstEqual(char[] source, int index, char[] match) {
        for (int i = index; i < source.length; i++) {
            if (equalsOne(source[i], match) == true) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * 在给定的字符数组中找到第一个匹配给定字符的索引，从指定的位置开始匹配
     *
     * @return index of matched character or -1
     */
    public final static int findFirstEqual(char[] source, int index, char match) {
        for (int i = index; i < source.length; i++) {
            if (source[i] == match) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * 在给定的字符数组中找到第一个不匹配给定字符数组的字符的索引，从指定的位置开始匹配
     *
     * @return index of matched character or -1
     */
    public final static int findFirstDiff(char[] source, int index, char[] match) {
        for (int i = index; i < source.length; i++) {
            if (equalsOne(source[i], match) == false) {
                return i;
            }
        }
        return -1;
    }


    /**
     *在给定的字符数组中找到第一个不匹配给定字符的索引，从指定的位置开始匹配
     *
     * @return index of matched character or -1
     */
    public final static int findFirstDiff(char[] source, int index, char match) {
        for (int i = index; i < source.length; i++) {
            if (source[i] != match) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Returns <code>true</code> if character is a white space ({@code <= ' '}).
     * White space definition is taken from String class (see: <code>trim()</code>).
     */
    public final static boolean isWhitespace(char c) {
    	return c <= ' ';
    }
    
    /**
     * 判断字符是不是小写字母
     */
    public final static boolean isLowercaseAlpha(char c) {
        return (c >= 'a') && (c <= 'z');
    }
    
    /**
     * 判断字符是不是大写字母
     */
    public final static boolean isUppercaseAlpha(char c) {
        return (c >= 'A') && (c <= 'Z');
    }
    
    /**
     * 判断字符是不是字母或者数字
     */
    public final static boolean isAlphaOrDigit(char c) {
        return isDigit(c) || isAlpha(c);
    }
    
    public final static boolean isWordChar(char c) {
        return isDigit(c) || isAlpha(c) || (c == '_');
    }
    
    public final static boolean isPropertyNameChar(char c) {
        return isDigit(c) || isAlpha(c) || (c == '_') || (c == '.') || (c == '[') || (c == ']');
    }
    
    /**
     * 判断字符是不是字母
     *
     * @see <a href="http://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
     */
    public final static boolean isAlpha(char c) {
        return ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'));
    }

    /**
     * 判断字符是不是数字
     *
     * @see <a href="http://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
     */
    public final static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
    
    /**
     * 判断给定字符是否是十六进制数字
     */
    public final static boolean isHexDigit(char c) {
        return (c >= '0' && c <= '9') || ((c >= 'a') && (c <= 'f')) || ((c >= 'A') && (c <= 'F'));
    }
    
    /**
     * 判读是不是通用的分界符
     *
     * @see <a href="http://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
     */
    public final static boolean isGenericDelimiter(int c) {
        switch (c) {
            case ':':
            case '/':
            case '?':
            case '#':
            case '[':
            case ']':
            case '@':
                return true;
            default:
                return false;
        }
    }
    
    /**
     * 判读是不是副的分界符
     *
     * @see <a href="http://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
     */
    public final static boolean isSubDelimiter(int c) {
        switch (c) {
            case '!':
            case '$':
            case '&':
            case '\'':
            case '(':
            case ')':
            case '*':
            case '+':
            case ',':
            case ';':
            case '=':
                return true;
            default:
                return false;
        }
    }
    
    /**
     * 判断是不是保留的分界符？
     *
     * @see <a href="http://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
     */
    public final static boolean isReserved(char c) {
        return isGenericDelimiter(c) || isSubDelimiter(c);
    }
    
    /**
	 * 判断是不是非保留的分界符？ 
     * @see <a href="http://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
     */
    public final static boolean isUnreserved(char c) {
        return isAlpha(c) || isDigit(c) || c == '-' || c == '.' || c == '_' || c == '~';
    }
    
    /**
     * Indicates whether the given character is in the <i>pchar</i> set.
     *
     * @see <a href="http://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
     */
    public final static boolean isPchar(char c) {
        return isUnreserved(c) || isSubDelimiter(c) || c == ':' || c == '@';
    }
    
    /**
     * 将字符转化为大写ASCII
     */
    public final static char toUpperAscii(char c) {
        if (isLowercaseAlpha(c)) {
            c -= (char) 0x20;
        }
        return c;
    }
    
    /**
     * 将字符转化为小写ASCII
     */
    public final static char toLowerAscii(char c) {
        if (isUppercaseAlpha(c)) {
            c += (char) 0x20;
        }
        return c;
    }

}
