package com.worldunion.dylanapp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author Dylan
 * @time 2016/11/3 14:16
 * @des 常用工具类
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class CommonUtils {

    /**
     * 判断对象是否为NotEmpty(!null或元素>0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj
     *            待检查对象
     * @return boolean 返回的布尔值
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(Object pObj) {
        if (pObj == null)
            return false;
        if (pObj.equals(""))
            return false;
        if (pObj instanceof String) {
            if (((String) pObj).length() == 0) {
                return false;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return false;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断对象是否Empty(null或元素为0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj
     *            待检查对象
     * @return boolean 返回的布尔值
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object pObj) {
        if (pObj == null)
            return true;
        if (pObj.equals(""))
            return true;
        if (pObj instanceof String) {
            if (((String) pObj).length() == 0) {
                return true;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return true;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return true;
            }
        }
        return false;
    }


    /**
     * JSON对象在get值的时候判断不为空和NaN
     *
     * @date 2013-12-7 下午4:56:29
     * @param object
     * @return
     */
    public static boolean isNotEmptyAndNaN(Object object) {
        if (isNotEmpty(object) && !object.toString().equals("NaN")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 使用StringTokenizer类将字符串按分隔符转换成字符数组
     *
     * @param string
     *            字符串
     * @param divisionChar
     *            分隔符
     * @return 字符串数组
     * @see [类、类#方法、类#成员]
     */
    public static String[] stringAnalytical(String string, String divisionChar) {
        int i = 0;
        StringTokenizer tokenizer = new StringTokenizer(string, divisionChar);
        String[] str = new String[tokenizer.countTokens()];
        while (tokenizer.hasMoreTokens()) {
            str[i] = new String();
            str[i] = tokenizer.nextToken();
            i++;
        }
        return str;
    }

    /**
     * activity切换公共方法
     *
     * @param activity
     * @param activityclass
     * @param bundle
     */
    public static void changeActivity(Activity activity, Class<?> activityclass, Bundle bundle) {
        Intent intent = new Intent(activity, activityclass);
        if (CommonUtils.isNotEmpty(bundle)) {
            intent.putExtras(bundle);
        }
        if (CommonUtils.isNotEmpty(activity)) {
            activity.startActivity(intent);
        }
    }

    /**
     * activity切换公共方法_forResult
     *
     * @param activity
     * @param activityclass
     * @param bundle
     */
    public static void changeActivityForResult(Activity activity, Class<?> activityclass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(activity, activityclass);
        if (CommonUtils.isNotEmpty(bundle)) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 数据格式化.
     *
     */
    public static String codeFormat(String pattern, Object value) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }

    /**
     * 格式化时间.
     *
     * @param date
     *            the date
     * @return the string
     */
    public static String fomatDate(String date) {
        if (isNotEmpty(date)) {
            return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
        }
        return null;
    }

    /**
     * 格式化时间.
     *
     * @param date
     *            the date
     * @return the string
     */
    public static String fomatLongDate(String date) {
        if (isNotEmpty(date)) {
            return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8) + " " + date.substring(8, 10) + ":" + date.substring(10, 12) + ":" + date.substring(12, 14);
        }
        return null;
    }

    /**
     * 格式化时间.
     *
     * @param date
     *            the date
     * @return the string
     */
    public static String fomatDateTime2String(String date) {
        if (isNotEmpty(date)) {
            return date.replace("-", "").replace("T", "").replace(":", "").replace(" ", "");
        }
        return null;
    }

    /**
     * 将时间字符串格式化成一个日期(java.util.Date)
     *
     * @param dateStr
     *            要格式化的日期字符串，如"2014-06-15 12:30:12"
     * @param formatStr
     *            格式化模板，如"yyyy-MM-dd HH:mm:ss"
     * @return the string
     */
    public static Date formatDateString2Date(String dateStr, String formatStr) {
        DateFormat dateFormat = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将时间字符串格式化成一个日期(java.util.Date)
     *
     */
    public static String formatDate2String(Date date, String formatStr) {
        DateFormat dateFormat = new SimpleDateFormat(formatStr);
        String result = null;
        try {
            result = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将一个毫秒数时间转换为相应的时间格式
     *
     * @param longSecond
     * @return
     */
    public static String formateDateLongToString(long longSecond) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(longSecond);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(gc.getTime());
    }

    /**
     * 格式化金额.
     *
     * @param value
     *            the value
     * @return the string
     */
    public static String formatCurrency2String(Long value) {
        if (value == null || "0".equals(String.valueOf(value))) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value / 100.00);
    }

    /**
     * 格式化金额.
     *
     * @param priceFormat
     *            the price format
     * @return the long
     */
    public static Long formatCurrency2Long(String priceFormat) {
        BigDecimal bg = new BigDecimal(priceFormat);
        Long price = bg.multiply(new BigDecimal(100)).longValue();
        return price;
    }

    /**
     * 获取当前时间.
     *
     * @return
     * @throws
     */
    public static String getToDayStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 获取当前时间当作文件名称
     *
     * @return
     */
    public static String getToDayStrAsFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }

    /**
     * 获取下一天.
     *
     * @param currentDate
     *            the current date
     * @return the next date str
     */
    public static String getNextDateStr(String currentDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = sdf.parse(currentDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        String nextDate = sdf.format(calendar.getTime());
        return nextDate;
    }

    /**
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String get16SFormatDate(Date date){
        String result  = "";
        if (date == null){
            return result;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (date != null){
            result = format.format(date);
        }
        return result;
    }

    /**
     * 获取上一天.
     *
     * @param currentDate
     *            the current date
     * @return the next date str
     * @throws ParseException
     *             the parse exception
     */
    public static String getYesterdayStr(String currentDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = sdf.parse(currentDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        String nextDate = sdf.format(calendar.getTime());
        return nextDate;
    }

    /**
     * 根据日期获取星期
     *
     * @param strdate
     * @return
     */
    public static String getWeekDayByDate(String strdate) {
        final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        try {
            date = sdfInput.parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0)
            dayOfWeek = 0;
        return dayNames[dayOfWeek];
    }

    /**
     * 生成固定长度的随机字符和数字
     *
     * @param len
     * @return
     */
    public static String generateRandomCharAndNumber(Integer len) {
        StringBuffer sb = new StringBuffer();
        for (Integer i = 0; i < len; i++) {
            int intRand = (int) (Math.random() * 52);
            int numValue = (int) (Math.random() * 10);
            char base = (intRand < 26) ? 'A' : 'a';
            char c = (char) (base + intRand % 26);
            if (numValue % 2 == 0) {
                sb.append(c);
            } else {
                sb.append(numValue);
            }
        }
        return sb.toString();
    }

    /**
     * 方法描述：将系统限定的路径转换为绝对正确的路径
     *
     * @param originalPath
     * @return
     */
    public static String getGeneralFilePath(String originalPath) {
        if ((null != originalPath) && !("".equals(originalPath))) {
            String strPath[] = originalPath.split("\\\\|/");
            originalPath = "";
            // 拼接jar路径
            for (int i = 0; i < strPath.length; i++) {
                if (!("".equals(strPath[i])) && !("".equals(strPath[i].trim()))) {
                    originalPath = originalPath + strPath[i].trim();
                    if (i < strPath.length - 1) {
                        originalPath = originalPath + File.separator;
                    }
                }
            }
        }
        return originalPath;
    }

    /**
     * 复制文件(以超快的速度复制文件)
     *
     * @param srcFile
     *            源文件File
     * @param destFile
     *            目标目录File
     */
    public static long copyFile(File srcFile, File destFile) throws Exception {
        long copySizes = 0;
        FileChannel fcin = new FileInputStream(srcFile).getChannel();
        FileChannel fcout = new FileOutputStream(destFile).getChannel();
        long size = fcin.size();
        fcin.transferTo(0, fcin.size(), fcout);
        fcin.close();
        fcout.close();
        copySizes = size;
        return copySizes;
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     *            被删除文件的文件名
     * @return 单个文件删除成功返回true,否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            file.delete();
            System.out.println("删除单个文件" + fileName + "成功！");
            return true;
        } else {
            System.out.println("删除单个文件" + fileName + "失败！");
            return false;
        }
    }

    /**
     * @Title: getRandomNumber
     * @Description: 获取随机数
     * @param count
     *            位数，如果是1就产生1位的数字，如果是2就产生2位数字，依次类推
     * @return
     */
    public static String getRandomNumber(int count) {
        String result = "";
        for (int i = 0; i < count; i++) {
            int rand = (int) (Math.random() * 10);
            result += rand;
        }
        return result;
    }

    public static void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }

    /***
     * 组合数组
     *
     * @param <T>
     * @param llist
     */
    public static <T> void comb(List<List<T>> llist, List<T> firstList, List<T> temp, List<List<T>> combList) {

        for (int i = 0; i < llist.size(); i++) {

            if (llist.indexOf(firstList) == i) {

                for (T l : firstList) {

                    List<T> comb = new ArrayList<T>(0);

                    for (T s : temp) {
                        comb.add(s);
                    }
                    comb.add(l);

                    if (i < llist.size() - 1) {

                        comb(llist, llist.get(i + 1), comb, combList);

                    } else if (i == llist.size() - 1) {

                        combList.add(comb);
                    }
                }
            }
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                try {
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 显示软键盘
     *
     * @param activity
     */
    public static void showKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (!imm.isActive()) {
                imm.showSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    /**
     * 当手机有虚拟键盘的时候，调用这个方法就可以获得手机的虚拟键盘高度
     *
     * @param poCotext
     * @return
     */
    public static int getVrtualBtnHeight(Context poCotext) {
        int location[] = getScreenWH(poCotext);
        int realHeiht = getDpi((Activity) poCotext);
        int virvalHeight = realHeiht - location[1];
        return virvalHeight;
    }

    public static int getDpi(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        int height = 0;
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            height = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return height;
    }

    public static int[] getScreenWH(Context poCotext) {
        WindowManager wm = (WindowManager) poCotext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return new int[] { width, height };
    }


    /**
     * 判断用户rom是否是miui的方法
     * @return
     */
    public static boolean isMiUi() {
        return !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"));
    }

    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            java.lang.Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }
}
