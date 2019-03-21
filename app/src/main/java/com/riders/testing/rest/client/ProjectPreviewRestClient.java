package com.riders.testing.rest.client;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.riders.testing.rest.api.ProjectPreviewAPIService;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by michael on 14/04/2016.
 */
public class ProjectPreviewRestClient {

    private static final String TAG = ProjectPreviewRestClient.class.getSimpleName();

    private static final String BASE_ENDPOINT = "https://raw.githubusercontent.com";

    private ProjectPreviewAPIService apiRestService;

    /**
     * Constructor
     */
    public ProjectPreviewRestClient() {
        Log.i(TAG, "Construct");

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .build();


        apiRestService = restAdapter.create(ProjectPreviewAPIService.class);
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
    public ProjectPreviewAPIService getApiService() {
        Log.i(TAG, "Method public SearchApiRest getApiService()");
        return apiRestService;
    }
}
