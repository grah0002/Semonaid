package com.gerontechies.semonaid.Models.Service;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gerontechies.semonaid.Models.Budget.ServiceItem;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ServiceDAO {

    @Query("SELECT * FROM serviceitem")
    List<ServiceItem> getAll();
    @Query("SELECT * FROM serviceitem WHERE (category_1 = :category) OR (category_2 = :category) OR (category_3 = :category) OR (category_4 = :category) LIKE :category")
    List<ServiceItem> getCategoryItems(String category);
    @Query("SELECT * FROM serviceitem WHERE id = :id LIMIT 1")
    ServiceItem findByID(int id);
    @Query("SELECT * FROM serviceitem WHERE service_name = :service_name LIMIT 1")
    ServiceItem findByName(String service_name);
    @Insert
    void insertAll(ServiceItem... serviceitem);
    @Insert
    long insert(ServiceItem serviceitem);
    @Delete
    void delete(ServiceItem serviceitem);
    @Update(onConflict = REPLACE)
    public void updateItem(ServiceItem... serviceitem);
    @Query("DELETE FROM ServiceItem")
    void deleteAll();


}
