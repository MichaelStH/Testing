package com.riders.testing.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;

import com.riders.testing.model.Video;


public interface YoutubeListClickListener {
    void onYoutubeItemClicked(@NonNull ImageView view, Video video, int position);
}
