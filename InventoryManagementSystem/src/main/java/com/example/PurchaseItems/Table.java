package com.example.PurchaseItems;

import javax.persistence.Entity;

/**
 * Created by lav on 3/8/16.
 */

public class Table {
    String itemName[];
    float cost[];
    int quantity[];
    String vendorName;



    public Table()
    {}

    public Table(String vendorName, String[] itemName, float[] cost, int[] quantity) {
        this.vendorName = vendorName;
        this.itemName = itemName;
        this.cost = cost;
        this.quantity = quantity;
    }

    public String getVendorName() {

        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String[] getItemName() {
        return itemName;
    }

    public void setItemName(String[] itemName) {
        this.itemName = itemName;
    }

    public float[] getCost() {
        return cost;
    }

    public void setCost(float[] cost) {
        this.cost = cost;
    }

    public int[] getQuantity() {
        return quantity;
    }

    public void setQuantity(int[] quantity) {
        this.quantity = quantity;
    }
}
