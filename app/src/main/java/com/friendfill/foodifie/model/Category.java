package com.friendfill.foodifie.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by FriendFill on 19-Jan-18.
 */

public class Category implements Serializable {
    int id;
    String name, description;
    ArrayList<Item> items;

    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Category(int id, String name, String description, ArrayList<Item> items) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.items = items;
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

    public void setName(String title) {
        this.name = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
