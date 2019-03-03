package com.riders.testing.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.riders.testing.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActivityViewHolder extends RecyclerView.ViewHolder {

    private Context context;

    @BindView(R.id.row_item_cardView)
    public CardView itemCardView;

    @BindView(R.id.row_icon_imageView)
    ImageView iconImageView;

    @BindView(R.id.row_title_textView)
    TextView titleTextView;


    @BindView(R.id.row_description_textView)
    TextView descriptionTextView;


    public ActivityViewHolder(Context context, View itemView) {
        super(itemView);

        this.context = context;

        ButterKnife.bind(this, itemView);
    }

    public void bindData(String title, String description, int icon) {

        Glide.with(context)
                .load(icon)
                .into(iconImageView);

        titleTextView.setText(title);
        descriptionTextView.setText(description);
    }
}
