package com.yupiigames.querymovies.injection.module;

import com.yupiigames.querymovies.data.remote.MovieApiInterface;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by yair.carreno on 3/19/2016.
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    MovieApiInterface provideMovieApi() {
        return MovieApiInterface.Creator.newMovieApi();
    }
}
