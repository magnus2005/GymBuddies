package com.mmc.mateusz.gymbuddies.utils;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable, Cloneable {
    private static final long serialVersionUID = 42L;

    private int phoneNumber;
    private String password=null;

    private String Name = null;
    private String City = null;
    private Date DateBirthday = null;

    private Boolean booBuddy=false;

    public User(){
    }

    public User(int pnr,String paswd){
        this.phoneNumber=pnr;
        this.password=paswd;
    }

    public void setPhoneNumber(int p){
        phoneNumber=p;
    }

    public void setBuddyStatus(Boolean status){
        booBuddy=status;
    }

    public Boolean getBuddyStatus(){
        return booBuddy;
    }
    public int getPhoneNumber(){
        return phoneNumber;
    }

    public void setPassword(String pass){
        password=pass;
    }

    public String getPassword(){
        return password;
    }

    public String getName(){
        return Name;
    }
    public void setName(String aName){
        Name=aName;
    }

    public void setCity(String aCity){
        City=aCity;
    }

    public String getCity(){
        return City;
    }

    public void setDateBirthday(int year, int month, int day){
        DateBirthday = new Date(year,month,day);
    }
    public void setDateBirthday(long date_birthday) {
        DateBirthday = new Date(date_birthday);
    }

    public long getDateBirthday(){
        return  DateBirthday.getTime();
    }


    // clone interface
    protected User clone() throws CloneNotSupportedException {
        return (User) super.clone();
    }


}
