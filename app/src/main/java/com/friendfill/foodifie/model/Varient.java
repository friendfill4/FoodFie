package com.friendfill.foodifie.model;

/**
 * Created by FriendFill on 09-Aug-18.
 */

public class Varient {
    int id;
    int item_id;
    String name;
    Double qty;
    Double price;
    Double total;

    public Varient(int id, int item_id, String name, Double qty, Double price, Double total) {
        this.id = id;
        this.item_id = item_id;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
