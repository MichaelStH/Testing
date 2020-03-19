package com.riders.testing.rest.client;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.riders.testing.constants.Const;
import com.riders.testing.rest.api.YoutubeApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YoutubeRestClient {
    private static final String TAG = YoutubeRestClient.class.getSimpleName();

    private YoutubeApiService apiService;

    /**
     * Constructor
     */
    public YoutubeRestClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_ENDPOINT_YOUTUBE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(YoutubeApiService.class);
    }

    public YoutubeApiService getApiService() {
        return apiService;
    }
}
