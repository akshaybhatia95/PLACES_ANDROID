package com.example.d.places;

import java.io.Serializable;

/**
 * Created by D on 4/18/2018.
 */

public class MyPlaces implements Serializable {
    String icon;
    String name;
    String vicinity;
    String Place_id;
    String lat;
    String lon;

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public String getPlace_id() {
        return Place_id;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public void setPlace_id(String place_id) {
        Place_id = place_id;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
