package com.yupiigames.querymovies.ui.presenter;

import android.text.TextUtils;

import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent;
import com.yupiigames.querymovies.data.DataManager;
import com.yupiigames.querymovies.data.model.Movie;
import com.yupiigames.querymovies.data.model.Pager;
import com.yupiigames.querymovies.ui.view.MainMvpView;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

/**
 * Created by yair.carreno on 3/19/2016.
 */
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private CompositeDisposable mCompositeDisposable;
    private Subject<Pager> pagerSubject;
    private Pager pagerModel;

    @Inject
    MainPresenter(DataManager dataManager, Pager pager) {
        mDataManager = dataManager;
        pagerModel = pager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
        // Initializes variables
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        pagerSubject = PublishSubject.create();

        // PublishSubject using switchMap to invoke services to the API.
        mCompositeDisposable.add(
                pagerSubject.switchMap(this::sendRequestToApiObservable)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(results -> {
                            pagerModel.updateItemList(results);
                            if (results.isEmpty()) {
                                getMvpView().showMoviesEmpty();
                            } else {
                                getMvpView().showMovies(pagerModel.getItemList());
                            }
                        }, throwable -> {
                            Timber.e(throwable, "There was an error loading the movies.");
                            getMvpView().showError();
                        }));
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    /**
     * Send request to API. Validate if query is empty or not.
     * If query is empty, the request all items of the first page.
     */
    private Observable<List<Movie>> sendRequestToApiObservable(Pager pagerModel) {
        return mDataManager.syncMovies(pagerModel.getQuery(), pagerModel.getPage())
                .subscribeOn(Schedulers.io());
    }

    public void loadMovies() {
        checkViewAttached();
        pagerSubject.onNext(pagerModel);
    }

    public void loadMoviesStored() {
        mDataManager.getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    pagerModel.updateItemList(results);
                    getMvpView().showMovies(pagerModel.getItemList());
                }, Timber::e);
    }

    /**
     * Management search events how component Rx.
     *
     * @param observable : Observable from SearchView component.
     */
    public void rxSearchBoxEvent(Observable<CharSequence> observable) {
        checkViewAttached();
        mCompositeDisposable.add(
                observable
                        .filter(charSequence -> !TextUtils.isEmpty(charSequence))
                        .throttleLast(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .map(charSequence -> new Pager(charSequence.toString()))
                        .doOnNext(parameters -> this.pagerModel = parameters)
                        .switchMap(this::sendRequestToApiObservable)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(results -> {
                            pagerModel.updateItemList(results);
                            getMvpView().showMovies(pagerModel.getItemList());
                        }, Timber::e));
    }

    /**
     * Management scroll events how component Rx.
     *
     * @param observable : Observable from RecycleView component.
     */
    public void rxScrollEvents(Observable<RecyclerViewScrollEvent> observable) {
        checkViewAttached();
        mCompositeDisposable.add(
                observable
                        .filter(s -> getMvpView().totalItemsShowed() >= this.pagerModel.getItemCount())
                        .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .subscribe(bottomReached -> this.loadMovies(), Timber::e));
    }
}
