package com.worldunion.dylanapp.unit.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by wangxiaoqing on 2017/10/26.
 */

public class GsonRequest<T> extends Request<T> {

    private Response.Listener mResListener;
    private Gson mGson;
    private Class<T> mClass;

    public GsonRequest(int method, Class<T> clazz, String url, Response.Listener resListener, Response.ErrorListener listener) {
        super(method, url, listener);
        mGson = new Gson();
        mClass = clazz;
        mResListener = resListener;
    }

    public GsonRequest(Class<T> clazz, String url, Response.Listener resListener, Response.ErrorListener listener) {
        this(Method.GET, clazz, url, resListener, listener);
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        String parsed;
        try {
            parsed = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
        } catch (UnsupportedEncodingException var4) {
            return Response.error(new ParseError(var4));
        }

        return Response.success(mGson.fromJson(parsed, mClass), HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    @Override
    protected void deliverResponse(T t) {
        mResListener.onResponse(t);
    }
}
