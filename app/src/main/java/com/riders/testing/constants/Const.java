package com.riders.testing.constants;

import com.riders.testing.R;
import com.riders.testing.activities.FabricActivity;
import com.riders.testing.activities.HighChartsActivity;
import com.riders.testing.activities.ServiceActivity;
import com.riders.testing.model.ActivityItem;

import java.util.ArrayList;


public class Const {

    public static ArrayList<ActivityItem> activityItems;

    private Const() {
    }


    /**
     * Return app testing list
     * <p>
     * [Add here new app object]
     *
     * @return
     */
    public static ArrayList<ActivityItem> getActivites() {
        activityItems = new ArrayList<>();
        activityItems.add(new ActivityItem("Highcharts API", "Testing Highcharts API for Android ...",
                R.drawable.logo_highcharts, HighChartsActivity.class));

        activityItems.add(new ActivityItem("Android Services", "Android Services for any action...",
                R.drawable.logo_service, ServiceActivity.class));

        activityItems.add(new ActivityItem("Fabric", "You can test crash of app and have a detailed notification...",
                R.drawable.logo_fabric, FabricActivity.class));

        return activityItems;
    }
}
