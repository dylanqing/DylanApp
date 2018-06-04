package com.worldunion.dylanapp.unit.my.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.worldunion.dylanapp.base.BaseApplication;
import com.worldunion.dylanapp.bean.UserBean;
import com.worldunion.dylanapp.module.constant.IConstants;
import com.worldunion.dylanapp.module.constant.SPConstant;
import com.worldunion.dylanapp.module.http.BaseResponse;
import com.worldunion.dylanapp.module.http.BeanCallback;
import com.worldunion.dylanapp.base.presenter.BasePresenter;
import com.worldunion.dylanapp.utils.CommonUtils;
import com.worldunion.dylanapp.utils.SPUtils;
import com.worldunion.dylanapp.unit.my.view.MyView;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author Dylan
 * @time 2017/3/27 10:27
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class MyPresenter extends BasePresenter<MyView> {

    private Context mContext;

    public MyPresenter() {

    }

    public MyPresenter(Context ctx) {
        mContext = ctx;
    }


    public void login(String userName ,String pwd){
        OkGo.post(IConstants.LOGIN)
                .tag(getMvpView())
                .params("userName", userName)
                .params("password", pwd)
                .execute(new BeanCallback<BaseResponse<UserBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<UserBean> responseData, Call call, Response response) {
                        /*保存用户信息*/
                        BaseApplication.userBean = responseData.data;
                        SPUtils.setObjectValue(SPConstant.LOGIN_INFO, responseData.data);
                        if (CommonUtils.isNotEmpty(responseData.data.getDeviceToken())) {
                            SPUtils.setStringValue(SPUtils.LOGIN_TOKEN, responseData.data.getDeviceToken());
                        }

//                        /*友盟账号统计*/
//                        MobclickAgent.onProfileSignIn(responseData.data.getUserName());
//                        /*注册信鸽推送*/
//                        XGPushManager.registerPush(BaseApplication.getInstance(), responseData.data.getUserName());

                        getMvpView().loginSuccess(responseData.data);
                    }

                    @Override
                    public void onError(Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        getMvpView().loginFailed(e.getMessage());
                    }
                });
    }
}
