package com.worldunion.dylanapp.utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Dylan
 * @time 2016/12/14 14:17
 * @des 禁止emoji表情的过滤器
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class EditInputFilterEmoji implements InputFilter {

    private int mMax;

    public EditInputFilterEmoji(int max) {
        this.mMax = max;
    }

    public EditInputFilterEmoji() {
    }

    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        /*判断是否有表情*/
        if (isHasEmoji(source)) {
            return "";
        }
        /*判断是否超过长度*/
        if (mMax != 0) {
            int keep = mMax - (dest.length() - (dend - dstart));
            if (keep <= 0) {
                return "";
            } else if (keep >= end - start) {
                return null; // keep original
            } else {
                keep += start;
                if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                    --keep;
                    if (keep == start) {
                        return "";
                    }
                }
                return source.subSequence(start, keep);
            }
        }
        return null;
    }

    /**
     * @return 返回最大长度
     */
    public int getMax() {
        return mMax;
    }


    /**
     * 判断是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean isHasEmoji(CharSequence source) {
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        //过滤emoji
        Matcher emojiMatcher = emoji.matcher(source);
        if (emojiMatcher.find()) {
            return true;
        }
        return false;
    }

}
