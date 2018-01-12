package com.example.aamezencev.weatherinfo.DaoModels;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by aa.mezencev on 10.01.2018.
 */

@Entity
public class CityDbModel {
    @Id
    private Long key;
    @NonNull
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String country;
    @Generated(hash = 523078234)
    public CityDbModel(Long key, @NonNull Long id, @NonNull String name,
            @NonNull String country) {
        this.key = key;
        this.id = id;
        this.name = name;
        this.country = country;
    }
    @Generated(hash = 599002694)
    public CityDbModel() {
    }
    public Long getKey() {
        return this.key;
    }
    public void setKey(Long key) {
        this.key = key;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCountry() {
        return this.country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
}
