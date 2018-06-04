package com.worldunion.dylanapp.unit.pulltorefresh.view;

import com.worldunion.dylanapp.base.view.MvpView;

import java.util.List;

/**
 * @author Dylan
 * @time 2017/3/28 16:11
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public interface PtrView extends MvpView {
    /**
     * 加载更多
     *
     * @param list
     */
    void showLoadMore(List<String> list);

    /**
     * 刷新
     */
    void showRefresh(List<String> list);


    void showEmpty();

    void showError();

    void showResult(List<String> list);

}
