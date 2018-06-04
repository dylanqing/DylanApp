package com.worldunion.dylanapp.unit.house;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.base.BaseMvpFragment;

public class HousesFragment extends BaseMvpFragment {

    @Override
    public int getContentViewId() {
        return R.layout.fragment_house;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments!=null){
            String name = arguments.getString("name");
        }
    }

    public static HousesFragment newInstance(String name) {
        HousesFragment fragment = new HousesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        fragment.setArguments(bundle);
        return fragment;
    }
}
