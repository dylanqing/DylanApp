package com.worldunion.dylanapp.base.presenter;


import com.worldunion.dylanapp.base.view.MvpView;

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
public interface IPresenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
