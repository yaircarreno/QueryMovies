package com.yupiigames.querymovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yair.carreno on 3/20/2016.
 */
public class Result implements Parcelable {

    public int page;
    public List<Movie> results;
    public int total_results;
    public int total_pages;

    private Result(Parcel in) {
        this.page = in.readInt();
        if (results == null) {
            results = new ArrayList();
        }
        in.readTypedList(results, Movie.CREATOR);
        this.total_results = in.readInt();
        this.total_pages = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeTypedList(this.results);
        dest.writeInt(this.total_results);
        dest.writeInt(this.total_pages);
    }

    public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Result queryResult = (Result) o;

        if (page != queryResult.page)
            return false;
        if (results != null ? !results.equals(queryResult.results) : queryResult.results != null)
            return false;
        if (total_results != queryResult.total_results)
            return false;
        return (total_pages != queryResult.total_pages);
    }

    @Override
    public int hashCode() {
        int result = 31 * page;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        result = 31 * result + total_results;
        result = 31 * result + total_pages;
        return result;
    }
}