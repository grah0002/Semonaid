package com.gerontechies.semonaid.Models.Budget;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BudgetItem.class, ServiceItem.class, TipItem.class, JobContentItem.class, JobItem.class}, version = 2, exportSchema = false)

public abstract class SemonaidDB extends RoomDatabase {

    public abstract AppDAO AppDAO();
    private static volatile SemonaidDB INSTANCE;
    static SemonaidDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SemonaidDB.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    SemonaidDB.class, "db_semonaid")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }

}