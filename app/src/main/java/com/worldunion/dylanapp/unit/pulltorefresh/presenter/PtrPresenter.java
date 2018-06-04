package com.worldunion.dylanapp.unit.pulltorefresh.presenter;

import android.content.Context;

import com.worldunion.dylanapp.unit.pulltorefresh.adapter.PtrAdapter;
import com.worldunion.dylanapp.base.presenter.BasePresenter;
import com.worldunion.dylanapp.utils.CommonUtils;
import com.worldunion.dylanapp.unit.pulltorefresh.view.PtrView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * @author Dylan
 * @time 2017/3/28 16:30
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class PtrPresenter extends BasePresenter<PtrView> {

    private Context mContext;
    /*每页数据*/
    private int pageSize = 10;

    private int flag;
    /*正常展示(刷新显示)*/
    private final int NORMAL = 0;
    /*加载更多*/
    private final int LOADMORE = 1;
    /*下拉刷新*/
    private final int REFRESH = 2;

    private List<String> mData = new ArrayList<>();

    public PtrPresenter() {

    }

    public PtrPresenter(Context ctx) {
        mContext = ctx;
        for (int i = 0; i < 10; i++) {
            mData.add("i = " + i);
        }
    }


    public void getStringData(int page) {
        String areaCd = "";

        /*如果是网络请求则在success里面和error操作*/
        switch (flag) {
            case NORMAL:
                /*展示数据*/
                if (CommonUtils.isNotEmpty(mData)) {
                    getMvpView().showResult(mData);
                } else {
                    getMvpView().showEmpty();
                }
                break;
            case LOADMORE:
                /*加载更多*/
                getMvpView().showLoadMore(mData);
                break;
            case REFRESH:
                /*下拉刷新----做完后需求说不用刷新了 -V- */
                if (CommonUtils.isNotEmpty(mData)) {
                    getMvpView().showRefresh(mData);
                } else {
                    getMvpView().showEmpty();
                }
        }
        flag = NORMAL;
    }

    /**
     * 加载更多---如果搜索栏上有数据，需要带上
     *
     * @param page    页数
     * @param adapter
     */
    public void loadMoreData(final int page, final PtrAdapter adapter) {
        flag = LOADMORE;
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        List<String> data = adapter.getData();
                        int size = data.size();
                        for (int i = size; i < size + 10; i++) {
                            mData.add("i = " + i);
                        }
                        getStringData(page);
                    }
                });
    }

    /**
     * 下拉刷新---如果搜索栏上有数据，需要带上
     */
    public void refreshData() {
        flag = REFRESH;
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        getStringData(1);
                    }
                });
    }


    public void setItemClick(PtrAdapter adapter, int position) {
        getMvpView().showToast("click = " + position);
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
