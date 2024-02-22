package com.example.phone_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ElementDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Element element);

    @Update
    void update(Element element);

    @Delete
    void delete(Element element);

    @Query("DELETE FROM PhoneDB")
    void deleteAll();

    @Query("SELECT * FROM PhoneDB ORDER BY phone_id ASC")
    LiveData<List<Element>> getAllElements();

    @Query("SELECT * FROM PhoneDB WHERE phone_id = :id")
    LiveData<Element> getElementById(long id);
}
