package com.worldunion.dylanapp.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.Utils;
import com.dylan.baselib.utils.log.KLog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.worldunion.dylanapp.bean.UserBean;
import com.worldunion.dylanapp.module.constant.SPConstant;
import com.worldunion.dylanapp.utils.CommonUtils;
import com.worldunion.dylanapp.utils.NetUtils;
import com.worldunion.dylanapp.utils.SPUtils;
import com.worldunion.dylanapp.utils.UMengUtils;
//import com.dylan.baselib.utils.log.KLog;

import java.util.List;

/**
 * @author Dylan
 * @time 2017/2/24 15:12
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class BaseApplication extends Application {

    public static Application instantce;
    public static UserBean userBean;

    @Override
    public void onCreate() {
        super.onCreate();
        ////多进程导致 application多次初始化 所以判断进程名称进行判断
        //String curProcess = getProcessName(this, android.os.Process.myPid());
        //if (!getPackageName().equals(curProcess)) {
        //    KLog.i(TAG, "not'Main Process:"+curProcess);
        //    return;
        //}
        instantce = this;
        /*LOG日志初始化*/
        KLog.init(true,"KLog");
        //初始化网络请求头中的参数
        NetUtils.init(this);
        //网络框架初始化操作
        initializeOkGo();
        //初始化图片框架
        //ImageUtils.init(this);

        // 初始化百度定位sdk
        //SDKInitializer.initialize(getApplicationContext());
        /*全局异常捕获*/
        Utils.init(this);
        CrashUtils.init();
        //初始化sp缓存
        SPUtils.init(this, SPConstant.SHAREPREFRENCE_NAME, MODE_PRIVATE);
        //启动数据同步服务
        //startService(new Intent(this, SynDataService.class));
        /*友盟统计---普通统计场景类型*/
        UMengUtils.initUMeng(this);
        /*友盟统计调试模式*/
        UMengUtils.debugMode(true);
        UserBean bean = (UserBean) SPUtils.getObjectValue(SPConstant.LOGIN_INFO);
        if (CommonUtils.isNotEmpty(bean)) {
            userBean = bean;
        }
    }

    /**
     * 全局的网络设置
     */
    private void initializeOkGo() {
        OkGo.init(this);
        OkGo.getInstance()
                //                .debug("OkGo", Level.INFO, true)
                .setConnectTimeout(10000)  //全局的连接超时时间默认的 60秒
                .setReadTimeOut(10000)     //全局的读取超时时间
                .setWriteTimeOut(10000)    //全局的写入超时时间
                .setCacheMode(CacheMode.NO_CACHE) //缓存模式
                .setRetryCount(0);          //重试次数
    }

    public static Application getInstance() {
        return instantce;
    }

    public static String getProcessName(Context context, int pid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

}
