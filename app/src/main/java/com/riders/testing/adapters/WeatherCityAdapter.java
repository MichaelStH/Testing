package com.riders.testing.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.riders.testing.R;
import com.riders.testing.model.weather.CityListModel;

import java.util.ArrayList;

/*
public class WeatherCityAdapter extends RecyclerView.Adapter<WeatherCityViewHolder>
        implements Filterable {

    private Context context;

    private List<CityListModel> mCityList;
    private List<CityListModel> mCityListFiltered;

    private CitiesAdapterListener listener;

    public interface CitiesAdapterListener {
        void onCitySelected(CityListModel city);
    }


    public WeatherCityAdapter(Context context, List<CityListModel> cityList, CitiesAdapterListener listener) {
        sortList(cityList);

        this.context = context;
        this.mCityList = cityList;
        this.mCityListFiltered = cityList;
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        if (mCityListFiltered != null) {
            return mCityListFiltered.size();
        }
        return 0;
    }


    @NonNull
    @Override
    public WeatherCityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherCityViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_city_spinner, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherCityViewHolder holder, final int position) {

        CityListModel city = mCityListFiltered.get(position);

        holder.bindName(city.getName() + "," + city.getCountry());

        holder.itemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send selected contact in callback
                listener.onCitySelected(mCityListFiltered.get(position));
            }
        });

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mCityListFiltered = mCityList;
                } else {
                    List<CityListModel> filteredList = new ArrayList<>();
                    for (CityListModel row : mCityList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mCityListFiltered = filteredList;
                }

                // Sort by name
                sortList(mCityListFiltered);

                FilterResults filterResults = new FilterResults();
                filterResults.values = mCityListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mCityListFiltered = (ArrayList<CityListModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private void sortList(List<CityListModel> list) {
        Collections.sort(list, new Comparator<CityListModel>() {
            @Override
            public int compare(CityListModel leftItem, CityListModel rightItem) {
                return leftItem.getName().compareTo(rightItem.getName());
            }
        });
    }
}
*/

/*
FOR SPINNER
*/
public class WeatherCityAdapter extends ArrayAdapter<CityListModel> {

    private static final String TAG = WeatherCityAdapter.class.getSimpleName();

    private Context context;
    private int viewResourceId;
    private ArrayList<CityListModel> items;
    private ArrayList<CityListModel> itemsAll;
    private ArrayList<CityListModel> suggestions;

    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((CityListModel) (resultValue)).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (CityListModel CityListModel : itemsAll) {
                    if (CityListModel.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(CityListModel);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<CityListModel> filteredList = (ArrayList<CityListModel>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (CityListModel c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };


    public WeatherCityAdapter(@NonNull Context context, int viewResourceId, @NonNull ArrayList<CityListModel> items) {
        super(context, viewResourceId, items);

        this.context = context;

        this.items = items;
        this.itemsAll = (ArrayList<CityListModel>) items.clone();
        this.suggestions = new ArrayList<CityListModel>();
        this.viewResourceId = viewResourceId;
    }

    @Override
    public int getCount() {
        if (null != items)
            return items.size();
        return 0;
    }

    @Nullable
    @Override
    public CityListModel getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void populateItem(CityListModel item) {
        items.add(item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(viewResourceId, null);
        }

        CityListModel cityItem = items.get(position);

        if (cityItem != null) {
            TextView tvCityName = (TextView) view.findViewById(R.id.row_city_name_textView);
            if (tvCityName != null) {

                String szCity = items.get(position).getName() +
                        context.getResources().getString(R.string.separator_placeholder) +
                        items.get(position).getCountry();

//                Log.i(TAG, "getView CityListModel Name: " + szCity);

                tvCityName.setText(szCity);
            }
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }
}
