package com.worldunion.dylanapp.module.http;

import android.text.TextUtils;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;
import com.worldunion.dylanapp.utils.NetUtils;
import com.worldunion.dylanapp.utils.SPUtils;
import com.dylan.baselib.utils.log.KLog;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Response;

/**
 * 处理返回数据并序列化为对应的Bean
 * 请求前header签名 与 回调后业务逻辑的处理
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {

    private String TAG = "JsonCallback";

    /**
     * 请求成功
     **/
    private final String CODE_SUCCESS = "0001";
    /**
     * 警告
     **/
    private final String CODE_WARN = "0002";
    /**
     * 错误
     **/
    private final String CODE_ERRER = "0003";
    /**
     * 会话失效重新登入
     **/
    private final String CODE_LOSS_SESSION = "0004";
    /**
     * 非法请求，签名失败
     **/
    private final String CODE_SIGN_FAILURE = "0005";

    /**
     * 主要用于在所有请求之前添加公共的请求头或请求参数
     *
     * @param request
     */
    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        //加密参数
        Map headers = new HashMap<String, String>();
        headers.put("appkey", NetUtils.appkey);
        headers.put("deviceId", NetUtils.deviceId);
        headers.put("deviceType", NetUtils.deviceType);
        headers.put("timestamp", System.currentTimeMillis() + "");//时间戳
        headers.put("version", NetUtils.versionName);
        String token = SPUtils.getStringValue(SPUtils.LOGIN_TOKEN, "");
        if (!TextUtils.isEmpty(token)) {
            headers.put("token", token);
        }
        String sign = NetUtils.getSign(headers);//字典排序所有headers然后MD5加密

        Iterator<Map.Entry<String, String>> iter = headers.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            String key = entry.getKey();
            String value = entry.getValue();
            request.headers(key, value);//设置6种header
        }
        request.headers("sign", sign);//设置秘钥header

        KLog.d(TAG, "url = " + request.getUrl());
        KLog.d(TAG, "params: " + request.getParams().toString());
//      KLog.d(TAG, "sign = " + sign + " headers = " + headers.toString());
    }


    /**
     * 处理与后台协定的业务逻辑(子线程处理，不能做ui相关的工作)
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     */
    @Override
    public T convertSuccess(Response response) throws Exception {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
        Type rawType = ((ParameterizedType) type).getRawType();
        Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];

//      JsonReader jsonReader = new JsonReader(response.body().charStream());
        /* OKHTTP的坑 response.body().string()使用之后 流就关闭 所以用String 缓存下来 */
        String body = response.body().string();//JsonReader估计是因为流只能被使用一次 所以换成String
//        KLog.d(TAG, "response.body() = " + body);
        KLog.json(TAG, body);
        if (typeArgument == Void.class || rawType == BaseResponse.class || rawType == ListResponse.class) {
            //无数据类型,表示没有data的对象 用来判断业务逻辑返回的状态码
            DataBean voidBean = Convert.fromJson(body, DataBean.class);
            String code = voidBean.code;
            String message = voidBean.message;
            switch (code) {
                case CODE_SUCCESS:
                    if (typeArgument == Void.class) {  //无数据类型
                        response.close();
                        return (T) voidBean.toResponse();
                    } else if (rawType == BaseResponse.class) {
                        //有数据类型，表示有data
                        BaseResponse baseResponse = Convert.fromJson(body, type);
                        response.close();
                        return (T) baseResponse;
                    } else if (rawType == ListResponse.class) {
                        //有列表数据类型，(rows数组，total 总数)
                        ListResponse listResponse = Convert.fromJson(body, type);
                        response.close();
                        return (T) listResponse;
                    } else {
                        response.close();
                        throw new IllegalStateException("基类错误无法解析!");
                    }
                case CODE_WARN: //0002警告
                    throw new IllegalStateException(message);
                case CODE_ERRER: //0003错误
                    throw new IllegalStateException(message);
                case CODE_LOSS_SESSION: //0004会话失效
                /* 通知MainActivity执行重新登录逻辑 */
//                    EventBus.getDefault().post(new ReloginEvent());
                    throw new IllegalStateException(message);
                case CODE_SIGN_FAILURE: //0005签名失败
                /* 通知MainActivity执行重新登录逻辑 */
//                    EventBus.getDefault().post(new ReloginEvent());
                    throw new IllegalStateException(message);
                default:                 //其他错误
                    throw new IllegalStateException("错误代码：" + code + "，错误信息：" + message);
            }
        } else {
            response.close();
            throw new IllegalStateException("基类错误无法解析!");
        }
    }

}