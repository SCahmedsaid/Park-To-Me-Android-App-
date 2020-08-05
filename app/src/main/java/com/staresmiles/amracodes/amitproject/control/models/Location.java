package com.staresmiles.amracodes.amitproject.control.models;

import java.io.Serializable;

/**
 * Created by amra on 5/7/2018.
 */

public class Location implements Serializable {
    private String latitude;
    private String logitude;
    private String name;


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLogitude() {
        return logitude;
    }

    public void setLogitude(String logitude) {
        this.logitude = logitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
