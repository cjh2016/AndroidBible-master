package com.cjh.lib_basissdk.util;

import java.io.CharArrayWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.BitSet;

import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.InputFilter.LengthFilter;
import android.util.Base64;

/**
 * 编码相关
 */
public final class EncodeUtils {
	
	static BitSet dontNeedEncoding;
    static final int caseDiff = ('a' - 'A');
 
    static {
        /*
         * The list of characters that are not encoded has been determined as
         * follows:
         *
         * RFC 2396 states: ----- Data characters that are allowed in a URI but
         * do not have a reserved purpose are called unreserved. These include
         * upper and lower case letters, decimal digits, and a limited set of
         * punctuation marks and symbols.
         *
         * unreserved = alphanum | mark
         *
         * mark = "-" | "_" | "." | "!" | "~" | "*" | "'" | "(" | ")"
         *
         * Unreserved characters can be escaped without changing the semantics
         * of the URI, but this should not be done unless the URI is being used
         * in a context that does not allow the unescaped character to appear.
         * -----
         *
         * It appears that both Netscape and Internet Explorer escape all
         * special characters from this list with the exception of "-", "_",
         * ".", "*". While it is not clear why they are escaping the other
         * characters, perhaps it is safest to assume that there might be
         * contexts in which the others are unsafe if not escaped. Therefore, we
         * will use the same list. It is also noteworthy that this is consistent
         * with O'Reilly's "HTML: The Definitive Guide" (page 164).
         *
         * As a last note, Intenet Explorer does not encode the "@" character
         * which is clearly not unreserved according to the RFC. We are being
         * consistent with the RFC in this matter, as is Netscape.
         */
 
        dontNeedEncoding = new BitSet(256);
        int i;
        for (i = 'a'; i <= 'z'; i++) {
            dontNeedEncoding.set(i);
        }
        for (i = 'A'; i <= 'Z'; i++) {
            dontNeedEncoding.set(i);
        }
        for (i = '0'; i <= '9'; i++) {
            dontNeedEncoding.set(i);
        }

		/*
		 * encoding a space to a + is done in the
		 * encode() method
		 */
        dontNeedEncoding.set(' ');
        dontNeedEncoding.set('-');
        dontNeedEncoding.set('_');
        dontNeedEncoding.set('.');
        dontNeedEncoding.set('*');
        dontNeedEncoding.set(':');
        dontNeedEncoding.set('/');
        dontNeedEncoding.set('?');
        dontNeedEncoding.set(';');
        dontNeedEncoding.set('&');
        dontNeedEncoding.set('=');
 
    }
	

