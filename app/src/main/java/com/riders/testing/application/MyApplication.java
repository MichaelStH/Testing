package com.riders.testing.application;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.riders.testing.broadcast.ConnectivityReceiver;
import com.riders.testing.rest.client.SearchApiRestClient;
import com.riders.testing.rest.client.YoutubeRestClient;

import io.fabric.sdk.android.Fabric;

public class MyApplication extends Application {


    public static final String TAG = MyApplication.class.getSimpleName();

    private static Context context;
    private static MyApplication mInstance;

    //Volley
    private RequestQueue mRequestQueue;


    private static YoutubeRestClient youtubeRestClient;
    private static SearchApiRestClient mSearchApiRestClient;


    public MyApplication() {
        Log.i(TAG, "Construct de MyApplication");
        mInstance = this;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        //Init fabric stuff
        Fabric.with(this, new Answers(), new Crashlytics());

        mInstance = this;
        context = this;

        youtubeRestClient = new YoutubeRestClient();
        mSearchApiRestClient = new SearchApiRestClient();

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

        Log.i(TAG, "return : getSearchApiRestClient()");
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
