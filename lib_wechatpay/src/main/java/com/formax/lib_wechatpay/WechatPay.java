package com.formax.lib_wechatpay;

import android.content.Context;
import android.util.Log;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


/**
 * Created by Dylan on 2017/7/6.
 * 微信支付---
 */

public class WechatPay {


    private String TAG = "WechatPay";
    private static WechatPay instance;
    private IWXAPI mWxApi;

    /*成功*/
    public static final int SUCCESS = 0;

    /*错误：可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等*/
    public static final int ERROR = -1;

    /*无需处理。发生场景：用户不支付了，点击取消，返回APP。*/
    public static final int CANCEL = -2;


    private WechatPay() {
    }

    public static WechatPay getInstance() {
        if (instance == null) {
            synchronized (WechatPay.class) {
                if (instance == null) {
                    instance = new WechatPay();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化微信支付
     *
     * @param context
     * @param appid   微信提供的APP_ID
     */
    public void init(Context context, String appid) {
        mWxApi = WXAPIFactory.createWXAPI(context, appid, false);
        // 将该app注册到微信
        mWxApi.registerApp(appid);
    }


    /**
     * 检查是安装微信客户端和是否支持微信支付
     *
     * @return
     */
    public boolean checkWxClient() {
        if (mWxApi != null) {
            return mWxApi.isWXAppInstalled() && mWxApi.isWXAppSupportAPI();
        }
        return false;
    }

    /**
     * 发起支付
     *
     * @param bean 参数bean
     */
    public void pay(WechatOrderBean bean) {
        if (!checkWxClient()) {
            return;
        }
        if (bean == null) {
            return;
        }
        if (mWxApi == null) {
            return;
        }
        PayReq req = new PayReq();
        req.appId = bean.getAppId();
        req.partnerId = bean.getPartnerid();
        req.prepayId = bean.getPrepayid();
        req.nonceStr = bean.getNoncestr();
        req.timeStamp = bean.getTimestamp();
        req.packageValue = bean.getPgName();
        req.sign = bean.getSign();

        boolean b = mWxApi.sendReq(req);
        Log.d(TAG, "mWxApi.sendReq(payReq) = " + b);
    }

}
