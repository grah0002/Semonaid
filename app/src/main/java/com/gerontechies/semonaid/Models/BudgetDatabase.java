package com.gerontechies.semonaid.Models;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BudgetItem.class}, version = 2, exportSchema = false)

public abstract class BudgetDatabase extends RoomDatabase {

    public abstract BudgetDAO budgetDAO();
    private static volatile BudgetDatabase INSTANCE;
    static BudgetDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BudgetDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    BudgetDatabase.class, "budget_database")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }

}