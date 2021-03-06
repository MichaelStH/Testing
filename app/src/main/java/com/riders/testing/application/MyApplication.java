package com.riders.testing.application;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.riders.testing.broadcast.ConnectivityReceiver;
import com.riders.testing.rest.client.GooglePlacesRestClient;
import com.riders.testing.rest.client.SearchApiRestClient;
import com.riders.testing.rest.client.WeatherApiRestClient;
import com.riders.testing.rest.client.YoutubeRestClient;


public class MyApplication extends MultiDexApplication {


    public static final String TAG = MyApplication.class.getSimpleName();

    private static Context context;
    private static MyApplication mInstance;

    //Volley
    private RequestQueue mRequestQueue;


    private static YoutubeRestClient youtubeRestClient;
    private static SearchApiRestClient mSearchApiRestClient;
    private static GooglePlacesRestClient mGooglePlacesRestClient;
    private static WeatherApiRestClient mWeatherApiRestClient;


    public MyApplication() {
        Log.i(TAG, "Construct de MyApplication");
        mInstance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Init fabric stuff
        // Fabric.with(this, new Answers(), new Crashlytics());
        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
        crashlytics.setCrashlyticsCollectionEnabled(true);
        crashlytics.setUserId("myAppUserId");

        mInstance = this;
        context = this;

        youtubeRestClient = new YoutubeRestClient();
        mSearchApiRestClient = new SearchApiRestClient();
        mGooglePlacesRestClient = new GooglePlacesRestClient();
        mWeatherApiRestClient = new WeatherApiRestClient();

    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    public static Context getAppContext() {
        Log.i(TAG, "GetApplication de MyApplication");
        return context;
    }


    /**
     * Return the ApiRestClient object
     *
     * @return
     */
    public static SearchApiRestClient getSearchApiRestClient() {
        return mSearchApiRestClient;
    }

    /**
     * Return the ApiRestClient object
     *
     * @return
     */
    public YoutubeRestClient getYoutubeRestClient() {
        return youtubeRestClient;
    }

    /**
     * Return the ApiRestClient object
     *
     * @return
     */
    public static GooglePlacesRestClient getGooglePlacesApiRestClient() {
        return mGooglePlacesRestClient;
    }


    public WeatherApiRestClient getWeatherApiRestClient(){
        return mWeatherApiRestClient;
    }


    //Volley
    public RequestQueue getRequestQueue() {
        Log.i(TAG, "getRequestQueue");

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}
