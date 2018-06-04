package com.worldunion.dylanapp.unit.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.dylan.baselib.utils.AppManager;
import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.base.BaseMvpActivity;
import com.worldunion.dylanapp.base.BaseMvpFragment;
import com.worldunion.dylanapp.unit.customer.CustomerFragment;
import com.worldunion.dylanapp.unit.house.HousesFragment;
import com.worldunion.dylanapp.unit.my.MyFragment;
import com.worldunion.dylanapp.unit.main.presenter.MainPresenter;
import com.worldunion.dylanapp.utils.CommonUtils;
import com.worldunion.dylanapp.utils.StatusBarUtil;
import com.dylan.baselib.utils.log.KLog;
import com.worldunion.dylanapp.unit.main.view.MainView;
import com.worldunion.dylanapp.widget.BadgeView;
import com.worldunion.dylanapp.widget.TabRadioView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseMvpActivity implements MainView {

    /*楼盘*/
    @BindView(R.id.main_radio_houses)
    TabRadioView radioHouses;

    /*客户*/
    @BindView(R.id.main_radio_customer)
    TabRadioView radioCustomer;

    /*我的*/
    @BindView(R.id.main_radio_my)
    TabRadioView radioMy;

    public static String TAG = "MainActivity";

    public long EXIT_TIME = 2000;

    /**
     * 当前选择项
     */
    private TabRadioView radioNow;
    private FragmentManager mFragmentManager;
    /**
     * 菜单切换出的Fragment集合
     */
    private BaseMvpFragment[] fragments;
    private MainPresenter mPresenter;

    private BadgeView unReadView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setStatusBar(View view) {
    }

    @Override
    protected void init() {
        initDefault();
        /*消息红点*/
        unReadView = new BadgeView(mContext);
        unReadView.setTargetView(radioMy);//消息红点
        //unReadView.setBackgroundResource(R.drawable.message_red);
        unReadView.setBadgeMargin(0, 3, 18, 0);
        testReflect();
        boolean miUi = CommonUtils.isMiUi();
        showToast(miUi+"");
    }

    private void testReflect() {
        /*第一种方法*/
        //        try {
        //            Class<?> clazz = Class.forName("com.worldunion.dylanapp.unit.splash.SplashActivity");
        //            KLog.d(TAG, clazz.getName());
        //
        //        } catch (ClassNotFoundException e) {
        //            e.printStackTrace();
        //        }
        /*第二种方法*/
//        Class<SplashActivity> clazz = SplashActivity.class;
//
//        KLog.d(TAG, clazz.getName());
//        try {
//            Method ab = clazz.getDeclaredMethod("ab");
//            ab.invoke()
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        Field id = clazz.getDeclaredField("id");
//        for (int i = 0; i < declaredFields.length; i++) {
//            KLog.d(TAG, declaredFields[i]);
//        }
    }

    @Override
    public void initPresenter() {
        mPresenter = new MainPresenter(this);
        mPresenter.attachView(this);
    }

    private void initDefault() {
        mFragmentManager = getSupportFragmentManager();// 获取Fragment管理
        fragments = new BaseMvpFragment[3];
        //避免应用重载导致界面重叠的问题
        for (int i = 0; i < fragments.length; i++) {
            Fragment fragment = mFragmentManager.findFragmentByTag("tag" + i);
            if (fragment != null && fragment instanceof BaseMvpFragment) {
                fragments[i] = (BaseMvpFragment) fragment;
            }
        }
        chooseFragment(0);
        radioNow = radioHouses;
        radioNow.setCheck(true);
    }

    @Override
    protected void getData() {
        mPresenter.queryUnreadMsgCount();
    }


    @OnClick({R.id.main_radio_houses, R.id.main_radio_customer, R.id.main_radio_my})
    public void onClick(View view) {
        int position = 0;
        switch (view.getId()) {
            case R.id.main_radio_houses:
                position++;
            case R.id.main_radio_customer:
                position++;
            case R.id.main_radio_my:
                position++;
                TabRadioView radioView = (TabRadioView) view;
                if (!radioView.isCheck) {
                    radioNow.setCheck(false);
                    radioNow = radioView;
                    radioNow.setCheck(true);
                    chooseFragment(fragments.length - position);
                }
                break;
            default:
                break;
        }
    }

    public void chooseFragment(int position) {
        FragmentTransaction beginTransaction = mFragmentManager.beginTransaction();
        if (fragments[position] == null) {
            switch (position) {
                case 0:
                    fragments[position] = new HousesFragment();
                    break;
                case 1:
                    fragments[position] = new CustomerFragment();
                    break;
                case 2:
                    fragments[position] = new MyFragment();
                    break;
                default:
                    break;
            }
            beginTransaction.add(R.id.content_frame, fragments[position], "tag" + position);
        }
        switch (position) {
            case 0: //楼盘页面全屏特殊处理
                break;
            case 2: //我的页面全屏特殊处理
                break;
            default://其他页面设置有颜色的 内部resetFragmentView
                //                StatusBarUtil.setColorNoTranslucent(MainActivity.this, getResources().getColor(R.color.main_color));
                StatusBarUtil.setColor(MainActivity.this, getResources().getColor(R.color.main_color));
                break;
        }
        beginTransaction.show(fragments[position]);
        for (int i = 0; i < fragments.length; i++) {
            if (fragments[i] != null && position != i) {
                beginTransaction.hide(fragments[i]);
            }
        }
        beginTransaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            /*退出app*/
            if ((System.currentTimeMillis() - EXIT_TIME) > 2000) {
                showToast(getString(R.string.common_exit));
                EXIT_TIME = System.currentTimeMillis();
            } else {
                AppManager.getAppManager().finishAllActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void showUnReadMsg(int count) {
        KLog.i(TAG, "未读消息数量 : " + count);
        if (count != 0) {
            unReadView.setVisibility(View.VISIBLE);
            unReadView.setText(count < 99 ? "" + count : "99+");
        } else {
            unReadView.setVisibility(View.GONE);
        }
    }

    @Override
    public void initEvent() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
