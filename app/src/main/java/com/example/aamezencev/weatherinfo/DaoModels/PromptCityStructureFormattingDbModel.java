package com.example.aamezencev.weatherinfo.DaoModels;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by aa.mezencev on 12.01.2018.
 */
@Entity
public class PromptCityStructureFormattingDbModel {
    @Id(autoincrement = true)
    private Long key;
    @NotNull
    private String mainText;
    @NotNull
    private String secondaryText;
    @Generated(hash = 862830408)
    public PromptCityStructureFormattingDbModel(Long key, @NotNull String mainText,
            @NotNull String secondaryText) {
        this.key = key;
        this.mainText = mainText;
        this.secondaryText = secondaryText;
    }
    @Generated(hash = 729262522)
    public PromptCityStructureFormattingDbModel() {
    }
    public Long getKey() {
        return this.key;
    }
    public void setKey(Long key) {
        this.key = key;
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
