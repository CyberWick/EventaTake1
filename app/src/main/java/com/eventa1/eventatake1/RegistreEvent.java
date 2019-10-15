package com.eventa1.eventatake1;

import java.util.ArrayList;
import java.util.List;

public class RegistreEvent {
    private String Col,Eve,Des,Cat,image_url,contact_number,Date,EndDate,mYear,hostedby;
    private List<BookedEvents2user> bookedEvents2users = new ArrayList<>();
    List<String> mList=new ArrayList<>();

    public RegistreEvent(String col, String eve, String des, String cat, String image_url, String contact_number, String date, String endDate, String mYear, String hostedby, List<BookedEvents2user> bookedEvents2users, List<String> mList) {
        Col = col;
        Eve = eve;
        Des = des;
        Cat = cat;
        this.image_url = image_url;
        this.contact_number = contact_number;
        Date = date;
        EndDate = endDate;
        this.mYear = mYear;
        this.hostedby = hostedby;
        this.bookedEvents2users = bookedEvents2users;
        this.mList = mList;
    }

    public String getmYear() {
        return mYear;
    }

    public void setmYear(String mYear) {
        this.mYear = mYear;
    }

    public String getDate() {
        return Date;
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

    public RegistreEvent()
    {

    }

    public String getHostedby() {
        return hostedby;
    }

    public List<BookedEvents2user> getBookedEvents2users() {
        return bookedEvents2users;
    }

    public void setBookedEvents2users(List<BookedEvents2user> bookedEvents2users) {
        this.bookedEvents2users = bookedEvents2users;
    }

    public void setHostedby(String hostedby) {
        this.hostedby = hostedby;
    }

    public RegistreEvent(String col, String eve, String des, String cat, String image_url, String contact_number, String date, String endDate, List<String> mList, List<BookedEvents2user> mBookList, String hostedby) {
        Col = col;
        Eve = eve;
        Des = des;
        Cat = cat;
        this.hostedby = hostedby;
        this.bookedEvents2users = mBookList;
        this.image_url = image_url;
        this.contact_number = contact_number;
        Date = date;
        EndDate = endDate;
        this.mList = mList;
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

