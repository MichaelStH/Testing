package com.riders.testing.model;

import android.app.Activity;

public class ActivityItem {

    private String title;
    private String description;
    private int icon;
    private Class<? extends Activity> activity;

    public ActivityItem(String title, String description, int icon, Class<? extends Activity> activity) {
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.activity = activity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Class<? extends Activity> getActivity() {
        return activity;
    }

    public void setActivity(Class<? extends Activity> activity) {
        this.activity = activity;
    }
}
