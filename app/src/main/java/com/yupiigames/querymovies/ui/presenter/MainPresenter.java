package com.yupiigames.querymovies.ui.presenter;

import android.text.TextUtils;

import com.jakewharton.rxbinding.support.v7.widget.RecyclerViewScrollEvent;
import com.yupiigames.querymovies.data.DataManager;
import com.yupiigames.querymovies.ui.view.MainMvpView;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by yair.carreno on 3/19/2016.
 */
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private CompositeSubscription mCompositeSubscription;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
        if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription = new CompositeSubscription();
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mCompositeSubscription != null)
            mCompositeSubscription.clear();
    }

    public void loadMovies() {
        checkViewAttached();
        mCompositeSubscription.add(mDataManager.getMovies().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(response -> {
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

    public void loadSearch(Observable<CharSequence> observable, String page) {
        checkViewAttached();
        mCompositeSubscription.add(observable.filter(charSequence -> !TextUtils.isEmpty(charSequence))
                .throttleLast(100, TimeUnit.MILLISECONDS).debounce(200, TimeUnit.MILLISECONDS)
                .onBackpressureLatest().observeOn(AndroidSchedulers.mainThread()).map(s -> s.toString())
                .subscribe(title -> {
                    getMvpView().syncMovies(title, page);
                }, throwable -> {
                    getMvpView().showError();
                }));
    }

    /**
     * Management pagination how component Rx.
     *
     * @param observable : Observable from RecycleView component.
     */
    public void loadPager(Observable<RecyclerViewScrollEvent> observable) {
        mCompositeSubscription.add(observable.subscribeOn(AndroidSchedulers.mainThread()).subscribe(
                recyclerViewScrollEvent -> {
                    getMvpView().updateScroll();
                }));
    }
}
