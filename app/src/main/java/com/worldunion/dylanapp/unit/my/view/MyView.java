package com.worldunion.dylanapp.unit.my.view;

import com.worldunion.dylanapp.bean.UserBean;
import com.worldunion.dylanapp.base.view.MvpView;

/**
 * @author Dylan
 * @time 2017/3/27 10:27
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public interface MyView extends MvpView {

    /*登录成功*/
    void loginSuccess(UserBean bean);

    /*登录失败*/
    void loginFailed(String msg);


    void showCount();

}
