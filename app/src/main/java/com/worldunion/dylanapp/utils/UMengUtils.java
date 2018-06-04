package com.worldunion.dylanapp.utils;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

/**
 * @author Dylan
 * @time 2016/12/28 10:17
 * @des 友盟统计工具类
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class UMengUtils {

    /**
     * 初始化
     *
     * @param context
     */
    public static void initUMeng(Context context) {
        /*友盟统计---普通统计场景类型*/
        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    /**
     * 调试模式
     *
     * @param mode
     */
    public static void debugMode(boolean mode) {
        /*友盟统计调试模式*/
        //MobclickAgent.setDebugMode(true);
    }

    /**
     * onResume
     *
     * @param context
     */
    public static void onResume(Context context) {
        MobclickAgent.onResume(context);
    }

    /**
     * onPause
     *
     * @param context
     */
    public static void onPause(Context context) {
        MobclickAgent.onPause(context);
    }
}
