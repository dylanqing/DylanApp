package com.worldunion.dylanapp.module.http;

/**
 * 通用类型的数据解析 一般处理没有data的回调（比如修改成功失败 登出操作 的返回信息)
 * Created by 0169670 on 2017/1/9.
 */
public class DataBean {// {code:"",data:"",message:""}
    /**
     * 返回成功类型值
     */
    public String code;
    /**
     * 返回信息
     */
    public String message;

    public BaseResponse toResponse() {
        BaseResponse response = new BaseResponse();
        response.code = code;
        response.message = message;
        return response;
    }

}