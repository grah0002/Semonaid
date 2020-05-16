package com.gerontechies.semonaid.Models;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface T2tDAO {

    @Query("SELECT * FROM T2tItem")
    List<T2tItem> getAll();

    @Query("SELECT * FROM T2tItem WHERE name LIKE :name")
    List<T2tItem> getName(String name);

    @Query("SELECT * FROM T2tItem WHERE category LIKE :category")
    List<T2tItem> getCategory(String category);

    @Insert
    void insertAll(T2tItem... t2titems);

    @Insert
    long insert(T2tItem t2titem);

    @Delete
    void delete(T2tItem t2titem);


    @Query("DELETE FROM T2tItem")
    void deleteAll();


}
