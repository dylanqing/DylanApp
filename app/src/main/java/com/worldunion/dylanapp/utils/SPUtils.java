package com.worldunion.dylanapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * sp工具类
 */
public class SPUtils {

    public static SharedPreferences mSp;
    private static SPUtils instance;

    /*第一次打开APP 引导页*/
    public static String FRIST_OPEN = "frist_open";

    /*登录token值*/
    public static String LOGIN_TOKEN = "login_token";


    public static SPUtils getInstance() {
        if (instance == null) {
            instance = new SPUtils();
        }
        return instance;
    }

    /**
     * 初始化sp
     *
     * @param context 上下文
     * @param name    sp文件名称
     * @param mode    模式
     */
    public static void init(Context context, String name, int mode) {
        SharedPreferences sp = context.getSharedPreferences(name, mode);
        if (mSp == null) {
            mSp = sp;
        }
    }

    /**
     * 保存String
     *
     * @param key
     * @param value
     */
    public static void setStringValue(String key, String value) {
        if (mSp == null) {
            return;
        }
        Editor editor = mSp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获得String类型
     *
     * @param key
     * @return
     */
    public static String getStringValue(String key, String defValue) {
        if (mSp == null) {
            return null;
        }

        return mSp.getString(key, defValue);
    }

    /**
     * 存储boolean类型数据
     *
     * @param key
     * @param value
     */
    public static void setBooleanValue(String key, boolean value) {
        if (mSp == null) {
            return;
        }
        Editor editor = mSp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获得Boolean类型数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBooleanValue(String key, boolean defValue) {
        if (mSp == null) {
            return false;
        }
        return mSp.getBoolean(key, defValue);
    }

    /**
     * 保存int类型
     *
     * @param key
     * @param value
     */
    public static void setIntValue(String key, int value) {
        if (mSp == null) {
            return;
        }
        Editor editor = mSp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 获得int类型
     *
     * @param key
     * @param defValue
     * @return
     */
    public static int getIntValue(String key, int defValue) {
        if (mSp == null) {
            return -1;
        }
        return mSp.getInt(key, defValue);
    }

    /**
     * SharedPreferences 对象数据初始化
     *
     * @param key
     * @param object
     */
    public static void setObjectValue(String key, Object object) {
        if (mSp == null) {
            return;
        }
        String objectBase64 = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            objectBase64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Editor editor = mSp.edit();
        editor.putString(key, objectBase64);
        editor.commit();
    }

    /**
     * SharedPreferencesȡ 获取对象
     *
     * @param key
     * @return
     */
    public static Object getObjectValue(String key) {
        if (mSp == null) {
            return null;
        }
        Object object = null;
        try {
            String objectBase64 = mSp.getString(key, "");
            byte[] base64Bytes = Base64.decode(objectBase64.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            object = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    // 清空数据
    public void clearSharePreference() {
        if (mSp == null) {
            return;
        }
        mSp.edit().clear().commit();
    }

    /**
     * 删除指定sp
     *
     * @param key
     */
    public static void clearSharePreferencrForName(String key) {
        if (mSp == null) {
            return;
        }
        mSp.edit().remove(key).commit();
    }

}
