package com.riders.testing.views;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
