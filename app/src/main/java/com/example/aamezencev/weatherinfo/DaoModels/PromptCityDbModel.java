package com.example.aamezencev.weatherinfo.DaoModels;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by aa.mezencev on 12.01.2018.
 */
@Entity
public class PromptCityDbModel {
    @Id(autoincrement = true)
    private Long key;
    @NotNull
    private String description;
    @NotNull
    private String id;
    @NotNull
    private String placeId;
    @NotNull
    private String mainText;
    @NotNull
    private String secondaryText;
    @Generated(hash = 1858066437)
    public PromptCityDbModel(Long key, @NotNull String description,
            @NotNull String id, @NotNull String placeId, @NotNull String mainText,
            @NotNull String secondaryText) {
        this.key = key;
        this.description = description;
        this.id = id;
        this.placeId = placeId;
        this.mainText = mainText;
        this.secondaryText = secondaryText;
    }
    @Generated(hash = 1021489601)
    public PromptCityDbModel() {
    }
    public Long getKey() {
        return this.key;
    }
    public void setKey(Long key) {
        this.key = key;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPlaceId() {
        return this.placeId;
    }
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
    public String getMainText() {
        return this.mainText;
    }
    public void setMainText(String mainText) {
        this.mainText = mainText;
    }
    public String getSecondaryText() {
        return this.secondaryText;
    }
    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }
}
