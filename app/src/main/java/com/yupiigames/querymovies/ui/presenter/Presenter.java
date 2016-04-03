package com.yupiigames.querymovies.ui.presenter;

import com.yupiigames.querymovies.ui.view.MvpView;

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 * Created by yair.carreno on 3/19/2016.
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
