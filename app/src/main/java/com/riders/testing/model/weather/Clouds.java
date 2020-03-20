package com.riders.testing.model.weather;

import com.google.gson.annotations.SerializedName;

public class Clouds {

    @SerializedName("all")
    int cloudiness;

    public int getCloudiness() {
        return cloudiness;
    }
}
