package com.example.phone_database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ElementRepository {
    private ElementDao mElementDao;
    private LiveData<List<Element>> mAllElements;

    public ElementRepository(Application application){

        ElementRoomDatabase elementRoomDatabase = ElementRoomDatabase.getDataBase(application);

        mElementDao = elementRoomDatabase.elementDao();
        mAllElements = mElementDao.getAllElements();
    }

    LiveData<List<Element>> getAllElements(){
        return mElementDao.getAllElements();
    }

    void deleteAll(){
        ElementRoomDatabase.databaseWriteExecutor.execute(() ->{
            mElementDao.deleteAll();
        });
    }

    void update(Element element){
        ElementRoomDatabase.databaseWriteExecutor.execute(() ->{
            mElementDao.update(element);
        });
    }

    void insert(Element element){
        ElementRoomDatabase.databaseWriteExecutor.execute(() ->{
            mElementDao.insert(element);
        });
    }

    void delete(Element element){
        ElementRoomDatabase.databaseWriteExecutor.execute(() ->{
            mElementDao.delete(element);
        });
    }

    LiveData<Element> getElementById(long id) {
        return mElementDao.getElementById(id);
    }

}
