package com.gerontechies.semonaid.Models.Budget;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class JobItem {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public int id;


    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "category")
    public String category;


    @ColumnInfo(name = "desc")
    public String desc;

    @ColumnInfo(name = "skills")
    public String skills;

    @ColumnInfo(name = "certifications")
    public String certifications;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public JobItem(){}

}

