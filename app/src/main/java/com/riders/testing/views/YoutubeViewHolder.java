package com.riders.testing.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.riders.testing.R;
import com.riders.testing.model.Video;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YoutubeViewHolder extends RecyclerView.ViewHolder {

    // TAG
    private static final String TAG = YoutubeViewHolder.class.getSimpleName();
    private Context context;

    // Views
    @BindView(R.id.card_view_item)
    public CardView itemCardView;

    @BindView(R.id.loader_item)
    ProgressBar itemLoader;
    @BindView(R.id.image_item)
    ImageView imageThumb;
    @BindView(R.id.name_item)
    TextView nameTextView;
    @BindView(R.id.description_item)
    TextView descriptionTextView;


    public YoutubeViewHolder(Context context, View view) {
        super(view);

        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public ImageView getImageView() {
        return this.imageThumb;
    }

    public void setImage(String imageURL) {
        Glide.with(context)
                .load(imageURL)
                .into(imageThumb);
    }

    public void setName(String name) {
        nameTextView.setText(name);

    }

    public void setDescription(String description) {

        descriptionTextView.setText(description);
    }

    public void bind(Video itemYoutubeVideo) {


        if (itemLoader != null) {
            itemLoader.setVisibility(View.VISIBLE);
        }

        //Call Picasso to display the correct image in each row view item
        /*Picasso.with(context)
                .load(itemYoutubeVideo.getImageThumb())
                .into(imageThumb, new ImageLoadedCallback(itemLoader) {
                    @Override
                    public void onSuccess() {
                        if (itemLoader != null) {
                            itemLoader.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {

                        imageThumb.setImageResource(R.mipmap.ic_launcher);

                        Log.e(TAG, "bandeau pictures - OOOOOOOHHH CA VA PAAAAAS LAAAAA !!!");

                        if (itemLoader != null) {
                            itemLoader.setVisibility(View.GONE);
                        }
                    }
                });*/


    }

}
