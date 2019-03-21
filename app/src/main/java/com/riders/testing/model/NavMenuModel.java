package com.riders.testing.model;

/**
 * Created by michael on 03/02/2016.
 */
public class NavMenuModel {

    private String menuTitle;
    private int menuIcon;
    private boolean active;

    public NavMenuModel() {

    }

    public NavMenuModel(String menuTitle, int menuIcon) {
        this.menuTitle = menuTitle;
        this.menuIcon = menuIcon;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public int getMenuIcon() {
        return menuIcon;
    }

    public boolean isSelected() {
        return active;
    }
    public void setSelected(boolean active) {
        this.active = active;
    }
}
