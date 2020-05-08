package com.gerontechies.semonaid.Models;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TipDAO {

    @Query("SELECT * FROM tipitem")
    List<TipItem> getAll();

    @Query("SELECT * FROM tipitem WHERE name LIKE :name LIMIT 1")
    TipItem getItem(String name);

//    @Query("SELECT * FROM steps WHERE stepsId = :stepsId LIMIT 1")
//    Steps findByID(int stepsId);

    @Insert
    void insertAll(TipItem... tipitems);

    @Insert
    long insert(TipItem tipitem);

    @Delete
    void delete(TipItem tipitem);

//    @Update(onConflict = REPLACE)
//    public void updateItem(BudgetItem... budgetItems);
//



    @Query("DELETE FROM TipItem")
    void deleteAll();


}
