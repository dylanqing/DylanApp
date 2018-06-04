package com.formax.lib_wechatpay;

import java.io.Serializable;

/**
 * Created by Dylan on 2017/7/6.
 * 微信凋起支付订单所需参数bean
 */

public class WechatOrderBean implements Serializable {


    private static final long serialVersionUID = -3529080751919876979L;

    /*appid*/
    public String appId;

    /*商户号*/
    public String partnerid;

    /*预支付交易会话ID*/
    public String prepayid;

    /*扩展字段---暂填写固定值Sign=WXPay*/
    private String pgName = "Sign=WXPay";

    /*随机字符串*/
    public String noncestr;

    /*时间戳*/
    public String timestamp;

    /*签名*/
    public String sign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPgName() {
        return pgName;
    }

    private void setPgName(String pgName) {
        this.pgName = pgName;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "WechatOrderBean{" +
                "appId='" + appId + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", pgName='" + pgName + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
