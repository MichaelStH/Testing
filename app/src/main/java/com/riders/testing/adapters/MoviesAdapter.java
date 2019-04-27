package com.riders.testing.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.riders.testing.R;
import com.riders.testing.model.Movie;
import com.riders.testing.views.MultiPaneViewHolder;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MultiPaneViewHolder> {

    private static final String TAG = MoviesAdapter.class.getSimpleName();
    private List<Movie> moviesList;

    public MoviesAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public int getItemCount() {
        if (moviesList != null) {
            return moviesList.size();
        }
        return 0;
    }

    @Override
    public MultiPaneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MultiPaneViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_multi_pane, parent, false));
    }

    @Override
    public void onBindViewHolder(MultiPaneViewHolder holder, int position) {
        Movie movie = moviesList.get(position);

        holder.bindTitle(movie.getTitle());
        holder.bindGenre(movie.getGenre());
        holder.bindYear(movie.getYear());
    }
}
