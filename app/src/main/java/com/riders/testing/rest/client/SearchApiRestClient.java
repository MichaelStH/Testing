package com.riders.testing.rest.client;

import android.util.Log;

import com.riders.testing.rest.api.SearchApiService;

import retrofit.RestAdapter;

/**
 * Created by michael on 09/03/2016.
 */
public class SearchApiRestClient {

    private static final String TAG = SearchApiRestClient.class.getSimpleName();

    private static final String BASE_ENDPOINT = "https://ajax.googleapis.com";
    private SearchApiService apiRestService;

    /**
     * Constructor
     */
    public SearchApiRestClient() {
        Log.i(TAG, "Construct");

        Log.i(TAG, "ApiService = restAdapter.create(MyApiService.class)");

    }

    public RestAdapter getRestAdapter() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_ENDPOINT)
                .build();

        return restAdapter;
    }


    /**
     * Return the api service
     *
     * @return
     */
    public SearchApiService getApiService() {
        Log.i(TAG, "Method public SearchApiRest getApiService()");
        return apiRestService;
    }
}
