package com.riders.testing.model;

import com.google.gson.annotations.Expose;

public class WeatherKeyModel {

    @Expose
    public String appid;

    public String getAppid() {
        return appid;
    }
}
