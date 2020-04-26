package com.gerontechies.semonaid.Models;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BudgetDAO {

    @Query("SELECT * FROM budgetitem")
    List<BudgetItem> getAll();

    @Query("SELECT * FROM budgetitem WHERE category LIKE :category")
    List<BudgetItem> getCategoryItems( String category);

//    @Query("SELECT * FROM steps WHERE stepsId = :stepsId LIMIT 1")
//    Steps findByID(int stepsId);

    @Insert
    void insertAll(BudgetItem... budgetItems);

    @Insert
    long insert(BudgetItem budgetItems);

    @Delete
    void delete(BudgetItem budgetItems);

    @Update(onConflict = REPLACE)
    public void updateItem(BudgetItem... budgetItems);

//    @Query("UPDATE BudgetItem SET amount = :amount, fre WHERE itemName = :name")
//    void updateTour(double amount, String name);


    @Query("DELETE FROM BudgetItem")
    void deleteAll();

//    @Query("DELETE FROM BudgetItem where itemId = :itemId")
//    void delSteps(int itemId);

}