	private EncodeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
	}
	
	/**
	 * 不安全, 会把  :// 和/ 等这些符号也编码, 造成找不到路径
     * URL 编码
     * <p>若想自己指定字符集,可以使用{@link #urlEncode(String input, String charset)}方法</p>
     *
     * @param input 要编码的字符
     * @return 编码为 UTF-8 的字符串
     */
    public static String urlEncode(final String input) {
        return urlEncode(input, "UTF-8");
    }
	
	/**
	 * 不安全, 会把  :// 和/ 等这些符号也编码, 造成找不到路径
	 * URL 编码
	 * <p>若系统不支持指定的编码字符集,则直接将input原样返回</p>
	 * @param input   要编码的字符
	 * @param charsetName 字符集
	 * @return        编码为字符集的字符串
	 */
	public static String urlEncode(final String input, final String charsetName) {
		if (input == null || input.length() == 0) return "";
		try {
			return URLEncoder.encode(input, charsetName);
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError(e);
		}
	}
	
	/**
	 * 安全, 不会把  :// 和/ 等这些符号也编码, 造成找不到路径, 推荐
	 * URL 编码
	 */
	public static String encodeUrl(String url) {
		return Uri.encode(url, "-![.:/,%?&=]");
	}
	
	/**
	 * 安全, 不会把  :// 和/ 等这些符号也编码, 造成找不到路径, 推荐
	 * URL 编码
	 */
	public String toUtf8Encode(String input) {
		StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < input.length(); i++) {
	    	char c = input.charAt(i);
	        if (c >= 0 && c <= 255) {
	        	sb.append(c);
	        } else {
	            byte[] b;
	            try {
	            	b = String.valueOf(c).getBytes("utf-8");
	            } catch (Exception ex) {
	                System.out.println(ex);
	                b = new byte[0];
	            }
	            for (int j = 0; j < b.length; j++) {
	            	int k = b[j];
	                if (k < 0)
	                	k += 256;
	                sb.append("%" + Integer.toHexString(k).toUpperCase());
	            }
	       }
	    }
	    return sb.toString();
	}
	
	/**
	 * 安全, 不会把  :// 和/ 等这些符号也编码, 造成找不到路径, 推荐
	 * URL 编码
     * Translates a string into <code>application/x-www-form-urlencoded</code>
     * format using a specific encoding scheme. This method uses the supplied
     * encoding scheme to obtain the bytes for unsafe characters.
     * <p>
     * <em><strong>Note:</strong> The <a href=
     * "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
     * World Wide Web Consortium Recommendation</a> states that
     * UTF-8 should be used. Not doing so may introduce
     * incompatibilites.</em>
     *
     * @param input
     *            <code>String</code> to be translated.
     * @param charsetName
     *            The name of a supported <a
     *            href="../lang/package-summary.html#charenc">character
     *            encoding</a>.
     * @return the translated <code>String</code>.
     * @exception UnsupportedEncodingException
     *                If the named encoding is not supported
     * @see URLDecoder#decode(String, String)
     * @since 1.4
     */
    public static String encode(String input, String charsetName) throws UnsupportedEncodingException {
 
        boolean needToChange = false;
        StringBuffer out = new StringBuffer(input.length());
        Charset charset;
        CharArrayWriter charArrayWriter = new CharArrayWriter();
 
        if (charsetName == null)
            throw new NullPointerException("charsetName");
 
        try {
            charset = Charset.forName(charsetName);
        } catch (IllegalCharsetNameException e) {
            throw new UnsupportedEncodingException(charsetName);
        } catch (UnsupportedCharsetException e) {
            throw new UnsupportedEncodingException(charsetName);
        }
 
        for (int i = 0; i < input.length();) {
            int c = (int) input.charAt(i);
            // System.out.println("Examining character: " + c);
            if (dontNeedEncoding.get(c)) {
                if (c == ' ') {
                    c = '+';
                    needToChange = true;
                }
                // System.out.println("Storing: " + c);
                out.append((char) c);
                i++;
            } else {
                // convert to external encoding before hex conversion
                do {
                    charArrayWriter.write(c);
                    /*
                     * If this character represents the start of a Unicode
                     * surrogate pair, then pass in two characters. It's not
                     * clear what should be done if a bytes reserved in the
                     * surrogate pairs range occurs outside of a legal surrogate
                     * pair. For now, just treat it as if it were any other
                     * character.
                     */
                    if (c >= 0xD800 && c <= 0xDBFF) {
                        /*
                         * System.out.println(Integer.toHexString(c) +
                         * " is high surrogate");
                         */
                        if ((i + 1) < input.length()) {
                            int d = (int) input.charAt(i + 1);
                            /*
                             * System.out.println("\tExamining " +
                             * Integer.toHexString(d));
                             */
                            if (d >= 0xDC00 && d <= 0xDFFF) {
                                /*
                                 * System.out.println("\t" +
                                 * Integer.toHexString(d) +
                                 * " is low surrogate");
                                 */
                                charArrayWriter.write(d);
                                i++;
                            }
                        }
                    }
                    i++;
                } while (i < input.length() && !dontNeedEncoding.get((c = (int) input.charAt(i))));
 
                charArrayWriter.flush();
                String str = new String(charArrayWriter.toCharArray());
                byte[] ba = str.getBytes(charset);
                for (int j = 0; j < ba.length; j++) {
                    out.append('%');
                    char ch = Character.forDigit((ba[j] >> 4) & 0xF, 16);
                    // converting to use uppercase letter as part of
                    // the hex value if ch is a letter.
                    if (Character.isLetter(ch)) {
                        ch -= caseDiff;
                    }
                    out.append(ch);
                    ch = Character.forDigit(ba[j] & 0xF, 16);
                    if (Character.isLetter(ch)) {
                        ch -= caseDiff;
                    }
                    out.append(ch);
                }
                charArrayWriter.reset();
                needToChange = true;
            }
        }
 
        return (needToChange ? out.toString() : input);
    }
    
    
	
	/**
     * URL 解码
     * <p>若想自己指定字符集,可以使用 {@link #urlDecode(String input, String charset)}方法</p>
     *
     * @param input 要解码的字符串
     * @return URL 解码后的字符串
     */
    public static String urlDecode(final String input) {
        return urlDecode(input, "UTF-8");
    }
	
	/**
     * URL 解码
     * <p>若系统不支持指定的解码字符集,则直接将 input 原样返回</p>
     *
     * @param input   要解码的字符串
     * @param charsetName 字符集
     * @return URL 解码为指定字符集的字符串
     */
	public static String urlDecode(final String input, final String charsetName) {
		if (input == null || input.length() == 0) return "";
		try {
			return URLDecoder.decode(input, charsetName);
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError(e);
		}
	}
	
	/**
     * Base64 编码
     *
     * @param input 要编码的字符串
     * @return Base64 编码后的字符串
     */
	public static byte[] base64Encode(final String input) {
		return base64Encode(input.getBytes());
	}
	
	/**
	 * Base64 编码  
	 * NO_WRAP 这个参数意思是略去所有的换行符（设置后CRLF就没用了）
	 * @param input 要编码的字节数组
	 * @return Base64编码后的字符串
	 */
	public static byte[] base64Encode(final byte[] input) {
		if (input == null || input.length == 0) return new byte[0];
		return Base64.encode(input, Base64.NO_WRAP);
	}
	
	/**
	 * Base64 编码
	 * @param input  要编码的字节数组
	 * @return Base64 编码后的字符串
	 */
	public static String base64Encode2String(final byte[] input) {
		if (input == null || input.length == 0) return "";
		return Base64.encodeToString(input, Base64.NO_WRAP);
	}
	
	/**
     * Base64 解码
     *
     * @param input 要解码的字符串
     * @return Base64 解码后的字符串
     */
	public static byte[] base64Decode(final String input) {
		if (input == null || input.length() == 0) return new byte[0];
		return Base64.decode(input, Base64.NO_WRAP);
	}
	
	/**
     * Base64 解码
     *
     * @param input 要解码的字符串
     * @return Base64 解码后的字符串
     */
	public static byte[] base64Decode(final byte[] input) {
		if (input == null || input.length == 0) return new byte[0];
		return Base64.decode(input, Base64.NO_WRAP);
	}
    
    /**
     * Base64URL 安全编码
     * <p>将 Base64 中的 URL 非法字符�?,/=转为其他字符, 见 RFC3548
     * URL_SAFE 这个参数意思是加密时不使用对URL和文件名有特殊意义的字符来作为加密字符，具体就是以-和_取代+和/</p>
     *
     * @param input 要 Base64URL 安全编码的字符串
     * @return Base64URL 安全编码后的字符串
     */
    public static byte[] base64UrlSafeEncode(final String input) {
		if (input == null || input.length() == 0) return new byte[0];
		return Base64.encode(input.getBytes(), Base64.URL_SAFE);
    }
    
    
    /**
     * 对html一些特殊字符进行转义  编码
     * @param input 要 Html 编码的字符串
     * @return Html 编码后的字符串
     */
    public static String htmlEncode(final CharSequence input) {
		if (input == null || input.length() == 0) return "";
		StringBuilder sb = new StringBuilder();
    	char c;
    	
    	for (int i = 0, len = input.length(); i < len; i++) {
    		c = input.charAt(i);
    		switch (c) {
				case '<':
					sb.append("&lt;"); //$NON-NLS-1$
					break;
				case '>':
					sb.append("&gt;"); //$NON-NLS-1$
					break;
				case '&':
					sb.append("&amp;"); //$NON-NLS-1$
					break;
				case '\'':
					//http://www.w3.org/TR/xhtml1
                    // The named character reference &apos; (the apostrophe, U+0027) was
                    // introduced in XML 1.0 but does not appear in HTML. Authors should
                    // therefore use &#39; instead of &apos; to work as expected in HTML 4
                    // user agents.
                    sb.append("&#39;"); //$NON-NLS-1$
					break;
				case '"':
					sb.append("&quot;"); //$NON-NLS-1$
					break;
				default:
					sb.append(c);
			}
    	}
    	return sb.toString();
    }
    
    /**
     * Html 解码
     *
     * @param input 待解码的字符串
     * @return Html 解码后的字符串
     */
    @SuppressWarnings("deprecation")
	public static CharSequence htmlDecode(final String input) {
		if (input == null || input.length() == 0) return "";
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			return Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY);
		} else {
			return Html.fromHtml(input);
		}
	}


	/**
	 * Return the binary encoded string padded with one space
	 *
	 * @param input
	 * @return binary string
	 */
	public static String binEncode(final String input) {
		StringBuilder stringBuilder = new StringBuilder();
		for (char i : input.toCharArray()) {
			stringBuilder.append(Integer.toBinaryString(i));
			stringBuilder.append(' ');
		}
		return stringBuilder.toString();
	}

	/**
	 * Return UTF-8 String from binary
	 *
	 * @param input binary string
	 * @return UTF-8 String
	 */
	public static String binDecode(final String input) {
		String[] splitted = input.split(" ");
		StringBuilder sb = new StringBuilder();
		for (String i : splitted) {
			sb.append(((char) Integer.parseInt(i.replace(" ", ""), 2)));
		}
		return sb.toString();
	}

    
    /**
     * 半角转全角(包括空格) 
     * @param input 
     * @return 全角字符串
     */
    public static String toSBC(String input) {
    	char[] chars = input.toCharArray();
    	for (int i = 0; i < chars.length; i++) {
    		if (chars[i] == ' ') {
    			chars[i] = '\u3000';
    		} else if (chars[i] < '\177') {
    			chars[i] = (char) (chars[i] + 65248);
    		}
    	}
    	return new String(chars);
    }
    
}
