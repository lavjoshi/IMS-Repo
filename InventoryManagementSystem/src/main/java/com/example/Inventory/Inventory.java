package com.example.Inventory;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by lav on 2/8/16.
 */
@Entity
public class Inventory {
    @Id
    String name;

    String unitOfMeasurement;

    float unitCost;

    int openingBalance;

    int currentBalance;

    float dimensions;

    public int getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(int currentBalance) {
        this.currentBalance = currentBalance;
    }

    public float getDimensions() {
        return dimensions;
    }

    public void setDimensions(float dimensions) {
        this.dimensions = dimensions;
    }

    public int getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(int openingBalance) {
        this.openingBalance = openingBalance;
    }

    public float getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(float unitCost) {
        this.unitCost = unitCost;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
