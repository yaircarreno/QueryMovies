package com.yupiigames.querymovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yair.carreno on 3/20/2016.
 */
public class Movie implements Parcelable {

    public int id;
    public String title;
    public String poster_path;
    public String overview;
    public String release_date;

    public Movie() {
    }

    private Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.poster_path = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.poster_path);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int hashCode() {
        int result = 31 * id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (poster_path != null ? poster_path.hashCode() : 0);
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (release_date != null ? release_date.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (id != movie.id) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
        if (poster_path != null ? !poster_path.equals(movie.poster_path) : movie.poster_path != null) return false;
        if (overview != null ? !overview.equals(movie.overview) : movie.overview != null) return false;
        return (release_date != null ? !release_date.equals(movie.release_date) : movie.release_date != null);
    }
}
