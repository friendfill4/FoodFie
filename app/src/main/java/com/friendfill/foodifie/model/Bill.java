package com.friendfill.foodifie.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by FriendFill on 21-Jan-18.
 */

public class Bill implements Serializable {
    int id;
    String bill_no;
    Date bill_date;
    double subtotal, discount, tax, grandtotal;
    Customer customer;
    Table table;
    int status;
    ArrayList<Item> items;

    public Bill(int id, String bill_no, Date bill_date, Customer customer, Table table, Double grandtotal, ArrayList<Item> items) {
        this.id = id;
        this.bill_no = bill_no;
        this.bill_date = bill_date;
        this.customer = customer;
        this.table = table;
        this.grandtotal = grandtotal;
        this.items = items;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public Date getBill_date() {
        return bill_date;
    }

    public void setBill_date(Date bill_date) {
        this.bill_date = bill_date;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getGrandtotal() {
        return grandtotal;
    }

    public void setGrandtotal(double grandtotal) {
        this.grandtotal = grandtotal;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
