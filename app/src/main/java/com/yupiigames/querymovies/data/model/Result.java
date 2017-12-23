package com.yupiigames.querymovies.data.model;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by yair.carreno on 3/20/2016.
 */

@AutoValue
public abstract class Result {

    @SerializedName("page")
    public abstract int page();

    @SerializedName("results")
    public abstract List<Movie> results();

    @Nullable
    @SerializedName("dates")
    public abstract Dates dates();

    @SerializedName("total_pages")
    abstract int totalPages();

    @SerializedName("total_results")
    abstract int totalResults();

    public static TypeAdapter<Result> typeAdapter(Gson gson) {
        return new AutoValue_Result.GsonTypeAdapter(gson);
    }
}