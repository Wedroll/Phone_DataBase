package com.example.phone_database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "PhoneDB")
public class Element {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "phone_id")
    private long id;

    @ColumnInfo(name  = "manufacturer_column")
    private String manufacturer;

    @ColumnInfo(name = "model_column")
    private String model;

    @ColumnInfo(name = "android_version_column")
    private String androidVersion;

    @ColumnInfo(name = "website_column")
    private String website;

    @Ignore
    private long editElementId;

    public Element(String manufacturer, String model, String androidVersion, String website) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.androidVersion = androidVersion;
        this.website = website;
    }

    @Ignore
    public Element(long id, String manufacturer, String model, String androidVersion, String website) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.androidVersion = androidVersion;
        this.website = website;
    }

    // Геттеры и сеттеры

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    // Метод для установки идентификатора при редактировании
    public void setEditElementId(long editElementId) {
        this.editElementId = editElementId;
    }

    // Метод для получения идентификатора при редактировании
    public long getEditElementId() {
        return editElementId;
    }
}