package com.riders.testing.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.riders.testing.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by michael on 12/01/2016.
 */
public class FilterListViewDetailActivity extends AppCompatActivity {

    private static final String TAG = FilterListViewDetailActivity.class.getSimpleName();

    // Declare Variables
    @BindView(R.id.rank)
    TextView txtrank;
    @BindView(R.id.country)
    TextView txtcountry;
    @BindView(R.id.population)
    TextView txtpopulation;

    String rank;
    String country;
    String population;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_listview_detail);

        getSupportActionBar().setTitle(TAG);

        ButterKnife.bind(this);

        getBundle();

        loadData();
    }


    private void getBundle() {

        // Retrieve data from MainActivity on item click event
        Intent i = getIntent();
        // Get the results of rank
        rank = i.getStringExtra("rank");
        // Get the results of country
        country = i.getStringExtra("country");
        // Get the results of population
        population = i.getStringExtra("population");

    }

    private void loadData() {

        // Load the results into the TextViews
        txtrank.setText(rank);
        txtcountry.setText(country);
        txtpopulation.setText(population);
    }
}
