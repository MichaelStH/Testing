package com.riders.testing.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.riders.testing.R;
import com.riders.testing.adapters.RecyclerViewAdapter;
import com.riders.testing.constants.Const;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecyclerViewActivity extends AppCompatActivity {

    private Context context;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        context = this;

        ButterKnife.bind(this);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(Const.getRecyclerItems());

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);

    }
}
