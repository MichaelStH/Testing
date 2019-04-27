package com.riders.testing.model;

public class Movie {

    private String title, genre, year, urlThumbnail;

    public Movie() {
    }

    public Movie(String title, String genre, String year, String urlThumbnail) {
        this.title = title;
        this.genre = genre;
        this.year = year;

        this.urlThumbnail = urlThumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getUrlThumbnail (){
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail){
        this.urlThumbnail = urlThumbnail;
    }
}
