package com.yupiigames.querymovies.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.yupiigames.querymovies.R;
import com.yupiigames.querymovies.common.QueryMovieConstants;
import com.yupiigames.querymovies.data.model.Movie;
import com.yupiigames.querymovies.util.ViewUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yair.carreno on 3/21/2016.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> mMovies;
    private Context mContext;

    @Inject
    public MoviesAdapter(Context context) {
        this.mMovies = new ArrayList<>();
        this.mContext = context;
    }

    public void setmMovies(List<Movie> movies) {
        this.mMovies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        holder.titleTextView.setText(movie.title);
        holder.overviewTextView.setText(movie.overview);
        if (movie.poster_path != null && !movie.poster_path.isEmpty()) {
            holder.setMovieImage(QueryMovieConstants.BASE_URL_IMG + movie.poster_path);
        } else {
            holder.setPlaceholderImage();
        }
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        int imgSizePxWight = ViewUtil.dpToPx(100);
        int imgSizePxHeight = ViewUtil.dpToPx(150);
        @Bind(R.id.poster)  ImageView posterView;
        @Bind(R.id.title_movie) TextView titleTextView;
        @Bind(R.id.overview) TextView overviewTextView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setMovieImage(String urlImage) {
            Picasso.with(mContext)
                    .load(urlImage)
                    .placeholder(R.drawable.movie_placeholder)
                    .resize(imgSizePxWight, imgSizePxHeight)
                    .into(posterView);
        }

        public void setPlaceholderImage() {
            Picasso.with(mContext)
                    .load(R.drawable.movie_placeholder)
                    .resize(imgSizePxWight, imgSizePxHeight)
                    .into(posterView);
        }
    }
}
