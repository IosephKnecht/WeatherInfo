package com.example.aamezencev.weatherinfo.DaoModels;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by aa.mezencev on 19.01.2018.
 */
@Entity
public class CurrentWeatherDbModel {
    @Id
    private Long key;
    @NotNull
    private String main;
    @NotNull
    private String description;
    @NotNull
    private String icon;
    @NotNull
    private String cod;
    @NotNull
    private Long id;
    @Generated(hash = 1133194900)
    public CurrentWeatherDbModel(Long key, @NotNull String main,
            @NotNull String description, @NotNull String icon, @NotNull String cod,
            @NotNull Long id) {
        this.key = key;
        this.main = main;
        this.description = description;
        this.icon = icon;
        this.cod = cod;
        this.id = id;
    }
    @Generated(hash = 1721116696)
    public CurrentWeatherDbModel() {
    }
    public Long getKey() {
        return this.key;
    }
    public void setKey(Long key) {
        this.key = key;
    }
    public String getMain() {
        return this.main;
    }
    public void setMain(String main) {
        this.main = main;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getCod() {
        return this.cod;
    }
    public void setCod(String cod) {
        this.cod = cod;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
