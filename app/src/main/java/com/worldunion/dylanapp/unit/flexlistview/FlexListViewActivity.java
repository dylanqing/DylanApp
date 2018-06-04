package com.worldunion.dylanapp.unit.flexlistview;

import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.base.BaseMvpActivity;
import com.worldunion.dylanapp.unit.pulltorefresh.adapter.PtrAdapter;
import com.worldunion.dylanapp.widget.FlexibleListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author Dylan
 * @time 2017/3/29 16:20
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class FlexListViewActivity extends BaseMvpActivity {

    @BindView(R.id.flvList)
    FlexibleListView mFlvList;

    private List<String> mData = new ArrayList<>();
    private PtrAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_flexlistview;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void init() {
        for (int i = 0; i < 20; i++) {
            mData.add("i = " + i);
        }
        mAdapter = new PtrAdapter(this, mData);
        mFlvList.setAdapter(mAdapter);
    }

    @Override
    protected void getData() {

    }
}
