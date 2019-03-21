package com.riders.testing.model;

/**
 * Created by michael on 16/02/2016.
 */
public class VisitUsModel {

    private String title;
    private String link;
    private boolean active;

    public VisitUsModel() {

    }

    public VisitUsModel(String title, String link) {
        this.title = title;
        this.link = link;
    }


    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setLink(String link) {
        this.link = link;
    }
    public String getLink() {
        return link;
    }

    public boolean isSelected() {
        return active;
    }
    public void setSelected(boolean active) {
        this.active = active;
    }
}