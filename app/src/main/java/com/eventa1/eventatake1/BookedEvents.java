package com.eventa1.eventatake1;

public class BookedEvents {
    private String compName,eveName,image_url,price,tansID,date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BookedEvents(){

    }
    public BookedEvents(String compName, String eveName, String image_url, String price, String tansID,String date) {
        this.compName = compName;
        this.eveName = eveName;
        this.image_url = image_url;
        this.price = price;
        this.tansID = tansID;
        this.date = date;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getEveName() {
        return eveName;
    }

    public void setEveName(String eveName) {
        this.eveName = eveName;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTansID() {
        return tansID;
    }

    public void setTansID(String tansID) {
        this.tansID = tansID;
    }
}
