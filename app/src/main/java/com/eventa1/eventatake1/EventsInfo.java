package com.eventa1.eventatake1;

import android.media.Image;

import java.util.ArrayList;
import java.util.List;

public class EventsInfo {
    String titile;
    List<String> events = new ArrayList<>();
    String image;

    public EventsInfo(){

    }
    public EventsInfo(String title,String image){
        this.titile = title;

        this.image = image;
    }

    public EventsInfo(String titile, List<String> events, String image) {
        this.titile = titile;
        this.events = events;
        this.image = image;
    }

    public String getTitile() {
        return titile;
    }

    public List<String> getEvents() {
        return events;
    }

    public String getImage() {
        return image;
    }
}
