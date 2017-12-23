package com.yupiigames.querymovies.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.yupiigames.querymovies.R;
import com.yupiigames.querymovies.common.QueryMovieConstants;
import com.yupiigames.querymovies.data.model.Movie;
import com.yupiigames.querymovies.ui.adapter.MoviesAdapter;
import com.yupiigames.querymovies.ui.presenter.MainPresenter;
import com.yupiigames.querymovies.ui.view.MainMvpView;
import com.yupiigames.querymovies.util.DialogFactory;
import java.util.List;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView {

    private static final String EXTRA_TRIGGER_SYNC_FLAG = "com.yupiigames.querymovies.ui.activity.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject
    MainPresenter mMainPresenter;

    @Inject
    MoviesAdapter mMoviesAdapter;

    @BindView(R.id.main_content)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //Use Item menu is out of scope for ButterKnife
    SearchView searchView;
    private int page;

    private LinearLayoutManager mLinearLayoutManager;

    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        mRecyclerView.setAdapter(mMoviesAdapter);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMainPresenter.attachView(this);

        mMainPresenter.loadMoviesStored();

        // Create the Observable for scroll events
        mMainPresenter.rxScrollEvents(RxRecyclerView.scrollEvents(mRecyclerView));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showMovies(List<Movie> movies) {
        mMoviesAdapter.setOptions(movies);
        mMoviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_movies)).show();
    }

    @Override
    public void showMoviesEmpty() {
        DialogFactory.createGenericSnackBar(coordinatorLayout, getString(R.string.empty_movies)).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        page = QueryMovieConstants.FIRST_PAGE;
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));

        // Create the Observable for search view box
        mMainPresenter.rxSearchBoxEvent(RxSearchView.queryTextChanges(searchView));
        return true;
    }

    @Override
    public int totalItemsShowed() {
        return mLinearLayoutManager.getChildCount() + mLinearLayoutManager.findFirstVisibleItemPosition();
    }
}