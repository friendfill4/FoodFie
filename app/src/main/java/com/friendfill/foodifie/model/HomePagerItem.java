package com.friendfill.foodifie.model;

/**
 * Created by FriendFill on 19-Jan-18.
 */

public class HomePagerItem {
    int id;
    String title;
    String subtitle;
    String image_src;

    public HomePagerItem(int id, String title, String subtitle, String image_src) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.image_src = image_src;
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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }
}
