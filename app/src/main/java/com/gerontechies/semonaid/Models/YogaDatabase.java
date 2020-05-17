package com.gerontechies.semonaid.Models;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {YogaItem.class}, version = 2, exportSchema = false)

public abstract class YogaDatabase extends RoomDatabase {

    public abstract YogaDAO YogaDAO();
    private static volatile YogaDatabase INSTANCE;
    static YogaDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (YogaDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    YogaDatabase.class, "yoga_database")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }

}