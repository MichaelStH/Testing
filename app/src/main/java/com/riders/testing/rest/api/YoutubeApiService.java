package com.riders.testing.rest.api;

import com.riders.testing.model.Video;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.GET;

public interface YoutubeApiService {

    //Method to retrieve the youtube content
    @GET("/florent37/MyYoutube/master/myyoutube.json")
    Call<List<Video>> fetchYoutubeVideos();
}
