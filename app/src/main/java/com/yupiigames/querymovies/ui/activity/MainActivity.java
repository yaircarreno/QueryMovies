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
import com.yupiigames.querymovies.R;
import com.yupiigames.querymovies.data.SyncService;
import com.yupiigames.querymovies.data.model.Movie;
import com.yupiigames.querymovies.ui.adapter.MoviesAdapter;
import com.yupiigames.querymovies.ui.presenter.MainPresenter;
import com.yupiigames.querymovies.ui.view.MainMvpView;
import com.yupiigames.querymovies.util.DialogFactory;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "com.yupiigames.querymovies.ui.activity.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";
    private static final String EXTRA_PARAM_TITLE = "title";

    @Inject
    MainPresenter mMainPresenter;
    @Inject
    MoviesAdapter mMoviesAdapter;

    @Bind(R.id.main_content)
    CoordinatorLayout coordinatorLayout;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    //Use Item menu is out of scope for ButterKnife
    SearchView searchView;

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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMainPresenter.attachView(this);
        mMainPresenter.loadMovies();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    public SearchView getSearchView(){
        return searchView;
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showMovies(List<Movie> movies) {
        mMoviesAdapter.setmMovies(movies);
        mMoviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_movies))
                .show();
    }

    @Override
    public void syncMovies(String title) {
        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            Intent serviceIntent = new Intent(this, SyncService.class);
            serviceIntent.putExtra(EXTRA_PARAM_TITLE, title);
            startService(serviceIntent);
        }
    }

    @Override
    public void showMoviesEmpty() {
        mMoviesAdapter.setmMovies(Collections.<Movie>emptyList());
        mMoviesAdapter.notifyDataSetChanged();
        DialogFactory.createGenericSnackBar(coordinatorLayout, getString(R.string.empty_movies))
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        mMainPresenter.loadSearch();
        return true;
    }
}