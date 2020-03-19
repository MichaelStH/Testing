package com.riders.testing.rest.client;

import android.util.Log;

import com.riders.testing.constants.Const;
import com.riders.testing.rest.api.GooglePlacesAPIService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by michael on 12/09/2016.
 */
public class GooglePlacesRestClient {

    private static final String TAG = GooglePlacesRestClient.class.getSimpleName();


    private GooglePlacesAPIService apiRestService;

    /**
     * Constructor
     */
    public GooglePlacesRestClient() {
        Log.i(TAG, "Construct");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_ENDPOINT_GOOGLE_PLACES)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiRestService = retrofit.create(GooglePlacesAPIService.class);
    }


    /**
     * Return the api service
     *
     * @return
     */
    public GooglePlacesAPIService getApiService() {
        return apiRestService;
    }
}
