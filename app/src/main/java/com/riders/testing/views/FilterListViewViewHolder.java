package com.riders.testing.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.riders.testing.R;

import butterknife.BindView;

public class FilterListViewViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.rank)
    TextView rank;
    @BindView(R.id.country)
    TextView country;
    @BindView(R.id.population)
    TextView population;


    public FilterListViewViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
