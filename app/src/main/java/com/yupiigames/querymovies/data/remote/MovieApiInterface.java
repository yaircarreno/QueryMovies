package com.yupiigames.querymovies.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yupiigames.querymovies.common.QueryMovieConstants;
import com.yupiigames.querymovies.data.model.Result;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yair.carreno on 3/19/2016.
 */
public interface MovieApiInterface {

    @GET(QueryMovieConstants.SEARCH_MOVIE_PATH)
    Observable<Result> getMovies(
            @Query(QueryMovieConstants.API_KEY) String apiKey,
            @Query(QueryMovieConstants.QUERY) String query
    );

    /******** Helper class that sets up a new services *******/
    class Creator {
        public static MovieApiInterface newMovieApi() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(QueryMovieConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(MovieApiInterface.class);
        }
    }
}
