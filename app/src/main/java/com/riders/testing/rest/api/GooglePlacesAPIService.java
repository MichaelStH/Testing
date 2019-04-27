package com.riders.testing.rest.api;


import com.riders.testing.model.GooglePlacesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by michael on 12/09/2016.
 */
public interface GooglePlacesAPIService {

    @GET("search/json")
    Call<GooglePlacesResponse> getPlaces(@Query("radius") int radius, @Query("key") String api_key);
}
