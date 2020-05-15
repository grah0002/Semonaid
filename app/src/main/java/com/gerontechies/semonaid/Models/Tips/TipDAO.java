package com.gerontechies.semonaid.Models.Tips;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.gerontechies.semonaid.Models.Budget.TipItem;

import java.util.List;

@Dao
public interface TipDAO {

    @Query("SELECT * FROM tipitem")
    List<TipItem> getAll();
    @Query("SELECT * FROM tipitem WHERE name LIKE :name")
    List<TipItem> getName(String name);
    @Query("SELECT * FROM tipitem WHERE category LIKE :category")
    List<TipItem> getCategory(String category);
    @Insert
    void insertAll(TipItem... tipitems);
    @Insert
    long insert(TipItem tipitem);
    @Delete
    void delete(TipItem tipitem);
    @Query("DELETE FROM TipItem")
    void deleteAll();


}
