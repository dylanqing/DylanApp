package com.formax.lib_wechatpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

/**
 * Created by Dylan on 2017/7/25.
 * 微信支付回调页面基类，在主工程的WXPayEntryActivity类中继承这个类
 * 处理支付的回调结果
 */

public abstract class WechatPayBaseActivity extends Activity implements IWXAPIEventHandler {


    public abstract void init();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }


}
