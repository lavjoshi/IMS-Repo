package com.example.PurchaseItems;

import com.example.PurchaseOrder.PurchaseOrder;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * Created by lav on 2/8/16.
 */
@Entity
@Table
public class PurchaseItem {
    @Id
    @GeneratedValue
    int itemid;

    String name;




    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    int quantity;

    float cost;


}
