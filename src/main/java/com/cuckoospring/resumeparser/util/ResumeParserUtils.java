package com.cuckoospring.resumeparser.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 简历解析工具类
 * @author cuckoo-spring
 *
 */
public final class ResumeParserUtils {

    /** 禁止实例化 */
    private ResumeParserUtils() {}

    /**
     * 字符串首字母大写
     * @param str 字符串
     * @return 首字母大写的字符串
     */
    public static String toUpperCaseFirst(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        char[] chars = str.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] = (char)(chars[0] - 32);
        }
        return new String(chars);
    }

}
