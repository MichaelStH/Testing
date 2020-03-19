package com.riders.testing.rest.client;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.riders.testing.constants.Const;
import com.riders.testing.model.WeatherKeyModel;
import com.riders.testing.rest.api.WeatherApiService;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApiRestClient {

    private static final String TAG = WeatherApiRestClient.class.getSimpleName();

    private WeatherApiService apiService;

    /**
     * Constructor
     */
    public WeatherApiRestClient() {
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        OkHttpClient client;
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                Type WEATHER_KEY_TYPE = new TypeToken<WeatherKeyModel>() {
                }.getType();
                Gson gson = new Gson();
                JsonReader reader = new JsonReader(new FileReader("/assets/weather_api.json"));

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("appid",
                                gson.fromJson(reader, WEATHER_KEY_TYPE))
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_ENDPOINT_WEATHER)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(WeatherApiService.class);
    }

    public WeatherApiService getApiService() {
        return apiService;
    }
}
