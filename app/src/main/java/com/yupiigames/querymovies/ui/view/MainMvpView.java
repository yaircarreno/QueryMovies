package com.yupiigames.querymovies.ui.view;

import com.yupiigames.querymovies.data.model.Movie;
import java.util.List;

/**
 * Created by yair.carreno on 3/19/2016.
 */
public interface MainMvpView extends MvpView {

    void showMovies(List<Movie> movies);

    void showMoviesEmpty();

    void showError();

    void syncMovies(String title);
}
