package com.yiguohan.easylib;

/**
 * 字符串工具类
 * Created by yiguohan on 2017/11/13.
 * github: https://www.github.com/yiguohan
 * email: yiguohan@gmail.com
 */

public class StringUtils {

    private StringUtils() {
    }

    /**
     * 判断字符串是否为空字符串
     *
     * @param content 传入的字符串
     * @return
     */
    public static boolean isSpace(String content) {
        if (content == null) {
            return true;
        }
        for (int i = 0; i < content.length(); i++) {
            //只要有一个字符是非空格则跳出返回false
            if (!Character.isSpaceChar(content.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
