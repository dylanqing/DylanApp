package com.worldunion.dylanapp.base.view;

/**
 * @author Dylan
 * @time 2017/3/20 10:02
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public interface MvpView {

    void showLoading();

    void hideLoading();

    void showToast(String data);

    void showToast(int id);


}
