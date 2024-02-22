package com.example.phone_database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ElementViewModel extends AndroidViewModel {
    private final ElementRepository mRepository;
    private final LiveData<List<Element>> mAllElements;

    public ElementViewModel(@NotNull Application application){

        super(application);
        mRepository = new ElementRepository(application);
        mAllElements = mRepository.getAllElements();
    }

    public void deleteAll(){mRepository.deleteAll();}

    public void update(Element element) {mRepository.update(element);}

    public void insert(Element element) {mRepository.insert(element);}

    public void delete(Element element) {mRepository.delete(element);}
    LiveData<List<Element>> getAllElements(){
        return mAllElements;
    }

    public LiveData<Element> getElementById(long id) {
        return mRepository.getElementById(id);
    }

    public void clearDatabase() {
        mRepository.deleteAll();
    }
}
