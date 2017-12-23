package com.yupiigames.querymovies.data.local;

import com.squareup.sqlbrite3.BriteDatabase;
import com.yupiigames.querymovies.data.model.Movie;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.Observable;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_FAIL;

/**
 * Created by yair.carreno on 3/19/2016.
 */
@Singleton
public class DatabaseHelper {

    @Inject
    BriteDatabase db;

    @Inject
    DatabaseHelper() {
    }

    public Observable<Boolean> setMovies(final Collection<Movie> newMovies) {
        return Observable.defer(() -> {
            BriteDatabase.Transaction transaction = db.newTransaction();
            try {
                db.delete(Movie.TABLE_NAME, null);
                for (Movie movie : newMovies) {
                    long result = db.insert(Movie.TABLE_NAME, CONFLICT_FAIL,
                            new Movie.Builder().id(movie.id())
                                    .title(movie.title()).overview(movie.overview())
                                    .posterPath(movie.posterPath()).releaseDate(movie.releaseDate()).build());
                    if (result < 0) {
                        return Observable.just(false);
                    }
                }
                transaction.markSuccessful();
            } finally {
                transaction.end();
            }
            return Observable.just(true);
        });
    }

    public Observable<List<Movie>> getMovies() {
        return db.createQuery(Movie.TABLE_NAME, DbCallback.LIST_QUERY)
                .mapToList(Movie.MAPPER);
    }
}
