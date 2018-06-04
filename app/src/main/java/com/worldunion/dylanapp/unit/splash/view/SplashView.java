package com.worldunion.dylanapp.unit.splash.view;


import com.worldunion.dylanapp.base.view.MvpView;

/**
 * @author Dylan
 * @time 2016/11/23 11:22
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public interface SplashView extends MvpView {

    /*引导页*/
    void goGuide();

    /*跳转登录页面*/
    void goLogin();

    /*跳转主页面*/
    void goMain();

    void updateVersion(String url, String desc);

    /*强制更新*/
    void forecdUpdate(String url, String desc);
}
