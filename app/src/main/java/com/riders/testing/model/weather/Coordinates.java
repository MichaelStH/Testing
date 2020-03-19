package com.riders.testing.model.weather;

import com.google.gson.annotations.SerializedName;

public class Coordinates {

    @SerializedName("lon")
    int longitude;

    @SerializedName("lat")
    int latitude;

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }
}
