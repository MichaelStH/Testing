package com.riders.testing.rest.client;

import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.riders.testing.application.MyApplication;
import com.riders.testing.constants.Const;
import com.riders.testing.model.WeatherKeyModel;
import com.riders.testing.rest.api.WeatherApiService;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
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

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String message) {
                Log.d("OkHttp", message);
            }
        });

//        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                HttpUrl url = null;

                String json = null;
                WeatherKeyModel model;
                JSONObject obj = new JSONObject();
                AssetManager mAssetManager = MyApplication.getInstance().getResources().getAssets();

                try {

                    InputStream is = mAssetManager
                            .open("weather_api.json");

                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();

                    json = new String(buffer, "UTF-8");

                    obj = new JSONObject(json);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                }


                try {
                    url = originalHttpUrl.newBuilder()
                            .addQueryParameter("appid", (String) obj.get("appid"))
                            .addQueryParameter("units", "metric")
                            .build();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                }

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
