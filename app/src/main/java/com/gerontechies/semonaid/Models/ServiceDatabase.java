package com.gerontechies.semonaid.Models;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ServiceItem.class}, version = 2, exportSchema = false)

public abstract class ServiceDatabase extends RoomDatabase {

    public abstract ServiceDAO ServiceDAO();
    private static volatile ServiceDatabase INSTANCE;
    static ServiceDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ServiceDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    ServiceDatabase.class, "service_database")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }

}