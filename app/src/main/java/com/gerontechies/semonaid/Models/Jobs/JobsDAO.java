package com.gerontechies.semonaid.Models.Jobs;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.gerontechies.semonaid.Models.Budget.JobContentItem;
import com.gerontechies.semonaid.Models.Budget.JobItem;

import java.util.List;

@Dao
public interface JobsDAO {

    @Query("SELECT * FROM JobContentItem")
    List<JobContentItem> getAllSkills();

    @Query("SELECT * FROM jobitem")
    List<JobItem> getAllJobs();

    @Insert
    void insertAll(JobContentItem... skillitem);
    @Insert
    long insert(JobContentItem skillitem);

    @Insert
    void insertAllJobs(JobItem... jobItem);

    @Insert
    long insertJobs(JobItem jobItem);

    @Delete
    void delete(JobContentItem skillitem);

    @Query("DELETE FROM JobContentItem")
    void deleteAll();


}
