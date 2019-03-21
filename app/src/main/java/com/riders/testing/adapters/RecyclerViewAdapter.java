package com.riders.testing.adapters;

import android.support.annotation.NonNull;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.riders.testing.R;
import com.riders.testing.model.RecyclerItem;
import com.riders.testing.views.MyRecyclerViewHolder;

import java.util.ArrayList;



public class RecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {

    private ArrayList<RecyclerItem> recyclerItemArrayList;
    private int mExpandedPosition = -1;
    private RecyclerView recyclerView = null;


    public RecyclerViewAdapter(ArrayList<RecyclerItem> recyclerItemArrayList) {
        this.recyclerItemArrayList = recyclerItemArrayList;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        this.recyclerView = recyclerView;
    }

    @Override
    public int getItemCount() {

        if (null != recyclerItemArrayList)
            return recyclerItemArrayList.size();
        return 0;
    }


    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyRecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, final int position) {

        RecyclerItem item = recyclerItemArrayList.get(position);

        holder.setNameText(item.getName());

        // This line checks if the item displayed on screen
        // was expanded or not (Remembering the fact that Recycler View )
        // reuses views so onBindViewHolder will be called for all
        // items visible on screen.
        final boolean isExpanded = position == mExpandedPosition;

        //This line hides or shows the layout in question
        holder.detailsLinearLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        // I do not know what the heck this is :)
        holder.itemView.setActivated(isExpanded);

        // Click event for each item (itemView is an in-built variable of holder class)
        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // if the clicked item is already expanded then return -1
                //else return the position (this works with notifyDataSetChanged )
                mExpandedPosition = isExpanded ? -1 : position;

                // fancy animations can skip if like
//                TransitionManager.beginDelayedTransition(recyclerView);

                //This will call the onBindViewHolder for all the itemViews on Screen
                notifyDataSetChanged();
            }
        });
    }
}
