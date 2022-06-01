package com.common.utils;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * @Description
 * @author AbrahamVong
 * @since 26/5/2022
 */
public class StringUtils {

    public StringUtils() {
    }

    public static boolean isNotBlank(String str) {
        boolean result = false;
        if (null != str && clearBlank(str).length() > 0 && !"null".equalsIgnoreCase(str) && !"undefined".equalsIgnoreCase(str)) {
            result = true;
        }

        return result;
    }

    public static boolean isObjNotBlank(Object obj) {
        boolean result = false;
        if (null != obj && isNotBlank(obj.toString())) {
            result = true;
        }

        return result;
    }

    public static boolean isObjBlank(Object obj) {
        boolean result = true;
        if (isObjNotBlank(obj)) {
            result = false;
        }

        return result;
    }

    public static boolean isBlank(String str) {
        boolean result = true;
        if (isNotBlank(str)) {
            result = false;
        }

        return result;
    }

    public static boolean isEmpty(String str) {
        return null == str || str.length() <= 0;
    }

    public static String clearBlank(String str) {
        if (null != str) {
            str = str.replaceAll(" ", "");
            str = str.replaceAll("\t", "");
        }

        return str;
    }

    public static boolean isTrue(String str) {
        return isNotBlank(str) ? "true".equalsIgnoreCase(str) : false;
    }

    public static String lowerCaseFirst(String str) {
        if (isNotBlank(str)) {
            str = str.replaceFirst(String.valueOf(str.charAt(0)), String.valueOf(Character.toLowerCase(str.charAt(0))));
        }

        return str;
    }

    public static String upperCaseFirst(String str) {
        if (isNotBlank(str)) {
            str = str.replaceFirst(String.valueOf(str.charAt(0)), String.valueOf(Character.toUpperCase(str.charAt(0))));
        }

        return str;
    }

    public static boolean isLetterUpperCase(String str, int idx) {
        return isNotBlank(str) && idx >= 0 && str.length() > idx ? Character.isUpperCase(str.charAt(idx)) : false;
    }

    public static String toHumpStr(String sourceStr, String... separatorStr) {
        String result = null;
        if (isNotBlank(sourceStr)) {
            if (0 == separatorStr.length) {
                separatorStr = new String[]{"_"};
            }

            String[] var3 = separatorStr;
            int var4 = separatorStr.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String s = var3[var5];
                if (sourceStr.contains(s)) {
                    String[] strArr = sourceStr.split(s);
                    String[] var8 = strArr;
                    int var9 = strArr.length;

                    for(int var10 = 0; var10 < var9; ++var10) {
                        String str = var8[var10];
                        str = str.toLowerCase();
                        if (null == result) {
                            result = str;
                        } else {
                            result = result.concat(upperCaseFirst(str));
                        }
                    }
                }
            }
        }

        return null == result ? sourceStr : result;
    }

    public static String hump2dbFieldStr(String sourceStr, String separatorStr, Integer upperLower) {
        String resultStr = null;
        if (isNotBlank(sourceStr)) {
            char[] charArr = sourceStr.toCharArray();
            char[] var5 = charArr;
            int var6 = charArr.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                char ch = var5[var7];
                if (null == resultStr) {
                    resultStr = String.valueOf(ch);
                } else if (Character.isUpperCase(ch)) {
                    resultStr = resultStr.concat(separatorStr).concat(String.valueOf(ch));
                } else {
                    resultStr = resultStr.concat(String.valueOf(ch));
                }
            }
        }

        if (null != upperLower) {
            if (1 == upperLower) {
                resultStr = resultStr.toLowerCase();
            } else if (2 == upperLower) {
                resultStr = resultStr.toUpperCase();
            }
        }

        return resultStr;
    }

    public static String toString(Object obj) {
        return null != obj ? obj.toString() : null;
    }

    public static String randomCode(Integer length) {
        String base = "abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            char ch = base.charAt((new Random()).nextInt(base.length()));
            sb.append(ch);
        }

        return sb.toString();
    }

    public static String randomCodeWithSpecialCode(Integer length) {
        String base = "abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789~'!@#￥$%^&*()-+_=:";
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            char ch = base.charAt((new Random()).nextInt(base.length()));
            sb.append(ch);
        }

        return sb.toString();
    }

    public static Double getRandomDouble(Integer min, Integer max, Integer precision) {
        if (null == precision) {
            precision = 2;
        }

        Double scale = Math.pow(10.0D, (double)precision);
        return (double)((new Random()).nextInt(max) % (max - min) + min) + (double)Math.round(Math.random() * (double)scale.intValue()) / scale;
    }

    public static String getRandomDouble(Integer min, Integer max, String pattern) {
        Double result = (double)((new Random()).nextInt(max) % (max - min) + min) + Math.random();
        if (isNotBlank(pattern)) {
            DecimalFormat df = new DecimalFormat(pattern);
            return df.format(result);
        } else {
            return result.toString();
        }
    }

    public static Integer getRandomInt(Integer min, Integer max, boolean maxAllow) {
        return (new Random()).nextInt(max) % (max - min + (maxAllow ? 1 : 0)) + min;
    }

}
