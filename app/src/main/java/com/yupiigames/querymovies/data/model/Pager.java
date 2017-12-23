package com.yupiigames.querymovies.data.model;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class Pager {

    private int page;
    private String query;
    private List<Movie> movieList;

    @Inject
    public Pager() {
        this.movieList = new ArrayList<>();
        this.page = 1;
        this.query = "default";
    }

    public Pager(String query) {
        this();
        this.query = query;
    }

    public List<Movie> getItemList() {
        return movieList;
    }

    public int getItemCount() {
        return movieList.size();
    }

    public String getPage() {
        return Integer.valueOf(page).toString();
    }

    public String getQuery() {
        return query;
    }

    public void updateItemList(List<Movie> movieList) {
        if (this.movieList != null && !movieList.isEmpty()) {
            this.movieList.addAll(movieList);
        } else {
            this.movieList = movieList;
        }
        this.page++;
    }
}
