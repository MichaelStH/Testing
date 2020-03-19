package com.riders.testing.views;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.riders.testing.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.row_card_view)
    public CardView cardView;

    @BindView(R.id.row_name_text_view)
    public TextView nameTextView;

    @BindView(R.id.row_details_linear_layout)
    public LinearLayout detailsLinearLayout;

    public MyRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void setNameText(String name) {
        nameTextView.setText(name);
    }
}
