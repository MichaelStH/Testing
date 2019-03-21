package com.riders.testing.model;

/**
 * Created by michael on 08/03/2016.
 */
public class SingersModel {

    private String singer;
    private String biography;
    private String urlThumbnail;


    public SingersModel(){

    }

    public SingersModel(String singer, String urlThumbnail) {

        this.singer = singer;
        this.urlThumbnail = urlThumbnail;
    }


    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

}
