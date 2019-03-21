package com.riders.testing.constants;

import com.riders.testing.R;
import com.riders.testing.activities.*;
import com.riders.testing.model.ActivityItem;
import com.riders.testing.model.RecyclerItem;

import java.util.ArrayList;


public class Const {

    public static ArrayList<ActivityItem> activityItems;
    static ArrayList<RecyclerItem> recyclerItems;

    // Activity Recognition
    public static final String BROADCAST_DETECTED_ACTIVITY = "activity_intent";
    public static final long DETECTION_INTERVAL_IN_MILLISECONDS = 30 * 1000;
    public static final int CONFIDENCE = 70;


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

        activityItems.add(new ActivityItem("Colors", "Change color programmatically...",
                R.drawable.logo_colors, ColorActivity.class));

        activityItems.add(new ActivityItem("Recycler", "Recycler Basics and best practices...",
                R.drawable.ic_launcher_background, RecyclerViewActivity.class));

        activityItems.add(new ActivityItem("Transition", "Transition, shared elements...",
                R.drawable.transition_fade, TransitionActivity.class));

        activityItems.add(new ActivityItem("Tabs", "ViewPager Fragments Tabs...",
                R.drawable.ic_tab_black_48dp, WorkingTabsActivity.class));

        activityItems.add(new ActivityItem("Floating Labels", "Floating Labels Form...",
                R.drawable.ic_label_outline_black_48dp, FloatingLabelsActivity.class));

        activityItems.add(new ActivityItem("Location Google API", "Find user location and display coordinates...",
                R.drawable.ic_location_on_black_24dp, LocationGoogleAPIActivity.class));


        activityItems.add(new ActivityItem("WIP", "Coming soon...",
                R.drawable.ic_warning, null));

        return activityItems;
    }


    public static ArrayList<RecyclerItem> getRecyclerItems() {

        recyclerItems = new ArrayList<>();

        recyclerItems.add(new RecyclerItem("Dwayne Johnson"));
        recyclerItems.add(new RecyclerItem("Majd Bayassi"));
        recyclerItems.add(new RecyclerItem("Nesrine"));
        recyclerItems.add(new RecyclerItem("Mourad"));
        recyclerItems.add(new RecyclerItem("Mike Tyson"));
        recyclerItems.add(new RecyclerItem("Lyes"));
        recyclerItems.add(new RecyclerItem("Cedric"));
        recyclerItems.add(new RecyclerItem("Michael B. J."));
        recyclerItems.add(new RecyclerItem("Ken Iverson"));
        recyclerItems.add(new RecyclerItem("Max"));
        recyclerItems.add(new RecyclerItem("Halley Becksmann"));
        recyclerItems.add(new RecyclerItem("Carol Danvers"));
        recyclerItems.add(new RecyclerItem("Steph Dann"));
        recyclerItems.add(new RecyclerItem("Danny Walter"));
        recyclerItems.add(new RecyclerItem("Carlos Esposito"));
        recyclerItems.add(new RecyclerItem("Wilmer"));
        recyclerItems.add(new RecyclerItem("Gilles"));
        recyclerItems.add(new RecyclerItem("Yohan"));

        return recyclerItems;
    }
}
