package com.worldunion.dylanapp.unit.heartbeat;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.dylan.baselib.utils.log.KLog;

/**
 * Created by wangxiaoqing on 2017/10/24.
 */

public class TestService extends Service {

    private String TAG = "TestService";

    @Override
    public void onCreate() {
        KLog.d(TAG, "onCreate");
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KLog.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        KLog.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
