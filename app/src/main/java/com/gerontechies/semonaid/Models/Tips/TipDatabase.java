package com.gerontechies.semonaid.Models.Tips;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.gerontechies.semonaid.Models.Budget.TipItem;

@Database(entities = {TipItem.class}, version = 2, exportSchema = false)

public abstract class TipDatabase extends RoomDatabase {

    public abstract TipDAO TipDAO();
    private static volatile TipDatabase INSTANCE;
    static TipDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TipDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    TipDatabase.class, "tips_database")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }
}