package com.gerontechies.semonaid.Models.Budget;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BudgetItem {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "itemName")
    public String itemName;

    @ColumnInfo(name = "amount")
    public double amount;

    @ColumnInfo(name = "frequency")
    public int frequency;

    @ColumnInfo(name = "type")
    public int type;

    @ColumnInfo(name = "category")
    public String category;

    public BudgetItem(String itemName, double amount, int frequency, int type, String category) {
        this.itemName = itemName;
        this.amount = amount;
        this.frequency = frequency;
        this.type = type;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

