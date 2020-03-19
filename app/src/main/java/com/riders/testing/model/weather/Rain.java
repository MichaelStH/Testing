package com.riders.testing.model.weather;

import com.google.gson.annotations.SerializedName;

public class Rain {

    // Rain volume for the last 1 hour, mm
    @SerializedName("1h")
    double lastHour;

    // Rain volume for the last 3 hour, mm
    @SerializedName("3h")
    double lastThreeHour;

    public double getLastHour() {
        return lastHour;
    }

    public void setLastHour(double lastHour) {
        this.lastHour = lastHour;
    }

    public double getLastThreeHour() {
        return lastThreeHour;
    }

    public void setLastThreeHour(double lastThreeHour) {
        this.lastThreeHour = lastThreeHour;
    }
}
