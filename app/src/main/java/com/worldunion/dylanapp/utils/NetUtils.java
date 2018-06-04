package com.worldunion.dylanapp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.dylan.baselib.utils.log.KLog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.Observable;

/**
 * 网络请求 工具类
 */
public class NetUtils {

    /**
     * 登入后获取的 账户唯一标示 用于登入
     **/
    public static String token;
    /**
     *
     */
    public static String appkey;
    /**
     * app版本名称
     **/
    public static String versionName;
    /*版本号*/
    public static int versionCode;
    /**
     * 来源 Android、ios、微信
     **/
    public static String deviceType;
    /**
     * 设备标识
     **/
    public static String deviceId;

    public static Context mCotext;

    /**
     * 初始化请求头中几个参数
     */
    public static void init(Context content) {
        //deviceId = getDevice(content);
        versionName = getVersionName(content);
        versionCode = getVersionCode(content);
        appkey = "jike";
        deviceType = "android";
        mCotext = content;
    }

    /**
     * 生成加密sign
     *
     * @param params
     * @return
     */
    public static String getSign(Map<String, String> params) {
        // 参数key 排序
        String sortKey = sortMapByKey(params);
        String md5Sign = md5(sortKey);//MD5加密
        return md5Sign;
    }

    /**
     * map 排序(参数map 排序)
     *
     * @param oriMap
     * @return 返回 排序后的key 拼接字符串
     */
    private static String sortMapByKey(Map<String, String> oriMap) {
        String[] arrayToSort = new String[oriMap.size()];
        int i = 0;
        for (String key : oriMap.keySet()) {
            if (key != null) {
                arrayToSort[i] = key;
            }
            i++;
        }
        Arrays.sort(arrayToSort);
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < oriMap.size(); j++) {
            sb.append(arrayToSort[j] + oriMap.get(arrayToSort[j]));
        }
        String resultString = sb.toString().replaceAll("\\}, \\{", "},{");
        KLog.d("Heads sortMapByKey= " + resultString);
        return resultString;
    }

    /**
     * 获取设备唯一标识
     *
     * @return 设备唯一标识字符串
     */
    private static String getDevice(Context mContext) {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String DEVICE_ID = tm.getDeviceId();
        if (TextUtils.isEmpty(DEVICE_ID)) {
            // 找不到的情况下 默认一个值
            DEVICE_ID = "1234567890";
        }
        return DEVICE_ID;
    }

    /**
     * 获取版本名称
     *
     * @return 当前应用的版本号
     */
    private static String getVersionName(Context mContext) {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    private static int getVersionCode(Context mContext) {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isNetConnection(Context context) {
        if (context == null)
            return false;
        ConnectivityManager mConnMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnMgr == null)
            return false;
        NetworkInfo aActiveInfo = mConnMgr.getActiveNetworkInfo();
        // 获取活动网络连接信息
        if (aActiveInfo != null) {
            return true;
        }
        return false;
    }

    /**
     * 使用md5的算法进行加密
     *
     * @param plainText
     * @return
     * @updateTime 2015-6-22,下午2:44:29
     * @updateAuthor troy
     */
    public static String md5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

}
