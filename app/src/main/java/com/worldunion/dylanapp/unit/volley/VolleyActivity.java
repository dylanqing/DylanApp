package com.worldunion.dylanapp.unit.volley;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dylan.baselib.utils.log.KLog;
import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.base.BaseMvpActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangxiaoqing on 2017/10/26.
 */

public class VolleyActivity extends BaseMvpActivity {


    @BindView(R.id.btnStringRequest)
    Button mBtnStringRequest;
    @BindView(R.id.ivImage)
    ImageView mIvImage;

    private String url = "http://gank.io/api/search/query/listview/category/Android/count/10/page/1";
    private String imageUrl = "http://lorempixel.com/400/400/";
    private RequestQueue mRequestQueue;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_volley;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void init() {
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    @Override
    protected void getData() {

    }

    @Override
    public void initListener() {
        super.initListener();
    }

    public static void launch(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, VolleyActivity.class);
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        }
    }

    @OnClick({R.id.btnStringRequest, R.id.btnJsonRequest, R.id.btnImageRequest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnStringRequest:
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        KLog.d(TAG, "StringRequest result = " + s);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        KLog.d(TAG, "StringRequest onErrorResponse= " + volleyError.getMessage());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("params1", "value1");
                        map.put("params2", "value2");
                        return map;
                    }
                };
                mRequestQueue.add(stringRequest);
                break;
            case R.id.btnJsonRequest:
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        KLog.json(jsonObject.toString());
                        KLog.d(TAG, "JsonObjectRequest jsonObject = " + jsonObject.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        KLog.d(TAG, "JsonObjectRequest onErrorResponse = " + volleyError.getMessage());
                    }
                });
                mRequestQueue.add(jsonObjectRequest);
                break;
            case R.id.btnImageRequest:
                ImageRequest imageRequest = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        mIvImage.setImageBitmap(bitmap);
                    }
                }, 400, 400, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        KLog.d(TAG, "ImageRequest onErrorResponse = " + volleyError.getMessage());
                    }
                });
                mRequestQueue.add(imageRequest);
                break;
            default:

                break;
        }
    }
}
