package com.riders.testing.rest.client;

import android.util.Log;
import com.riders.testing.rest.api.GooglePlacesAPIService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by michael on 12/09/2016.
 */
public class GooglePlacesRestClient {

    private static final String TAG = GooglePlacesRestClient.class.getSimpleName();

    private static final String BASE_ENDPOINT = "https://maps.googleapis.com/maps/api/place/";

    private GooglePlacesAPIService apiRestService;

    /**
     * Constructor
     */
    public GooglePlacesRestClient() {
        Log.i(TAG, "Construct");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiRestService = retrofit.create(GooglePlacesAPIService.class);

        Log.i(TAG, "ApiService = restAdapter.create(MyApiService.class)");

    }


    /**
     * Return the api service
     *
     * @return
     */
    public GooglePlacesAPIService getApiService() {
        Log.i(TAG, "Method public GooglePlacesAPIService getApiService()");
        return apiRestService;
    }
}
