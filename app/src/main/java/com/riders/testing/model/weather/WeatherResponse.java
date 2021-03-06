package com.riders.testing.model.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WeatherResponse {

    @SerializedName("coord")
    Coordinates coordinates;

    @Expose
    List<Weather> weather;

    @Expose
    String base;

    @Expose
    Main main;

    @Expose
    int visibility;

    @Expose
    Rain rain;

    @Expose
    Clouds clouds;

    @Expose
    long dt;

    @Expose
    Sys sys;

    @Expose
    int timezone;

    @Expose
    int id;

    @Expose
    String name;

    @Expose
    int cod;
}
