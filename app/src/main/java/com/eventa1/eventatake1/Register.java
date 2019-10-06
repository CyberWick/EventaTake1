package com.eventa1.eventatake1;

import java.util.ArrayList;
import java.util.List;

public class Register {
    private String Col,Eve,Des,Cat,image_url,contact_number,Date,EndDate;
    List<String> mList=new ArrayList<>();

    public String getDate() {
        return Date;
    }

    public Register(String col, String eve, String des, String cat, String image_url, String contact_number, String date, String endDate, List<String> mList) {
        Col = col;
        Eve = eve;
        Des = des;
        Cat = cat;
        this.image_url = image_url;
        this.contact_number = contact_number;
        Date = date;
        EndDate = endDate;
        this.mList = mList;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public List<String> getmList() {
        return mList;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_no) {
        this.contact_number = contact_no;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setmList(List<String> mList) {
        this.mList = mList;
    }

    public Register() {
    }

    public String getCol() {
        return Col;
    }

    public void setCol(String col) {
        Col = col;
    }

    public String getEve() {
        return Eve;
    }

    public void setEve(String eve) {
        Eve = eve;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }

    public String getCat() {
        return Cat;
    }

    public void setCat(String cat) {
        Cat = cat;
    }
}

