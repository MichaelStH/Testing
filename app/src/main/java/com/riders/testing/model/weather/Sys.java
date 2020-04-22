package com.riders.testing.model.weather;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Sys {

    @Expose
    int type;

    @Expose
    int id;

    @Expose
    String country;

    @Expose
    long sunrise;

    @Expose
    long sunset;
}
