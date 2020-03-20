package com.riders.testing.model.weather;

import com.google.gson.annotations.SerializedName;

public class CityListModel {

    int id;
    String name;
    String state;
    String country;
    @SerializedName("coord")
    Coordinates coordinates;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
