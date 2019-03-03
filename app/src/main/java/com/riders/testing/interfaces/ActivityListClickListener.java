package com.riders.testing.interfaces;

import android.view.View;

import com.riders.testing.model.ActivityItem;


public interface ActivityListClickListener {
    void onAppItemCLickListener(View view, ActivityItem item, int position);
}
