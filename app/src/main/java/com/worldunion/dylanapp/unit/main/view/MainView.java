package com.worldunion.dylanapp.unit.main.view;


import com.worldunion.dylanapp.base.view.MvpView;

/**
 * @author Dylan
 * @time 2017/2/27 10:04
 * @des 主界面view
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public interface MainView extends MvpView {

    /**
     * 显示未读消息
     */
    void showUnReadMsg(int count);

}
