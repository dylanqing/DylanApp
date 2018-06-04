package com.worldunion.dylanapp.unit.heartbeat;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.AppUtils;
import com.dylan.baselib.utils.log.KLog;
import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.base.BaseMvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangxiaoqing on 2017/10/24.
 */

public class TestServiceActivity extends BaseMvpActivity {

    @BindView(R.id.btnService)
    Button mBtnService;

    private PendingIntent heartbeatPendingIntent;
    private int time = 5000;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_test_service;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initListener() {
        super.initListener();
        mBtnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager am = (AlarmManager) getApplication().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent("com.formax.heartbeatIntent");
                intent.setClass(getApplicationContext(), TestService.class);
                heartbeatPendingIntent = PendingIntent.getService(getApplicationContext(), 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);
                try {
                    am.cancel(heartbeatPendingIntent);
                    am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + time, heartbeatPendingIntent);
                } catch (Exception e) {
                    KLog.e("Exception", "printStackTrace()--->", e);
                }
            }
        });
    }

    @Override
    protected void init() {

    }

    @Override
    protected void getData() {

    }


    public static void launch(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, TestServiceActivity.class);
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        }
    }
}
