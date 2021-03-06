package com.riders.testing.model;

import com.google.api.client.util.Key;

import java.io.Serializable;
import java.util.List;
/**
 * Created by michael on 12/09/2016.
 */

/** Implement this class from "Serializable"
 * So that you can pass this class Object to another using Intents
 * Otherwise you can't pass to another actitivy
 * */
public class PlacesList implements Serializable {

    @Key
    public String status;

    @Key
    public List<Place> results;

}