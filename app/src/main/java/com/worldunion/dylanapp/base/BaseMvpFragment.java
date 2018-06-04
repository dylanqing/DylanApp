package com.worldunion.dylanapp.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.lzy.okgo.OkGo;
import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.db.base.DatabaseHelper;
import com.worldunion.dylanapp.module.http.HttpProgress;
import com.worldunion.dylanapp.base.view.MvpView;
import com.worldunion.dylanapp.widget.LoadingLayout;
import com.worldunion.dylanapp.widget.title.TitleView;

import butterknife.ButterKnife;

/**
 * @author Dylan
 * @time 2017/2/24 15:15
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public abstract class BaseMvpFragment extends Fragment implements MvpView {

    protected Context mContext;
    protected Activity mActivity;
    protected View mRootView;
    /**
     * waitDialog.
     */
    private HttpProgress mWaitDialog;

    /**
     * 标题
     */
    protected TitleView mTitleView;
    /**
     * 加载错误处理的页面
     */
    protected LoadingLayout mLoadingLayout;

    private DatabaseHelper databaseHelper = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mActivity = getActivity();

        mRootView = inflater.inflate(getContentViewId(), container, false);
        ButterKnife.bind(this, mRootView);

        mTitleView = (TitleView) mRootView.findViewById(R.id.titleBar);
        mLoadingLayout = (LoadingLayout) mRootView.findViewById(R.id.loading_layout);

        initPresenter();
        init();
        initListener();
        initEvent();
        getData();

        return mRootView;
    }

    public abstract int getContentViewId();

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
    }

    public void initEvent() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    public DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(mContext, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void showToast(String data) {
        Toast.makeText(mActivity, data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int id) {
        Toast.makeText(mActivity, getString(id), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        //网络请求前显示对话框
        mWaitDialog = HttpProgress.show(mActivity, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
    }

    @Override
    public void hideLoading() {
        //网络请求结束后关闭对话框
        if (mWaitDialog != null && mWaitDialog.isShowing())
            mWaitDialog.dismiss();
    }



}
