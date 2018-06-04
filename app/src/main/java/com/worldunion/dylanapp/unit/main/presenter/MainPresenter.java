package com.worldunion.dylanapp.unit.main.presenter;

import android.content.Context;

import com.worldunion.dylanapp.base.presenter.BasePresenter;
import com.worldunion.dylanapp.unit.main.view.MainView;

/**
 * @author Dylan
 * @time 2017/2/27 10:05
 * @des 主界面presenter
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class MainPresenter extends BasePresenter<MainView> {

    private String TAG = "MainPresenter";
    private Context mContext;

    public MainPresenter() {

    }

    public MainPresenter(Context ctx) {
        this.mContext = ctx;
    }

    /**
     * 获取消息未读数并显示
     */
    public void queryUnreadMsgCount() {
//        OkGo.post(IConstants.GET_UNREAD_MESSAGE_COUNT)
//                .tag(this)
//                .params("userId", BaseApplication.userBean.getUserId())
//                .execute(new BeanCallback<BaseResponse<Integer>>() {
//                    @Override
//                    public void onSuccess(BaseResponse<Integer> voidBaseResponse, Call call, Response response) {
//                        int unReadCount = voidBaseResponse.data;
//                        getMvpView().showUnReadMsg(unReadCount);
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                    }
//                });
    }

}
