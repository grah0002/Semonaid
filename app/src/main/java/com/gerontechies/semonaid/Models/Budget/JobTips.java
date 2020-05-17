package com.gerontechies.semonaid.Models.Budget;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*Model for job tips */

@Entity
public class JobTips {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public int id;


    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "desc")
    public String desc;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }



    public JobTips(){}

}

