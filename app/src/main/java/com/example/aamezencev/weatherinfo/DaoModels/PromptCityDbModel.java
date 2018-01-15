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
    private Long structureFormattingId;
    @NotNull
    private String description;
    @NotNull
    private String id;
    @NotNull
    private String placeId;
    @ToOne(joinProperty = "structureFormattingId")
    private PromptCityStructureFormattingDbModel structuredFormatting;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1554318806)
    private transient PromptCityDbModelDao myDao;
    @Generated(hash = 1815841651)
    public PromptCityDbModel(Long key, @NotNull Long structureFormattingId,
            @NotNull String description, @NotNull String id,
            @NotNull String placeId) {
        this.key = key;
        this.structureFormattingId = structureFormattingId;
        this.description = description;
        this.id = id;
        this.placeId = placeId;
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
    public Long getStructureFormattingId() {
        return this.structureFormattingId;
    }
    public void setStructureFormattingId(Long structureFormattingId) {
        this.structureFormattingId = structureFormattingId;
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
    @Generated(hash = 618877247)
    private transient Long structuredFormatting__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 571450421)
    public PromptCityStructureFormattingDbModel getStructuredFormatting() {
        Long __key = this.structureFormattingId;
        if (structuredFormatting__resolvedKey == null
                || !structuredFormatting__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PromptCityStructureFormattingDbModelDao targetDao = daoSession
                    .getPromptCityStructureFormattingDbModelDao();
            PromptCityStructureFormattingDbModel structuredFormattingNew = targetDao
                    .load(__key);
            synchronized (this) {
                structuredFormatting = structuredFormattingNew;
                structuredFormatting__resolvedKey = __key;
            }
        }
        return structuredFormatting;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 99932696)
    public void setStructuredFormatting(
            @NotNull PromptCityStructureFormattingDbModel structuredFormatting) {
        if (structuredFormatting == null) {
            throw new DaoException(
                    "To-one property 'structureFormattingId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.structuredFormatting = structuredFormatting;
            structureFormattingId = structuredFormatting.getKey();
            structuredFormatting__resolvedKey = structureFormattingId;
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
