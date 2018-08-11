package com.friendfill.foodifie.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by FriendFill on 19-Jan-18.
 */

public class Item implements Serializable {

    int id, bill_id, customer_id;
    String name, description, category, serving, review;
    String image;
    double qty, rating;
    double price;
    ArrayList<Varient> varients;

    public Item(int id, String name, String description, String category, String serving, String image, double rating, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.serving = serving;
        this.image = image;
        this.qty = qty;
        this.rating = rating;
        this.price = price;
    }

    public Item(int id, String name, String description, String category, String serving, String image, double rating, double price, ArrayList<Varient> varients) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.serving = serving;
        this.image = image;
        this.qty = qty;
        this.rating = rating;
        this.price = price;
        this.varients = varients;
    }

    public Item(int id, String name, String description, String category, String image, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.image = image;
        this.price = price;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public ArrayList<Varient> getVarients() {
        return varients;
    }

    public void setVarients(ArrayList<Varient> varients) {
        this.varients = varients;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item) {
            return ((Item) obj).getId() == this.getId();
        }

        return false;
    }
}
