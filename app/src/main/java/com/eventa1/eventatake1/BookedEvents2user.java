package com.eventa1.eventatake1;

import java.util.List;

public class BookedEvents2user {
    private String compName,eveName,image_url,price,tansID,usernname;
    //private List<Compete2user> bookList;
    public BookedEvents2user(){

    }

    public String getUsernname() {
        return usernname;
    }

    public void setUsernname(String usernname) {
        this.usernname = usernname;
    }

    public BookedEvents2user(String compName, String eveName, String image_url, String price, String tansID, String usernname) {
        this.compName = compName;
        this.eveName = eveName;
        this.image_url = image_url;
        this.price = price;
        this.tansID = tansID;
        this.usernname = usernname;
        //this.bookList = bookList;
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
