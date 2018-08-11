package com.friendfill.foodifie.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by FriendFill on 11-Feb-18.
 */

public class Review implements Serializable {
    int id, bill_id, customer_id;
    String customer_name, review, customer_image;
    int rate;
    Date date;

    public Review(int id, int bill_id, int customer_id, String customer_name, int rate, String review, String customer_image) {
        this.id = id;
        this.bill_id = bill_id;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.rate = rate;
        this.review = review;
        this.customer_image = customer_image;
    }

    public Review(int id, int bill_id, int customer_id, String customer_name, int rate, String review) {
        this.id = id;
        this.bill_id = bill_id;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.rate = rate;
        this.review = review;
    }

    public Review(int id, int bill_id, int customer_id, String customer_name, int rate, String review, String customer_image, Date date) {
        this.id = id;
        this.bill_id = bill_id;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_image = customer_image;
        this.rate = rate;
        this.review = review;
        this.date = date;
    }

    public String getCustomer_image() {
        return customer_image;
    }

    public void setCustomer_image(String customer_image) {
        this.customer_image = customer_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
