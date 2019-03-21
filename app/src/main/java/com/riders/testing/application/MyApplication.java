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
import com.riders.testing.rest.client.ProjectPreviewRestClient;
import com.riders.testing.rest.client.SearchApiRestClient;

import io.fabric.sdk.android.Fabric;

public class MyApplication extends Application {


    public static final String TAG = MyApplication.class.getSimpleName();

    private static Context context;
    private static MyApplication mInstance;

    //Volley
    private RequestQueue mRequestQueue;


    private static ProjectPreviewRestClient mProjectPreviewRestClient;
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

        context = this;


        mProjectPreviewRestClient = new ProjectPreviewRestClient();
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
    public static ProjectPreviewRestClient getProjectPreviewApiRestClient() {

        Log.i(TAG, "return : getProjectPreviewApiRestClient()");
        return mProjectPreviewRestClient;
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

}
