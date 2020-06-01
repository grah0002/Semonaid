package com.gerontechies.semonaid.Models;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface OpshopDAO {

    @Query("SELECT * FROM OpshopItem")
    List<OpshopItem> getAll();

//    @Query("SELECT * FROM opshopitem WHERE (category_1 = :category) OR (category_2 = :category) OR (category_3 = :category) OR (category_4 = :category) LIKE :category")
//    List<OpshopItem> getCategoryItems(String category);

    @Query("SELECT * FROM opshopitem WHERE id = :id LIMIT 1")
    OpshopItem findByID(int id);

    @Query("SELECT * FROM OpshopItem WHERE name = :opshop_name LIMIT 1")
    OpshopItem findByName(String opshop_name);

    @Insert
    void insertAll(OpshopItem... opshopitem);

    @Insert
    long insert(OpshopItem opshopitem);

    @Delete
    void delete(OpshopItem opshopitem);

    @Update(onConflict = REPLACE)
    public void updateItem(OpshopItem... opshopitem);

//    @Query("UPDATE BudgetItem SET amount = :amount, fre WHERE itemName = :name")
//    void updateTour(double amount, String name);


    @Query("DELETE FROM OpshopItem")
    void deleteAll();

//    @Query("DELETE FROM BudgetItem where itemId = :itemId")
//    void delSteps(int itemId);

}
