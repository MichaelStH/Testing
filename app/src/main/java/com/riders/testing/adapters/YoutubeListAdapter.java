package com.riders.testing.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.riders.testing.R;
import com.riders.testing.model.YoutubeItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by michael on 14/04/2016.
 */
public class YoutubeListAdapter extends RecyclerView.Adapter<YoutubeListAdapter.MyViewHolder> {

    private static final String TAG = YoutubeListAdapter.class.getSimpleName();

    private Context context;
    private List<YoutubeItem> youtubeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar itemloader;
        public ImageView imageThumb;
        public TextView name, description;

        public MyViewHolder(View view) {
            super(view);

            itemloader = (ProgressBar) view.findViewById(R.id.project_preview_loader_item);

            imageThumb = (ImageView) view.findViewById(R.id.project_preview_image_item);

            name = (TextView) view.findViewById(R.id.project_preview_name_item);
            description = (TextView) view.findViewById(R.id.project_preview_description_item);
        }
    }


    public YoutubeListAdapter(Context context, List<YoutubeItem> youtubeList) {
        this.context = context;
        this.youtubeList = youtubeList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_preview_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        YoutubeItem item = youtubeList.get(position);

        if (holder.itemloader != null) {
            holder.itemloader.setVisibility(View.VISIBLE);
        }

        Picasso.with(context)
                .load(item.getImageThumb())
                /*.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE)*/
                .into(holder.imageThumb, new ImageLoadedCallback(holder.itemloader) {
                    @Override
                    public void onSuccess() {
                        holder.itemloader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                        holder.itemloader.setVisibility(View.GONE);
                        holder.imageThumb.setImageResource(R.mipmap.ic_launcher);

                        Log.e(TAG, "YoutubeList - OOOOOOOHHH CA VA PAAAAAS LAAAAA !!!");

                    }
                });

        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());

    }

    @Override
    public int getItemCount() {
        return youtubeList.size();
    }


    private class ImageLoadedCallback implements Callback {

        ProgressBar mProgressBar;

        public ImageLoadedCallback(ProgressBar progressBar) {
            mProgressBar = progressBar;
        }

        @Override
        public void onSuccess() {
            if (mProgressBar != null) {
                mProgressBar.setVisibility(View.GONE);
            }

            Log.i("ImageLoadedCallback", "onSuccess : ");
        }

        @Override
        public void onError() {
            if (mProgressBar != null) {
                mProgressBar.setVisibility(View.GONE);
            }
            Log.i("ImageLoadedCallback", "onError");
        }
    }
}