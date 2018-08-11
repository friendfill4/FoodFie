package com.friendfill.foodifie.model;

/**
 * Created by FriendFill on 31-Jan-18.
 */

public class Cart {
    String customer_name, customer_email, customer_phone, customer_address, customer_birthdate, table_id, bill_id;

    public Cart(String customer_name, String customer_email, String customer_phone, String customer_address, String customer_birthdate, String table_id, String bill_id) {
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.customer_phone = customer_phone;
        this.customer_address = customer_address;
        this.customer_birthdate = customer_birthdate;
        this.table_id = table_id;
        this.bill_id = bill_id;
    }

    public Cart(String customer_name, String customer_email, String customer_phone, String customer_address, String customer_birthdate, String table_id) {
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.customer_phone = customer_phone;
        this.customer_address = customer_address;
        this.customer_birthdate = customer_birthdate;
        this.table_id = table_id;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_birthdate() {
        return customer_birthdate;
    }

    public void setCustomer_birthdate(String customer_birthdate) {
        this.customer_birthdate = customer_birthdate;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }
}
