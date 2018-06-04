package com.dylan.baselib.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Dylan on 2017/9/29.
 * 中文--拼音转换工具类
 */

public class Cn2Spell {


    public static StringBuffer sb = new StringBuffer();
    private static Map<String, String> mStrMap = new HashMap();

    /**
     * 多音字map
     */
    static {
        mStrMap.put("曾", "Z");
        mStrMap.put("单", "S");
        mStrMap.put("查", "Z");
        mStrMap.put("仇", "Q");
        mStrMap.put("区", "O");
        mStrMap.put("解", "X");
    }

    /**
     * 获取汉字字符串的首字母，英文字符不变
     * 例如：阿飞→af
     */
    public static String getPinYinHeadChar(String chines) {
        sb.setLength(0);
        char[] chars = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] > 128) {
                try {
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(chars[i], defaultFormat)[0].charAt(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                sb.append(chars[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 获取汉字字符串的第一个字母
     */
    public static String getPinYinFirstLetter(String str) {
        sb.setLength(0);
        char c = str.charAt(0);
        /*查找是否是多音字*/
        Iterator<Map.Entry<String, String>> entries = mStrMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            if (entry.getKey().equals(str.substring(0, 1))) {
                return entry.getValue();
            }
        }
        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
        if (pinyinArray != null) {
            sb.append(pinyinArray[0].charAt(0));
        } else {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 获取汉字字符串的汉语拼音，英文字符不变
     */
    public static String getPinYin(String chines) {
        sb.setLength(0);
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                sb.append(nameChar[i]);
            }
        }
        return sb.toString();
    }


}
