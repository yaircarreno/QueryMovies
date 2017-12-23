package com.yupiigames.querymovies.ui.presenter;

import android.text.TextUtils;

import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent;
import com.yupiigames.querymovies.data.DataManager;
import com.yupiigames.querymovies.ui.view.MainMvpView;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by yair.carreno on 3/19/2016.
 */
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private CompositeDisposable mDisposables;

    @Inject
    MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposables != null) {
            mDisposables.clear();
        }
    }

    public void loadMovies() {
        checkViewAttached();
        mDisposables.add(mDataManager.getMovies().observeOn(AndroidSchedulers.mainThread())
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
        mDisposables.add(observable.filter(charSequence -> !TextUtils.isEmpty(charSequence))
                .throttleLast(100, TimeUnit.MILLISECONDS).debounce(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread()).map(CharSequence::toString)
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
        mDisposables.add(observable.subscribeOn(AndroidSchedulers.mainThread()).subscribe(
                recyclerViewScrollEvent -> {
                    getMvpView().updateScroll();
                }));
    }
}
