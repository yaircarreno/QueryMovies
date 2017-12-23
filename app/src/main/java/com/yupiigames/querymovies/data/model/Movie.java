package com.yupiigames.querymovies.data.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;
import com.yupiigames.querymovies.data.local.Db;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import io.reactivex.functions.Function;

@AutoValue
public abstract class Movie implements Parcelable {

    public static final String TABLE_NAME = "movies";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_POSTER_PATH = "poster_path";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_RELEASE_DATE = "release_date";

    public abstract int id();

    public abstract String title();

    @Nullable
    @SerializedName("poster_path")
    public abstract String posterPath();

    public abstract String overview();

    @Nullable
    @SerializedName("release_date")
    public abstract String releaseDate();

    public static TypeAdapter<Movie> typeAdapter(Gson gson) {
        return new AutoValue_Movie.GsonTypeAdapter(gson);
    }

    public static final Function<Cursor, Movie> MAPPER = cursor -> {
        int id = Db.getInt(cursor, COLUMN_ID);
        String title = Db.getString(cursor, COLUMN_TITLE);
        String posterPath = Db.getString(cursor, COLUMN_POSTER_PATH);
        String overview = Db.getString(cursor, COLUMN_OVERVIEW);
        String releaseDate = Db.getString(cursor, COLUMN_RELEASE_DATE);
        return new AutoValue_Movie(id, title, posterPath, overview, releaseDate);
    };

    public static final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder id(int id) {
            values.put(COLUMN_ID, id);
            return this;
        }

        public Builder title(String title) {
            values.put(COLUMN_TITLE, title);
            return this;
        }

        public Builder posterPath(String posterPath) {
            values.put(COLUMN_POSTER_PATH, posterPath);
            return this;
        }

        public Builder overview(String overview) {
            values.put(COLUMN_OVERVIEW, overview);
            return this;
        }

        public Builder releaseDate(String releaseDate) {
            values.put(COLUMN_RELEASE_DATE, releaseDate);
            return this;
        }

        public ContentValues build() {
            return values;
        }
    }
}
