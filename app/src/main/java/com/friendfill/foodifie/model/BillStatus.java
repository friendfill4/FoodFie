package com.friendfill.foodifie.model;

/**
 * Created by FriendFill on 31-Jan-18.
 */

public class BillStatus {
    int bill_id;
    String status;

    public BillStatus(int bill_id, String status) {
        this.bill_id = bill_id;
        this.status = status;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
