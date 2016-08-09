package com.example.Production;

import com.example.ProductionItems.ProductionItem;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * Created by lav on 2/8/16.
 */
@Entity
public class Production {



    @Id
    @GeneratedValue

    int productionNo;

    String productName;

    Date date;

    int totalManufactured;

    String userName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="production_productionNo")
    Set<ProductionItem> productionItem;

    public int getProductionNo() {
        return productionNo;
    }

    public void setProductionNo(int productionNo) {
        this.productionNo = productionNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotalManufactured() {
        return totalManufactured;
    }

    public void setTotalManufactured(int totalManufactured) {
        this.totalManufactured = totalManufactured;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<ProductionItem> getProductionItem() {
        return productionItem;
    }

    public void setProductionItem(Set<ProductionItem> productionItem) {
        this.productionItem = productionItem;
    }
}
