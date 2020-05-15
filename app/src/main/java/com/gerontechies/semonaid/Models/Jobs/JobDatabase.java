package com.gerontechies.semonaid.Models.Jobs;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.gerontechies.semonaid.Models.Budget.JobItem;
import com.gerontechies.semonaid.Models.Budget.JobContentItem;

@Database(entities = {JobContentItem.class, JobItem.class}, version = 2, exportSchema = false)

public abstract class JobDatabase extends RoomDatabase {

    public abstract JobsDAO JobsDAO();
    private static volatile JobDatabase INSTANCE;
    static JobDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (JobDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    JobDatabase.class, "job_skills_database")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }
}