/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 16:40:01 
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-01-29 12:27:25
 */
package top.imwonder.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * StringUtil
 * 
 * @description: 字符串工具类
 **/
public class StringUtil {

    private static Pattern humPattern = Pattern.compile("[A-Z]");

    /**
     * 判断是否为空字符串最优代码
     * 
     * @param str
     * @return 如果为空，则返回true
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断字符串是否非空
     * 
     * @param str 如果不为空，则返回true
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否全部为空
     * 
     * @param str 如果全部为空，则返回true
     * @return
     */
    public static boolean isAllEmpty(String... strs) {
        for (String str : strs) {
            if (!isEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否全部非空
     * 
     * @param str 如果全部非空，则返回true
     * @return
     */
    public static boolean isAllNotEmpty(String... strs) {
        for (String str : strs) {
            if (isEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    public static String toHumpCase(String str, String regex, boolean isFirstAlphaUpperCase, boolean hasPerfix) {
        StringBuffer sb = new StringBuffer();
        Queue<String> qu = new LinkedList<>(Arrays.asList(str.toLowerCase().split(regex)));
        if (hasPerfix) {
            qu.poll();
        }
        if (qu.size() <= 0) {
            return "";
        }
        String head = qu.poll();
        if (isFirstAlphaUpperCase) {
            sb.append(head.substring(0, 1).toUpperCase());
            sb.append(head.substring(1));
        } else {
            sb.append(head);
        }
        for (String s : qu) {
            sb.append(s.substring(0, 1).toUpperCase());
            sb.append(s.substring(1));
        }
        return sb.toString();
    }

    public static String toSeparator(String str, String separator, boolean isToUpperCase, String... perfix) {
        Matcher matcher;
        if (isToUpperCase) {
            matcher = humPattern.matcher(str.toUpperCase());
        } else {
            matcher = humPattern.matcher(str);
        }
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, separator + matcher.group(0));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 将字符串转为 URL 安全的 base64
     * 
     * @param str
     * @return
     */
    public static String encodeToBase64(String str) {
        if (str == null) {
            return null;
        } else if ("".equals(str)) {
            return str;
        }
        try {
            return Base64.encodeBase64URLSafeString(URLEncoder.encode(str, "utf-8").getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            // It is Impossible to happen!
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 将 base64 解码为字符串，并自动解码 urlencode。
     * 
     * @param str
     * @return
     */
    public static String decodeBase64ToString(String str) {
        if (str == null) {
            return null;
        } else if ("".equals(str)) {
            return str;
        }
        try {
            return URLDecoder.decode(new String(Base64.decodeBase64(str)), "utf-8");
        } catch (UnsupportedEncodingException e) {
            // It is Impossible to happen!
            e.printStackTrace();
            return "";
        }
    }
}