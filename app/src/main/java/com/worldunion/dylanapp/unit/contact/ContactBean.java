package com.worldunion.dylanapp.unit.contact;

import java.io.Serializable;

/**
 * Created by Dylan on 2017/9/29.
 */

public class ContactBean implements Serializable{

    private String firstLetter;
    private String name;
    private String phoneNum;

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
