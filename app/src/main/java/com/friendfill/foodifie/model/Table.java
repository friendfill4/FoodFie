package com.friendfill.foodifie.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by FriendFill on 19-Jan-18.
 */

public class Table implements Serializable {
    int id;
    String title;
    String description;
    int capacity;
    int adjustment;
    int status;
    Customer current_customer;
    Date booked_from;
    Date booked_to;

    public Table(int id, String title, String description, int capacity, int adjustment, int status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.adjustment = adjustment;
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(int adjustment) {
        this.adjustment = adjustment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Customer getCurrent_customer() {
        return current_customer;
    }

    public void setCurrent_customer(Customer current_customer) {
        this.current_customer = current_customer;
    }

    public Date getBooked_from() {
        return booked_from;
    }

    public void setBooked_from(Date booked_from) {
        this.booked_from = booked_from;
    }

    public Date getBooked_to() {
        return booked_to;
    }

    public void setBooked_to(Date booked_to) {
        this.booked_to = booked_to;
    }
}
