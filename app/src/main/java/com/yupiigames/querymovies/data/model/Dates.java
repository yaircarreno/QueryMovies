package com.yupiigames.querymovies.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by ycarreno on 12/22/17.
 */

@AutoValue
public abstract class Dates {

    abstract String maximum();

    abstract String minimum();

    public static TypeAdapter<Dates> typeAdapter(Gson gson) {
        return new AutoValue_Dates.GsonTypeAdapter(gson);
    }
}
