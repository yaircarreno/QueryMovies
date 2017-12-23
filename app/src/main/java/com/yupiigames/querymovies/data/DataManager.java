package com.yupiigames.querymovies.data;

import com.yupiigames.querymovies.common.QueryMovieConstants;
import com.yupiigames.querymovies.data.local.DatabaseHelper;
import com.yupiigames.querymovies.data.model.Movie;
import com.yupiigames.querymovies.data.remote.MovieApiInterface;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by yair.carreno on 3/19/2016.
 */

@Singleton
public class DataManager {

    private final MovieApiInterface mMovieApiInterface;
    private final DatabaseHelper mDatabaseHelper;

    @Inject
    public DataManager(MovieApiInterface movieApiInterface, DatabaseHelper databaseHelper) {
        this.mMovieApiInterface = movieApiInterface;
        this.mDatabaseHelper = databaseHelper;
    }

    public Observable<List<Movie>> syncMovies(String title, String page) {
        return mMovieApiInterface.getMovies(QueryMovieConstants.API_KEY_CODE, title, page)
                .concatMap(s -> mDatabaseHelper.setMovies(s.results()))
                .onErrorResumeNext(throwable -> {
                    Timber.e(throwable);
                    return Observable.empty();
                });
    }

    public Observable<List<Movie>> getMovies() {
        return mDatabaseHelper.getMovies().distinct();
    }
}
