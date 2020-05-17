package com.gerontechies.semonaid.Models;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {T2tItem.class}, version = 2, exportSchema = false)

public abstract class T2tDatabase extends RoomDatabase {

    public abstract T2tDAO T2tDAO();
    private static volatile T2tDatabase INSTANCE;
    static T2tDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (T2tDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    T2tDatabase.class, "t2t_database")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }

}