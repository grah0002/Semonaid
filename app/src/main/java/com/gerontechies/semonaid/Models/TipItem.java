package com.gerontechies.semonaid.Models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TipItem {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public int id;


    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "tips_1")
    public String tips_1;

    @ColumnInfo(name = "tips_2")
    public String tips_2;


    @ColumnInfo(name = "title_1")
    public String title_1;

    @ColumnInfo(name = "title_2")
    public String title_2;

    public TipItem(){}

    public String getTitle_1() {
        return title_1;
    }

    public void setTitle_1(String title_1) {
        this.title_1 = title_1;
    }

    public String getTitle_2() {
        return title_2;
    }

    public void setTitle_2(String title_2) {
        this.title_2 = title_2;
    }
//    public TipItem(@NonNull int id, String name, String category, String tips_1, String tips_2) {
//        this.id = id;
//        this.name = name;
//        this.category = category;
//        this.tips_1 = tips_1;
//        this.tips_2 = tips_2;
//    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTips_1() {
        return tips_1;
    }

    public void setTips_1(String tips_1) {
        this.tips_1 = tips_1;
    }

    public String getTips_2() {
        return tips_2;
    }

    public void setTips_2(String tips_2) {
        this.tips_2 = tips_2;
    }
}

