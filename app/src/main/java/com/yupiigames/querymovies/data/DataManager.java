package com.yupiigames.querymovies.data;

import com.yupiigames.querymovies.common.QueryMovieConstants;
import com.yupiigames.querymovies.data.local.DatabaseHelper;
import com.yupiigames.querymovies.data.local.PreferencesHelper;
import com.yupiigames.querymovies.data.model.Movie;
import com.yupiigames.querymovies.data.remote.MovieApiInterface;
import com.yupiigames.querymovies.util.EventPosterHelper;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.functions.Action0;

/**
 * Created by yair.carreno on 3/19/2016.
 */

@Singleton
public class DataManager {

    private final MovieApiInterface mMovieApiInterface;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final EventPosterHelper mEventPoster;

    @Inject
    public DataManager(MovieApiInterface movieApiInterface, PreferencesHelper preferencesHelper,
            DatabaseHelper databaseHelper, EventPosterHelper eventPosterHelper) {
        this.mMovieApiInterface = movieApiInterface;
        this.mPreferencesHelper = preferencesHelper;
        this.mDatabaseHelper = databaseHelper;
        this.mEventPoster = eventPosterHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Movie> syncMovies(String title, String page) {
        return mMovieApiInterface.getMovies(QueryMovieConstants.API_KEY_CODE, title, page)
                .concatMap(s -> mDatabaseHelper.setMovies(s.results))
                .onErrorResumeNext(throwable -> {
                    return Observable.empty();
                });
    }

    public Observable<List<Movie>> getMovies() {
        return mDatabaseHelper.getMovies().distinct();
    }

    /// Helper method to post events from doOnCompleted.
    private Action0 postEventAction(final Object event) {
        return () -> mEventPoster.postEventSafely(event);
    }
}
