package com.yupiigames.querymovies.ui.presenter;

import android.text.TextUtils;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.yupiigames.querymovies.data.DataManager;
import com.yupiigames.querymovies.ui.activity.MainActivity;
import com.yupiigames.querymovies.ui.view.MainMvpView;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by yair.carreno on 3/19/2016.
 */
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private final CompositeSubscription mCompositeSubscription;

    @Inject
    public MainPresenter(DataManager dataManager, CompositeSubscription compositeSubscription) {
        mDataManager = dataManager;
        mCompositeSubscription = compositeSubscription;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mCompositeSubscription != null)
            mCompositeSubscription.unsubscribe();
    }

    public void loadMovies() {
        checkViewAttached();
        mCompositeSubscription.add(mDataManager.getMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    if (response.isEmpty()) {
                        getMvpView().showMoviesEmpty();
                    } else {
                        getMvpView().showMovies(response);
                    }
                }, throwable -> {
                    Timber.e(throwable, "There was an error loading the movies.");
                    getMvpView().showError();
                }));
    }

    public void loadSearch() {
        checkViewAttached();
        mCompositeSubscription.add(RxSearchView.queryTextChanges(((MainActivity) getMvpView()).getSearchView())
                .filter(charSequence -> !TextUtils.isEmpty(charSequence))
                .throttleLast(100, TimeUnit.MILLISECONDS)
                .debounce(200, TimeUnit.MILLISECONDS)
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .map(s -> s.toString())
                .subscribe(getMvpView()::syncMovies, throwable -> {
                    getMvpView().showError();
                }));
    }
}
