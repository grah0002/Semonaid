package com.gerontechies.semonaid.Models;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {OpshopItem.class}, version = 2, exportSchema = false)

public abstract class OpshopDatabase extends RoomDatabase {

    public abstract OpshopDAO OpshopDAO();
    private static volatile OpshopDatabase INSTANCE;
    static OpshopDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (OpshopDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    OpshopDatabase.class, "opshop_database")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }

}