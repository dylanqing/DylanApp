package com.worldunion.dylanapp.unit.okhttp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.dylan.baselib.utils.log.KLog;
import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.base.BaseMvpActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangxiaoqing on 2017/11/7.
 * okhttp页面
 */

public class OkHttpActivity extends BaseMvpActivity {

    @BindView(R.id.tvResult)
    TextView mTvResult;
    private String url = "http://gank.io/api/search/query/listview/category/Android/count/10/page/1";
    private String imageUrl = "http://lorempixel.com/400/400/";
    private OkHttpClient mClient;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_okhttp;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void init() {
        mClient = new OkHttpClient();
    }

    @Override
    protected void getData() {

    }

    public static void launch(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, OkHttpActivity.class);
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        }
    }


    @OnClick(R.id.btnRequest)
    public void onViewClicked() {
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                KLog.d(TAG, "onFailure");
                mTvResult.setText("onFailure = " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
//                KLog.d(TAG, "response = " + result);
                KLog.d(TAG, "response = " + Thread.currentThread().getId());
                Observable.just(result)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(@NonNull String s) throws Exception {
                                mTvResult.setText(s);
                            }
                        });
            }
        });

    }

    public class MyIntercepter implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            Request build = builder.build();
            Response proceed = chain.proceed(build);
            proceed.body().string();


            return null;
        }
    }
}
