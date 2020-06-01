package com.gerontechies.semonaid.Models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*Model for tips item */

public class T2TInstructionsItem {

    public String text;


    public T2TInstructionsItem(){}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

