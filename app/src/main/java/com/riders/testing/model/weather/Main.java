package com.riders.testing.model.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Main {

    @SerializedName("temp")
    double temperature;

    @SerializedName("feels_like")
    double feelsLike;

    @SerializedName("temp_min")
    double minTemperature;

    @SerializedName("temp_max")
    double maxTemperature;

    @Expose
    int pressure;

    @Expose
    int humidity;
}
