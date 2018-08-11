package com.friendfill.foodifie.model;

import java.io.Serializable;

/**
 * Created by FriendFill on 27-Jan-18.
 */

public class Staff implements Serializable {
    int id;
    String name, designation, shift;
    String email, phone;
    String image;
    boolean isLoggedIn;

    public Staff(int id, String name, String designation, String shift, String email, String phone, boolean isLoggedIn, String image) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.shift = shift;
        this.email = email;
        this.phone = phone;
        this.isLoggedIn = isLoggedIn;
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
