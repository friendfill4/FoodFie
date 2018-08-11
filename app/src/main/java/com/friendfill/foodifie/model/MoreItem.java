package com.friendfill.foodifie.model;

/**
 * Created by FriendFill on 23-Jan-18.
 */

public class MoreItem {
    int id;
    String title;
    String image;


    public MoreItem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
