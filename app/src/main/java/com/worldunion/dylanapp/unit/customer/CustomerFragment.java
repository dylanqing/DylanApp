package com.worldunion.dylanapp.unit.customer;

import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.base.BaseMvpFragment;

/**
 * 客户模块
 * Created by 0169670 on 2016/11/10.
 */
public class CustomerFragment extends BaseMvpFragment {


    @Override
    public int getContentViewId() {
        return R.layout.fragment_customer;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void getData() {

    }

    public CustomerFragment newInstance() {
        CustomerFragment fragment = new CustomerFragment();
        return fragment;
    }
}
