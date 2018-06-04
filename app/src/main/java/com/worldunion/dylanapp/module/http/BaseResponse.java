package com.worldunion.dylanapp.module.http;

import java.io.Serializable;

/**
 * 后台数据返回的公共部分
 * Created by 0169670 on 2017/1/9.
 */
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = -8792527631913866118L;
    public String code;
    public String message;
    public T data;
}