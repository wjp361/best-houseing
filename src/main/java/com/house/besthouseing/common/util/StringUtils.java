package com.house.besthouseing.common.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StringUtils extends org.apache.commons.lang3.StringUtils{
    
	private static final String CHARSET_NAME = "UTF-8";
	
	/**
     * 转换为字节数组
     * @param str
     * @return
     */
    public static String toString(byte[] bytes){
    	try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
    }
	/**
     * 转换为字节数组
     * @param str
     * @return
     */
    public static byte[] getBytes(String str){
    	if (str != null){
    		try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
    	}else{
    		return null;
    	}
    }
    
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isBlank(CharSequence... css) {
        if (css == null) {
            return true;
        } else {
            CharSequence[] var4 = css;
            int var3 = css.length;

            for(int var2 = 0; var2 < var3; ++var2) {
                CharSequence cs = var4[var2];
                if (isNotBlank(cs)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static String join(Object[] array, String separator) {
        return join((Iterable<Object>)Arrays.asList(array), separator);
    }

    public static String join(Iterable<?> it, String separator) {
        Iterator<?> iterator = null;
        if ((iterator = it.iterator()) != null && iterator.hasNext()) {
            Object first = iterator.next();
            if (!iterator.hasNext()) {
                return String.valueOf(first);
            } else {
                StringBuilder buf = (new StringBuilder()).append(first);

                while(iterator.hasNext()) {
                    Object obj = iterator.next();
                    if (obj != null) {
                        buf.append(separator).append(obj);
                    }
                }
                return buf.toString();
            }
        } else {
            return null;
        }
    }

    public static String substring(String str, int length, boolean dot) {
        return substring(str, 0, length, dot);
    }

    public static String substring(String str, int beginIndex, int endIndex, boolean dot) {
        if (str != null && str.length() >= endIndex + 1) {
            str = str.substring(beginIndex, endIndex);
            return dot ? str + "..." : str;
        } else {
            return str;
        }
    }

    public static String[] split(String str, String separator) {
        return split(str, separator, false);
    }

    public static String[] split(String str, String separator, boolean bool) {
        String[] strs = null;
        if (str != null) {
            strs = str.split(separator);
            if (bool) {
                int i = 0;

                for(int length = strs.length; i < length; ++i) {
                    strs[i] = strs[i] + separator;
                }
            }
        }
        return strs;
    }

    public static List<String> split(String str, int step) {
        if (str != null && step > 0) {
            ArrayList<String> list;
            if (step > str.length()) {
                list = new ArrayList<String>(1);
                list.add(str);
            } else {
                list = new ArrayList<String>();
                int i = 0;
                do {
                    list.add(str.substring(i, i += step));
                } while(i + step < str.length());

                if (i < str.length()) {
                    list.add(str.substring(i, str.length()));
                }
            }
            return list;
        } else {
            return null;
        }
    }
}
