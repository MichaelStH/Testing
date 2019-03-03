package com.riders.testing.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.riders.testing.R;
import com.riders.testing.interfaces.ActivityListClickListener;
import com.riders.testing.model.ActivityItem;
import com.riders.testing.views.ActivityViewHolder;

import java.util.ArrayList;


public class ActivityListAdapter extends RecyclerView.Adapter<ActivityViewHolder> {

    private Context context;
    private ArrayList<ActivityItem> activityListItem;
    private ActivityListClickListener listener;

    public ActivityListAdapter(Context context, ArrayList<ActivityItem> activityListItem, ActivityListClickListener listener) {
        this.context = context;
        this.activityListItem = activityListItem;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        if (activityListItem != null) {
            return activityListItem.size();
        }
        return 0;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ActivityViewHolder(context, LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_app_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ActivityViewHolder holder, int position) {
        final ActivityItem item = activityListItem.get(position);

        holder.bindData(item.getTitle(), item.getDescription(), item.getIcon());
        holder.itemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAppItemCLickListener(view, item, holder.getAdapterPosition());
            }
        });
    }

}
