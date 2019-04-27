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

        activityItems.add(new ActivityItem("Contact List", "Contact List...",
                R.drawable.ic_contacts_black_48dp, ContactListActivity.class));

        activityItems.add(new ActivityItem("Location On Maps", "Display User location on map...",
                R.drawable.ic_location_on_black_48dp, LocationOnMapsActivity.class));

        activityItems.add(new ActivityItem("Schedule Job", "Own alarm to remind user...",
                R.drawable.ic_schedule_black_48dp, ScheduleJobActivity.class));

        activityItems.add(new ActivityItem("Devices Informations", "Display device info...",
                R.drawable.ic_perm_device_information_black_48dp, DeviceInformationsActivity.class));

        activityItems.add(new ActivityItem("Palette", "Get different color from an image",
                R.drawable.ic_palette_black_48dp, PaletteActivity.class));

        activityItems.add(new ActivityItem("Filter ListView","ListView with filter..." ,
                R.drawable.ic_filter_list_black_48dp, FilterListViewActivity.class));

        activityItems.add(new ActivityItem("Multi Pane", "Display content on split screen...",
                R.drawable.ic_aspect_ratio_black_48dp, MultiPaneActivity.class));
/*
        listActivities.add( new ItemActivity("Contacts Database Activity", R.drawable.ic_perm_contact_calendar_black_48dp, ContactsDatabaseActivity.class) );
        listActivities.add( new ItemActivity("Search Box Activity", R.drawable.ic_alarm_black_48dp, SearchBoxActivity.class) );
        listActivities.add( new ItemActivity("Bandeau Pictures Activity", R.drawable.ic_picture_in_picture_black_48dp, BandeauPicturesActivity.class) );
        listActivities.add( new ItemActivity("Send Email Activity", R.drawable.ic_email_black_48dp, SendMailActivity.class) );
        listActivities.add( new ItemActivity("RecyclerView Basics Activity", R.drawable.ic_filter_list_black_48dp, RecyclerBasicsActivity.class) );
        */

        activityItems.add(new ActivityItem("RxJava", "Reactive Android...",
                R.drawable.logo_rx_java, RxJavaActivity.class));

        activityItems.add(new ActivityItem("Built-in Web View", "Display web view in activity directly...",
                R.drawable.ic_aspect_ratio_black_48dp, BuiltInWebViewActivity.class));

        activityItems.add(new ActivityItem("Songs Player (Work In Progress)", "Display content on split screen...",
                R.mipmap.ic_launcher_round, SongPlayerActivity.class));

        activityItems.add(new ActivityItem("Youtube", "Youtube look like...",
                R.drawable.youtube_icon_like, YoutubeActivity.class));

        activityItems.add(new ActivityItem("My Speech To Text", "Own speech to text implementation...",
                R.drawable.ic_mic, SpeechToTextActivity.class));

        activityItems.add(new ActivityItem("Activity Recognition", "Display user activity...",
                R.drawable.ic_walking, ActivityRecognitionActivity.class));


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
