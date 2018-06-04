package com.worldunion.dylanapp.unit.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.base.BaseMvpActivity;
import com.worldunion.dylanapp.unit.main.MainActivity;
import com.worldunion.dylanapp.unit.splash.view.SplashView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * @author Dylan
 * @time 2017/3/28 10:13
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class SplashActivity extends BaseMvpActivity implements SplashView {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void init() {
        Observable.timer(800, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void getData() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    /**
     * 第一次进入app 打开引导页
     */
    @Override
    public void goGuide() {
    }

    /**
     * 去登录页
     */
    @Override
    public void goLogin() {
    }

    /**
     * 去主界面
     */
    @Override
    public void goMain() {
    }

    @Override
    public void updateVersion(String url, String desc) {

    }

    @Override
    public void forecdUpdate(String url, String desc) {

    }
}
