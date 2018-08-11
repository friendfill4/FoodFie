package com.friendfill.foodifie.model;

/**
 * Created by FriendFill on 31-Jan-18.
 */

public class CartItem {
    int bill_id;
    int item_id;
    int table_id;
    double qty;

    public CartItem(int bill_id, int item_id, int table_id, double qty) {
        this.bill_id = bill_id;
        this.item_id = item_id;
        this.table_id = table_id;
        this.qty = qty;
    }

    public CartItem(int bill_id, int item_id, double qty) {
        this.bill_id = bill_id;
        this.item_id = item_id;
        this.qty = qty;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }
}
