package com.worldunion.dylanapp.unit.my;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.module.scheme.IntentUtil;
import com.worldunion.dylanapp.unit.clearedittext.EditInputActivity;
import com.worldunion.dylanapp.unit.contact.ContactActivity;
import com.worldunion.dylanapp.unit.flexlistview.FlexListViewActivity;
import com.worldunion.dylanapp.unit.contact.LoadContactActivity;
import com.worldunion.dylanapp.unit.heartbeat.TestServiceActivity;
import com.worldunion.dylanapp.unit.pulltorefresh.PtrActivity;
import com.worldunion.dylanapp.base.BaseMvpFragment;
import com.worldunion.dylanapp.bean.UserBean;
import com.worldunion.dylanapp.unit.my.presenter.MyPresenter;
import com.worldunion.dylanapp.unit.qrscan.QRScanActivity;
import com.worldunion.dylanapp.unit.test.TouchActivity;
import com.worldunion.dylanapp.unit.my.view.MyView;
import com.worldunion.dylanapp.unit.volley.VolleyActivity;
import com.worldunion.dylanapp.widget.CommonDialog;
import com.worldunion.dylanapp.widget.SelectTimeDialog;
import com.worldunion.dylanapp.widget.TimeCount;
import com.worldunion.dylanapp.widget.choosedialog.ChooseDialog;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class MyFragment extends BaseMvpFragment implements MyView {


    @BindView(R.id.tvCount)
    TextView mTvCount;
    @BindView(R.id.tvText)
    TextView mTvText;
    private CommonDialog mCommonDialog;
    private MyPresenter mPresenter;
    private TimeCount mTimeCount;
    private SelectTimeDialog mSelectTimeDialog;
    private String dealDate;


    @Override
    public int getContentViewId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initPresenter() {
        mPresenter = new MyPresenter(mActivity);
        mPresenter.attachView(this);
    }

    @Override
    protected void init() {
        //StatusBarUtil.setTranslucentForImageViewInFragment(mActivity, null);
        mTimeCount = new TimeCount(60000, 1000, mTvCount);
    }

    @Override
    protected void getData() {

    }


    @OnClick({R.id.rlCommonDialog, R.id.rlLogin, R.id.rlCount, R.id.rlBlur, R.id.rlTimeSelect,
            R.id.rlPulltorefresh, R.id.rlChangeHead, R.id.rlLoadcontact, R.id.rlEditInput,
            R.id.rlFlexListView, R.id.rlTouchView, R.id.rlLoadcontactActivity, R.id.rlTestService,
            R.id.rlVolleyActivity, R.id.rlOkhttpActivity, R.id.rlQRScanActivity,R.id.rlTimeSelect2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlCommonDialog:
                /*通用对话框*/
                mCommonDialog = new CommonDialog();
                mCommonDialog.init(mActivity, "title", "desc", "取消", "确定");
                mCommonDialog.show(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShort("确定");
                        mCommonDialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShort("取消");
                        mCommonDialog.dismiss();
                    }
                });
                break;
            case R.id.rlLogin:
                /*登录*/
                mPresenter.login("19999999999", "123.com");
                break;
            case R.id.rlBlur:
                /*高斯模糊*/
                IntentUtil.forward(mActivity, "dylan://okhttpActivity");
                break;
            case R.id.rlTimeSelect:
                /*时间选择器*/
                /*成交日期*/
                if (mSelectTimeDialog == null) {
                    /*初始化当前时间*/
                    mSelectTimeDialog = new SelectTimeDialog();
                    mSelectTimeDialog.init(mActivity, TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd")));
                    mSelectTimeDialog.setOnWheelSelectListener(new SelectTimeDialog.OnWheelSelectListener() {
                        @Override
                        public void onSelect(String year, String month, String day) {
                            dealDate = year + "-" + month + "-" + day;
                        }
                    });
                }
                mSelectTimeDialog.show(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTvText.setText(dealDate);
                        mSelectTimeDialog.dismiss();
                    }
                });
                break;
            case R.id.rlTimeSelect2:
                /*时间选择器2*/
                String[] array = new String[10];
                for (int i = 0; i < 10; i++) {
                    array[i] = "第" + i + "条数据";
                }
                ChooseDialog dialog = new ChooseDialog.Builder(getActivity())
                        .setItems(array)
                        .setCurrItemIndex(3)
                        .setVisibleItems(3)
                        .setCyclic(false)
                        .setOnEventListener(new ChooseDialog.OnEventListener() {

                            @Override
                            public void onCheck(int index) {
                                ToastUtils.showShort("第" + index + "条数据被点击了");
                            }
                        }).create();
                dialog.show();
                break;
            case R.id.rlPulltorefresh:
                /*pulltorefresh*/
                Intent intent = new Intent(mActivity, PtrActivity.class);
                startActivity(intent);
                break;
            case R.id.rlLoadcontact:
                /*导入联系人*/
                Intent contactIntent = new Intent(mActivity, LoadContactActivity.class);
                startActivity(contactIntent);
                break;
            case R.id.rlChangeHead:
                /*头像选择*/
                IntentUtil.forward(mActivity, "dylan://changeHead");
                break;
            case R.id.rlEditInput:
                /*EditInput*/
                Intent editIntent = new Intent(mActivity, EditInputActivity.class);
                startActivity(editIntent);
                break;
            case R.id.rlFlexListView:
                /*FlexListView*/
                Intent flexIntent = new Intent(mActivity, FlexListViewActivity.class);
                startActivity(flexIntent);
                break;
            case R.id.rlTouchView:
                /*TouchView*/
                Intent touchIntent = new Intent(mActivity, TouchActivity.class);
                startActivity(touchIntent);
                break;
            case R.id.rlLoadcontactActivity:
                //ContactActivity.(mActivity);
                break;
            case R.id.rlTestService:
                /*测试service*/
                TestServiceActivity.launch(mActivity);
            case R.id.rlVolleyActivity:
                /*volleyActivity*/
                VolleyActivity.launch(mActivity);
                break;
            case R.id.rlOkhttpActivity:
                /*okhttpactivity*/
                IntentUtil.forward(mActivity, "dylan://okhttpActivity");
                break;
            case R.id.rlQRScanActivity:
                /*二维码扫描Activity*/
                QRScanActivity.launch(mActivity);
                break;
            case R.id.rlCount:
                /*倒计时*/
                mTimeCount.start();
                break;
            default:
                break;
        }
    }

    @Override
    public void loginSuccess(UserBean bean) {
        showToast("登录成功 = " + bean.getUserName());
    }

    @Override
    public void loginFailed(String msg) {
        showToast(msg);
    }

    @Override
    public void showCount() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }


    public MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

}
