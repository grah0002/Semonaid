package com.gerontechies.semonaid.Models;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface YogaDAO {

    @Query("SELECT * FROM YogaItem")
    List<YogaItem> getAll();

    @Query("SELECT * FROM YogaItem WHERE name LIKE :name")
    List<YogaItem> getName(String name);

    @Insert
    void insertAll(YogaItem... yogaitems);

    @Insert
    long insert(YogaItem yogaitem);

    @Delete
    void delete(YogaItem yogaitem);


    @Query("DELETE FROM YogaItem")
    void deleteAll();


}
