package com.yupiigames.querymovies.data.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import com.yupiigames.querymovies.data.model.Movie;

/**
 * Created by yair.carreno on 3/19/2016.
 */
public final class DbCallback extends SupportSQLiteOpenHelper.Callback {

    private static final int DATABASE_VERSION = 1;

    private static final String CREATE = "CREATE TABLE " + Movie.TABLE_NAME + " (" + Movie.COLUMN_ID
            + " INTEGER NOT NULL PRIMARY KEY, " + Movie.COLUMN_TITLE + " TEXT NOT NULL, " + Movie.COLUMN_POSTER_PATH
            + " TEXT, " + Movie.COLUMN_OVERVIEW + " TEXT, " + Movie.COLUMN_RELEASE_DATE + " TEXT" + " )";

    static final String LIST_QUERY = "SELECT * FROM " + Movie.TABLE_NAME;


    public DbCallback() {
        super(DATABASE_VERSION);
    }

    @Override
    public void onCreate(SupportSQLiteDatabase db) {
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SupportSQLiteDatabase db, int oldVersion, int newVersion) {
    }
}