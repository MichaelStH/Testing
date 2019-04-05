package com.riders.testing.interfaces;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.riders.testing.model.Video;


public interface YoutubeListClickListener {
    void onYoutubeItemClicked(@NonNull ImageView view, Video video, int position);
}
