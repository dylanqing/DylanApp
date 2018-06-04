package com.formax.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;

import java.util.Map;


/**
 * Created by Dylan on 2017/7/13.
 * 支付宝支付
 */

public class AliPay {
    private static final int SDK_PAY_FLAG = 1;

    private static volatile AliPay instance;

    private AliPay() {
    }

    public static AliPay getInstance() {
        if (instance == null) {
            synchronized (AliPay.class) {
                if (instance == null) {
                    instance = new AliPay();
                }
            }
        }
        return instance;
    }

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    if (listener != null) {
                        listener.payResult(payResult.getResult(), payResult.getResultStatus());
                    }
                    break;
                }
                default:
                    /*错误*/
                    if (listener != null) {
                        listener.payResult("-1", "-1");
                    }
                    break;
            }
        }
    };


    /**
     *
     返回码 	含义
     9000 	订单支付成功
     8000 	正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
     4000 	订单支付失败
     5000 	重复请求
     6001 	用户中途取消
     6002 	网络连接出错
     6004 	支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
     其它 	其它支付错误
     */

    /**
     * 发起支付
     *
     * @param activity
     * @param orderInfo 订单str串
     */
    public void pay(final Activity activity, final String orderInfo) {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 是否使用沙箱环境，在支付接口前调用如下方法
     *
     * @param useSandBox
     */
    public void setUseSandBox(boolean useSandBox) {
        if (useSandBox) {
            /*APP支付只支持Android版接入，在使用sdk时，在支付接口前调用如下方法，使用沙箱环境，默认使用正式环境*/
            EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        }
    }


    private OnPayResultListener listener;

    public void setOnPayResultListener(OnPayResultListener ls) {
        listener = ls;
    }

    /**
     * 回调接口，code:支付结果状态码
     */
    public interface OnPayResultListener {
        void payResult(String result, String resultStatus);
    }

    public class PayResult {
        private String resultStatus;
        private String result;
        private String memo;

        public PayResult(Map<String, String> rawResult) {
            if (rawResult == null) {
                return;
            }

            for (String key : rawResult.keySet()) {
                if (TextUtils.equals(key, "resultStatus")) {
                    resultStatus = rawResult.get(key);
                } else if (TextUtils.equals(key, "result")) {
                    result = rawResult.get(key);
                } else if (TextUtils.equals(key, "memo")) {
                    memo = rawResult.get(key);
                }
            }
        }

        @Override
        public String toString() {
            return "resultStatus={" + resultStatus + "};memo={" + memo
                    + "};result={" + result + "}";
        }

        /**
         * @return the resultStatus
         */
        public String getResultStatus() {
            return resultStatus;
        }

        /**
         * @return the memo
         */
        public String getMemo() {
            return memo;
        }

        /**
         * @return the result
         */
        public String getResult() {
            return result;
        }
    }

}
