package com.riders.testing.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.riders.testing.R;
import com.riders.testing.interfaces.YoutubeListClickListener;
import com.riders.testing.model.Video;
import com.riders.testing.views.YoutubeViewHolder;

import java.util.List;


/**
 * Created by michael on 14/04/2016.
 */
public class YoutubeListAdapter extends RecyclerView.Adapter<YoutubeViewHolder> {

    private static final String TAG = YoutubeListAdapter.class.getSimpleName();

    private Context context;
    private List<Video> youtubeList;
    private YoutubeListClickListener listener;

    public YoutubeListAdapter(Context context, List<Video> youtubeList, YoutubeListClickListener listener) {

        this.context = context;
        this.youtubeList = youtubeList;

        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return youtubeList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public YoutubeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new YoutubeViewHolder(context, LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_youtube_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final YoutubeViewHolder holder, int position) {

        final Video itemYoutubeVideo = youtubeList.get(position);

        holder.setName(itemYoutubeVideo.getName());
        holder.setDescription(itemYoutubeVideo.getDescription());
        holder.setImage(itemYoutubeVideo.getImageThumb());

        holder.itemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onYoutubeItemClicked(holder.getImageView(), itemYoutubeVideo, holder.getAdapterPosition());
            }
        });
    }
}