package com.riders.testing.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.riders.testing.R;
import com.riders.testing.activities.FilterListViewDetailActivity;
import com.riders.testing.model.WorldPopulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by michael on 12/01/2016.
 */
public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater mInflater;
    private List<WorldPopulation> worldpopulationlist = null;
    private ArrayList<WorldPopulation> arraylist;


    public ListViewAdapter(Context context, List<WorldPopulation> worldpopulationlist)
    {
        mContext = context;
        this.worldpopulationlist = worldpopulationlist;
        mInflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<WorldPopulation>();
        this.arraylist.addAll(worldpopulationlist);
    }


    public class ViewHolder{
        TextView rank;
        TextView country;
        TextView population;
    }


    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    @Override
    public Object getItem(int position) {
        return worldpopulationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.row_filter_listview, null);
            // Locate the TextViews in listview_item.xml
            holder.rank = (TextView) convertView.findViewById(R.id.rank);
            holder.country = (TextView) convertView.findViewById(R.id.country);
            holder.population = (TextView) convertView.findViewById(R.id.population);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Set the results into TextViews
        holder.rank.setText(worldpopulationlist.get(position).getRank());
        holder.country.setText(worldpopulationlist.get(position).getCountry());
        holder.population.setText(worldpopulationlist.get(position).getPopulation());

        // Listen for ListView Item Click
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, FilterListViewDetailActivity.class);
                // Pass all data rank
                intent.putExtra("rank", (worldpopulationlist.get(position).getRank()));
                // Pass all data country
                intent.putExtra("country", (worldpopulationlist.get(position).getCountry()));
                // Pass all data population
                intent.putExtra("population", (worldpopulationlist.get(position).getPopulation()));
                // Pass all data flag
                // Start SingleItemView Class
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(arraylist);
        }
        else
        {
            for (WorldPopulation wp : arraylist)
            {
                if (wp.getCountry().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    worldpopulationlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
