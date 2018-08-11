package com.friendfill.foodifie.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by FriendFill on 19-Jan-18.
 */

public class Customer implements Serializable {
    int id;
    String name, mobile, email, address;
    Date birthdate;

    public Customer(int id, String name, String mobile, String email, String address, Date birthdate) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.birthdate = birthdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
