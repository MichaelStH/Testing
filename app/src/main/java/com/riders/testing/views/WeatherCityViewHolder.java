package com.riders.testing.views;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.riders.testing.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherCityViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.row_item_card_view)
    public CardView itemCardView;
    @BindView(R.id.row_city_name_textView)
    TextView cityNameTextView;

    // Constructor
    public WeatherCityViewHolder(@NonNull View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bindName(String name) {
        cityNameTextView.setText(name);
    }

}