package com.worldunion.dylanapp.unit.pulltorefresh;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dylan.pulltorefresh.PullToRefreshBase;
import com.dylan.pulltorefresh.PullToRefreshListView;
import com.worldunion.dylanapp.R;
import com.worldunion.dylanapp.base.BaseMvpActivity;
import com.worldunion.dylanapp.unit.pulltorefresh.adapter.PtrAdapter;
import com.worldunion.dylanapp.unit.pulltorefresh.presenter.PtrPresenter;
import com.worldunion.dylanapp.utils.CommonUtils;
import com.worldunion.dylanapp.unit.pulltorefresh.view.PtrView;
import com.worldunion.dylanapp.widget.LoadingLayout;

import java.util.Date;
import java.util.List;

import butterknife.BindView;


/**
 * @author Dylan
 * @time 2017/3/28 16:08
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class PtrActivity extends BaseMvpActivity implements PtrView {

    @BindView(R.id.ptrlvSelectHouse)
    PullToRefreshListView mPtrlvSelectHouse;
    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;
    private PtrPresenter mPresenter;
    private PtrAdapter mAdapter;
    private int page = 1;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_ptr;
    }

    @Override
    public void initListener() {
        super.initListener();

        mPtrlvSelectHouse.setMode(PullToRefreshBase.Mode.BOTH);
        mPtrlvSelectHouse.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                /*下拉刷新*/
                String laseUpdateTime = getString(R.string.pull_to_refresh_last_update_time) + CommonUtils.get16SFormatDate(new Date());
                mPtrlvSelectHouse.getLoadingLayoutProxy(true, false).setLastUpdatedLabel(laseUpdateTime);
                mPresenter.refreshData();
                /*重置页数*/
                page = 1;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                /*加载更多*/
                page++;
                mPresenter.loadMoreData(page, mAdapter);
            }
        });

        mPtrlvSelectHouse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.setItemClick(mAdapter, position);
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter = new PtrPresenter(this);
        mPresenter.attachView(this);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void getData() {
        mLoadingLayout.showLoading();
        mPresenter.getStringData(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }


    @Override
    public void showLoadMore(List<String> list) {
        if (CommonUtils.isNotEmpty(list)) {
            mAdapter.addAll(list);
            mAdapter.notifyDataSetChanged();
        } else {
            page--;
            showToast(R.string.common_no_more);
        }
        mPtrlvSelectHouse.onRefreshComplete();
    }

    @Override
    public void showRefresh(List<String> list) {
        mAdapter.clear();
        mAdapter.addAll(list);
        mAdapter.notifyDataSetChanged();
        mPtrlvSelectHouse.onRefreshComplete();
    }

    @Override
    public void showEmpty() {
        mLoadingLayout.showEmpty();
    }

    @Override
    public void showError() {
        showToast("ERROR");
    }

    @Override
    public void showResult(List<String> list) {
        mLoadingLayout.showContent();
        mAdapter = new PtrAdapter(this, list);
        mPtrlvSelectHouse.setAdapter(mAdapter);
        /*小于10条数据不进行加载更多*/
        if (list.size() < 10) {
            mPtrlvSelectHouse.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        } else {
            mPtrlvSelectHouse.setMode(PullToRefreshBase.Mode.BOTH);
        }
    }
}
