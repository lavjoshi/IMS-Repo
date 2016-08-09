package com.example.PurchaseOrder;



import com.example.PurchaseItems.PurchaseItem;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * Created by lav on 2/8/16.
 */
@Entity

public class PurchaseOrder {
    @Id
    @GeneratedValue
    int purchaseno;

    Date date;

    String vendorname;

    String username;

    float totalCost;
    final  float tax=5.0f;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="purchase_order_purchaseno")
   Set<PurchaseItem> purchaseItems;


    public int getPurchaseno() {
        return purchaseno;
    }

    public void setPurchaseno(int purchaseno) {
        this.purchaseno = purchaseno;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getVendorname() {
        return vendorname;
    }

    public void setVendorname(String vendorname) {
        this.vendorname = vendorname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public float getTax() {
        return tax;
    }

    public Set<PurchaseItem> getPurchaseItems() {
        return purchaseItems;
    }

    public void setPurchaseItems(Set<PurchaseItem> purchaseItems) {
        this.purchaseItems = purchaseItems;
    }
}
