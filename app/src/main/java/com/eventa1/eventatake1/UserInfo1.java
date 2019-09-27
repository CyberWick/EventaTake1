package com.eventa1.eventatake1;

public class UserInfo1 {
    private String usrname;
    private String dob;
    private String phnno;
    public UserInfo1(String usrname, String dob, String phnno){
        this.usrname = usrname;
        this.phnno = phnno;
        this.dob = dob;

    }
    public UserInfo1(){

    }
    public String getUsrname() {
        return usrname;
    }

    public String getDob() {
        return dob;
    }

    public String getPhnno() {
        return phnno;
    }
}
