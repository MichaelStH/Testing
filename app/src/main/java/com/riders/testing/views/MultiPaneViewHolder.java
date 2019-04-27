package com.riders.testing.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.riders.testing.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MultiPaneViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.multi_pane_movie_item_title)
    TextView titleTextView;
    @BindView(R.id.multi_pane_movie_item_genre)
    TextView genreTextView;
    @BindView(R.id.multi_pane_movie_item_year)
    TextView yearTextView;

    public MultiPaneViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindTitle(String title) {
        titleTextView.setText(title);
    }

    public void bindGenre(String genre) {
        genreTextView.setText(genre);
    }

    public void bindYear(String year) {
        yearTextView.setText(year);
    }
}
