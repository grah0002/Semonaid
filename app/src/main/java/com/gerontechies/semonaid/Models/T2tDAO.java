package com.gerontechies.semonaid.Models;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface T2tDAO {

    @Query("SELECT * FROM T2tItem")
    List<T2tItem> getAllT2TItems();

    @Query("SELECT * FROM T2tItem WHERE name LIKE :name")
    List<T2tItem> getNameT2TItem(String name);

    @Query("SELECT * FROM T2tItem WHERE id LIKE :id LIMIT 1")
    List<T2tItem> getIdT2TItem(String id);

    @Query("SELECT * FROM T2tItem WHERE category LIKE :category")
    List<T2tItem> getCategoryT2TItem(int category);

    @Insert
    long insert(T2tItem t2titem);

    @Delete
    void delete(T2tItem t2titem);




}
