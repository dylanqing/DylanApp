package com.worldunion.dylanapp.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.dylan.baselib.utils.AppManager;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.lzy.okgo.OkGo;
import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.db.base.DatabaseHelper;
import com.worldunion.dylanapp.module.http.HttpProgress;
import com.worldunion.dylanapp.base.presenter.BasePresenter;
import com.worldunion.dylanapp.utils.StatusBarUtil;
import com.worldunion.dylanapp.utils.UMengUtils;
import com.worldunion.dylanapp.base.view.MvpView;
import com.worldunion.dylanapp.widget.LoadingLayout;
import com.worldunion.dylanapp.widget.title.TitleView;

import butterknife.ButterKnife;

/**
 * @author Dylan
 * @time 2017/2/24 15:12
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public abstract class BaseMvpActivity extends FragmentActivity implements MvpView {

    public final String TAG = getClass().getSimpleName();
    protected Context mContext = BaseMvpActivity.this;
    private DatabaseHelper databaseHelper = null;
    /**
     * 标题
     */
    protected TitleView mTitleView;
    /**
     * 加载错误处理的页面
     */
    protected LoadingLayout mLoadingLayout;

    protected BasePresenter presenter;

    /**
     * waitDialog.
     */
    private HttpProgress mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 固定竖屏(禁止横屏)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        View view = View.inflate(BaseMvpActivity.this, getContentViewId(), null);
        setContentView(view);
        ButterKnife.bind(this);
        setStatusBar(view);

        mTitleView = (TitleView) findViewById(R.id.titleBar);
        mLoadingLayout = (LoadingLayout) findViewById(R.id.loading_layout);
        AppManager.getAppManager().addActivity(this);
        initPresenter();
        init();
        initListener();
        initEvent();
        getData();
    }

    /**
     * 布局xml id
     */
    protected abstract int getContentViewId();

    protected void setStatusBar(View view) {
        StatusBarUtil.setColor(BaseMvpActivity.this, getResources().getColor(R.color.main_color));
    }

    /**
     * 初始化逻辑处理层
     */
    public abstract void initPresenter();

    /**
     * 初始化数据,设置数据
     */
    protected abstract void init();

    /**
     * 获取网络数据
     */
    protected abstract void getData();

    public void initListener() {
        if (mTitleView != null) {
            mTitleView.setOnLeftViewListener(new TitleView.OnLeftViewListener() {
                @Override
                public void onLeftViewonClick(View v) {
                    finish();
                }
            });
        }
    }


    public void initEvent() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
        AppManager.getAppManager().finishActivity(this);
    }


    public DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void showToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int id) {
        Toast.makeText(this, getString(id), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        //网络请求前显示对话框
        mLoadingDialog = HttpProgress.show(this, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
    }

    @Override
    public void hideLoading() {
        //网络请求结束后关闭对话框
        if (mLoadingDialog != null && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UMengUtils.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UMengUtils.onPause(this);
    }


}
