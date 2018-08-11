package com.friendfill.foodifie.model;

/**
 * Created by FriendFill on 31-Jan-18.
 */

public class Login {
    String username, password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
