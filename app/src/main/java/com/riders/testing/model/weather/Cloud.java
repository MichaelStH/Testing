package com.riders.testing.model.weather;

import com.google.gson.annotations.Expose;

public class Cloud {

    @Expose
    int all;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }
}
