package com.worldunion.dylanapp.module.http;

import java.io.Serializable;
import java.util.List;

/**
 * 后台数据返回的List
 * Created by 0169670 on 2017/1/9.
 */
public class ListResponse<T> implements Serializable {

    private static final long serialVersionUID = -3557322440491072522L;

    public String code;
    public String message;
    public DataList<T> data;

    public class DataList<T> {
        public List<T> rows;
        public int total;
    }

}