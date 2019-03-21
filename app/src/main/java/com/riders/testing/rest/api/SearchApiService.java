package com.riders.testing.rest.api;

import com.riders.testing.model.RequestResponse;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by michael on 09/03/2016.
 */

public interface SearchApiService<T> {

    @GET("/ajax/services/search/web?v=1.0")
    RequestResponse.Model getResults(@Query("q") String request);

    @GET("/ajax/services/search/web?v=1.0")
    RequestResponse.Model getResults(@Query("q") String request, @Query("userip") String userIp);

    @GET("/ajax/services/search/web?v=1.0")
    RequestResponse.Model getResults(@Query("q") String request, @Query("start") int Start, @Query("userip") String userIp);
}
