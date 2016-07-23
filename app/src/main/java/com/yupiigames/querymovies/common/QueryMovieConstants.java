package com.yupiigames.querymovies.common;

/**
 * Created by yair.carreno on 3/20/2016.
 */
public final class QueryMovieConstants {
    private QueryMovieConstants() { }
    public static final String BASE_URL = "http://api.themoviedb.org";
    public static final String VERSION_PATH = "/3";
    public static final String SEARCH_PATH = "/search";
    public static final String MOVIE_PATH = "/movie";
    public static final String API_KEY = "api_key";
    public static final String API_KEY_CODE = "73d93a8edf384634a2c561159294fcf0";
    public static final String QUERY = "query";
    public static final String PAGE = "page";
    public static final String SEARCH_MOVIE_PATH = VERSION_PATH + SEARCH_PATH + MOVIE_PATH;
    public static final String BASE_URL_IMG = "http://image.tmdb.org/t/p/w154";

    public static final String EXTRA_PARAM_TITLE = "title";
    public static final String EXTRA_PARAM_PAGE = "page";
    public static final int OFFSET_PAGE = 10;
    public static final int FIRST_PAGE = 1;

}
