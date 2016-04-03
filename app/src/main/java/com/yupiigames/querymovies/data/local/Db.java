package com.yupiigames.querymovies.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.yupiigames.querymovies.data.model.Movie;

/**
 * Created by yair.carreno on 3/19/2016.
 */
public class Db {

    public Db() {
    }

    public abstract static class MovieTable {
        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID
                + " INTEGER NOT NULL PRIMARY KEY, " + COLUMN_TITLE + " TEXT NOT NULL, " + COLUMN_POSTER_PATH
                + " TEXT, " + COLUMN_OVERVIEW + " TEXT, " + COLUMN_RELEASE_DATE + " TEXT" + " )";

        public static ContentValues toContentValues(Movie movie) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, movie.id);
            values.put(COLUMN_TITLE, movie.title);
            values.put(COLUMN_POSTER_PATH, movie.poster_path);
            values.put(COLUMN_OVERVIEW, movie.overview);
            values.put(COLUMN_RELEASE_DATE, movie.release_date);
            return values;
        }

        public static Movie parseCursor(Cursor cursor) {
            Movie movie = new Movie();
            movie.id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            movie.title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
            movie.poster_path = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER_PATH));
            movie.overview = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OVERVIEW));
            movie.release_date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RELEASE_DATE));
            return movie;
        }
    }
}
