package com.gerontechies.semonaid.Models.Budget;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

/*DAO for all the DB querries*/
@Dao
public interface AppDAO {


    //Budget Table
    @Query("SELECT * FROM budgetitem")
    List<BudgetItem> getAllBudgetData();

    @Query("SELECT * FROM budgetitem WHERE category LIKE :category")
    List<BudgetItem> getBudgetCategoryItems( String category);

    @Insert
    void insertAllBudgetItem(BudgetItem... budgetItems);

    @Insert
    long insertBudgetItem(BudgetItem budgetItems);

    @Delete
    void deleteBudgetItem(BudgetItem budgetItems);

    @Update(onConflict = REPLACE)
    public void updateBudgetItem(BudgetItem... budgetItems);

    @Query("DELETE FROM BudgetItem")
    void deleteAllBudgetItem();


    //Tips Table
    @Query("SELECT * FROM tipitem")
    List<TipItem> getAllTipItem();

    @Query("SELECT * FROM tipitem WHERE name LIKE :name")
    List<TipItem> getTipName(String name);

    @Query("SELECT * FROM tipitem WHERE category LIKE :category")
    List<TipItem> getTipCategory(String category);

    @Insert
    void insertAllTipItem(TipItem... tipitems);

    @Insert
    long insertTipItem(TipItem tipitem);

    @Delete
    void deleteTipItem(TipItem tipitem);

    @Query("DELETE FROM TipItem")
    void deleteAllTipItem();

    //Service Items
    @Query("SELECT * FROM serviceitem")
    List<ServiceItem> getAllServiceItem();

    @Query("SELECT * FROM serviceitem WHERE (category_1 = :category) OR (category_2 = :category) OR (category_3 = :category) OR (category_4 = :category) LIKE :category")
    List<ServiceItem> getCategoryServiceItem(String category);

    @Query("SELECT * FROM serviceitem WHERE id = :id LIMIT 1")
    ServiceItem findByServiceItemID(int id);

    @Query("SELECT * FROM serviceitem WHERE service_name = :service_name LIMIT 1")
    ServiceItem findByServiceItemName(String service_name);

    @Insert
    void insertAllServiceItem(ServiceItem... serviceitem);

    @Insert
    long insertServiceItem(ServiceItem serviceitem);

    @Delete
    void deleteServiceItem(ServiceItem serviceitem);

    @Update(onConflict = REPLACE)
    public void updateServiceItem(ServiceItem... serviceitem);

    @Query("DELETE FROM ServiceItem")
    void deleteAllServiceItem();


    //Skills List
    @Query("SELECT * FROM JobContentItem WHERE type = :type")
    List<JobContentItem> getAllJobContentItems(String type);

    @Insert
    void insertAllSkills(JobContentItem... skillitem);
    @Insert
    long insertSkills(JobContentItem skillitem);

    @Delete
    void delete(JobContentItem skillitem);

    @Query("DELETE FROM JobContentItem")
    void deleteAll();


    //Job items
    @Query("SELECT * FROM jobitem")
    List<JobItem> getAllJobs();

    @Insert
    void insertAllJobs(JobItem... jobItem);

    @Insert
    long insertJobs(JobItem jobItem);


    //Events
    @Query("SELECT * FROM eventitem")
    List<EventItem> getAllEvents();

    @Query("SELECT * FROM eventitem WHERE id = :id LIMIT 1")
    EventItem findByEventID(int id);

    @Insert
    long insertEventItem(EventItem eventItem);


    //how to apply for jobs
    @Query("SELECT * FROM JobTips")
    List<JobTips> getAllJobTips();

    @Insert
    long insertJobTipItem(JobTips jobTips);

}

