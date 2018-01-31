package com.example.aamezencev.weatherinfo.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

/**
 * Created by aa.mezencev on 30.01.2018.
 */

@Entity
public class PromptCityDbModel {
    @Id(autoincrement = true)
    private Long key;
    private String description;
    private String id;
    private String placeId;
    private String mainText;
    private String secondaryText;
    private String briefInformation;
    private Long relationKey;
    @ToOne(joinProperty = "relationKey")
    private CurrentWeatherDbModel weatherDbModel;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1554318806)
    private transient PromptCityDbModelDao myDao;
    @Generated(hash = 947701616)
    public PromptCityDbModel(Long key, String description, String id,
            String placeId, String mainText, String secondaryText,
            String briefInformation, Long relationKey) {
        this.key = key;
        this.description = description;
        this.id = id;
        this.placeId = placeId;
        this.mainText = mainText;
        this.secondaryText = secondaryText;
        this.briefInformation = briefInformation;
        this.relationKey = relationKey;
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
    public String getBriefInformation() {
        return this.briefInformation;
    }
    public void setBriefInformation(String briefInformation) {
        this.briefInformation = briefInformation;
    }
    public Long getRelationKey() {
        return this.relationKey;
    }
    public void setRelationKey(Long relationKey) {
        this.relationKey = relationKey;
    }
    @Generated(hash = 1538632706)
    private transient Long weatherDbModel__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1220332391)
    public CurrentWeatherDbModel getWeatherDbModel() {
        Long __key = this.relationKey;
        if (weatherDbModel__resolvedKey == null
                || !weatherDbModel__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CurrentWeatherDbModelDao targetDao = daoSession
                    .getCurrentWeatherDbModelDao();
            CurrentWeatherDbModel weatherDbModelNew = targetDao.load(__key);
            synchronized (this) {
                weatherDbModel = weatherDbModelNew;
                weatherDbModel__resolvedKey = __key;
            }
        }
        return weatherDbModel;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 229640694)
    public void setWeatherDbModel(CurrentWeatherDbModel weatherDbModel) {
        synchronized (this) {
            this.weatherDbModel = weatherDbModel;
            relationKey = weatherDbModel == null ? null : weatherDbModel.getKey();
            weatherDbModel__resolvedKey = relationKey;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 238356009)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPromptCityDbModelDao() : null;
    }
}

