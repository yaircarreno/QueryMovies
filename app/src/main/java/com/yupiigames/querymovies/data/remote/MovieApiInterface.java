package com.yupiigames.querymovies.data.remote;

import com.google.gson.GsonBuilder;
import com.yupiigames.querymovies.common.QueryMovieConstants;
import com.yupiigames.querymovies.data.model.Result;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yair.carreno on 3/19/2016.
 */
public interface MovieApiInterface {

    @GET(QueryMovieConstants.SEARCH_MOVIE_PATH)
    Observable<Result> getMovies(
            @Query(QueryMovieConstants.API_KEY) String apiKey,
            @Query(QueryMovieConstants.QUERY) String query,
            @Query(QueryMovieConstants.PAGE) String page
    );

    /******** Helper class that sets up a new services *******/
    class Creator {
        public static MovieApiInterface newMovieApi() {

            GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(
                    new GsonBuilder().registerTypeAdapterFactory(AutoValueGsonFactory.create())
                            .create());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(QueryMovieConstants.BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit.create(MovieApiInterface.class);
        }
    }
}
