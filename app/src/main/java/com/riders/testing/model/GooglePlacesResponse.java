package com.riders.testing.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by michael on 12/09/2016.
 */
@Parcel
public class GooglePlacesResponse {

    @SerializedName("error_message")
    public String error;

    @SerializedName("html_attributions")
    public String htmlAttributions;

    public ArrayList<Results> results;

    @SerializedName("status")
    public String status;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(String htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public ArrayList<Results> getResults() {
        return results;
    }

    public void setResults(ArrayList<Results> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
