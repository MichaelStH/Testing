package com.riders.testing.rest.client;

import android.util.Log;

import com.riders.testing.constants.Const;
import com.riders.testing.rest.api.SearchApiService;

import retrofit.RestAdapter;

/**
 * Created by michael on 09/03/2016.
 */
public class SearchApiRestClient {

    private static final String TAG = SearchApiRestClient.class.getSimpleName();

    private SearchApiService apiRestService;

    /**
     * Constructor
     */
    public SearchApiRestClient() {
    }

    public RestAdapter getRestAdapter() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Const.BASE_ENDPOINT_SEARCH)
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
