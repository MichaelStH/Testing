package com.riders.testing.utils;


import com.riders.testing.application.MyApplication;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

/**
 * Created by Michaël on 07/03/2017.
 */

public class PicassoSingleton {

    private static PicassoSingleton instance;

    private OkHttpClient okHttpClient = new OkHttpClient();
    private Picasso picasso = new Picasso.Builder(MyApplication.getAppContext())
//            .downloader(new OkHttpDownloader(okHttpClient))
            .build();

    public Picasso getPicasso() {
        return picasso;
    }

    private PicassoSingleton() {

    }

    public static PicassoSingleton getInstance() {

        if(instance == null){
            instance = new PicassoSingleton();
        }
        return instance;
    }
}
