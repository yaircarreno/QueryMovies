package com.yupiigames.querymovies.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import com.yupiigames.querymovies.data.model.Movie;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by yair.carreno on 3/19/2016.
 */
@Singleton
public class DatabaseHelper {
    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        mDb = SqlBrite.create().wrapDatabaseHelper(dbOpenHelper);
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }

    /**
     * Remove all the data from all the tables in the database.
     */
    public Observable<Void> clearTables() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                if (subscriber.isUnsubscribed())
                    return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    Cursor cursor = mDb.query("SELECT name FROM sqlite_master WHERE type='table'");
                    while (cursor.moveToNext()) {
                        mDb.delete(cursor.getString(cursor.getColumnIndex(Db.MovieTable.COLUMN_ID)), null);
                    }
                    cursor.close();
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<Movie> setMovies(final Collection<Movie> newMovies) {
        return Observable.create(new Observable.OnSubscribe<Movie>() {
            @Override
            public void call(Subscriber<? super Movie> subscriber) {
                if (subscriber.isUnsubscribed())
                    return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    mDb.delete(Db.MovieTable.TABLE_NAME, null);
                    for (Movie movie : newMovies) {
                        long result = mDb.insert(Db.MovieTable.TABLE_NAME, Db.MovieTable.toContentValues(movie),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        if (result >= 0)
                            subscriber.onNext(movie);
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<List<Movie>> getMovies() {
        return mDb.createQuery(Db.MovieTable.TABLE_NAME, "SELECT * FROM " + Db.MovieTable.TABLE_NAME).mapToList(
                cursor -> Db.MovieTable.parseCursor(cursor));
    }
}
