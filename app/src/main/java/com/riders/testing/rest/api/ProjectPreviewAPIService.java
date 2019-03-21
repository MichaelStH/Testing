package com.riders.testing.rest.api;

import com.riders.testing.model.YoutubeItem;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by michael on 14/04/2016.
 */
public interface ProjectPreviewAPIService<T> {

    @GET("/florent37/MyYoutube/master/myyoutube.json")
    public void getYoutubeContent(Callback<List<YoutubeItem>> cb);

}
