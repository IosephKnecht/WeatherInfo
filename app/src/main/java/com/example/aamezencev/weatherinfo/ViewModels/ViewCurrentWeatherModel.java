package com.example.aamezencev.weatherinfo.ViewModels;

/**
 * Created by aa.mezencev on 19.01.2018.
 */

public class ViewCurrentWeatherModel {
    private Long key;
    private String main;
    private String description;
    private String icon;
    private String cod;
    private Long id;

    public ViewCurrentWeatherModel() {
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String lineSep = System.lineSeparator();
        return main +
                lineSep +
                description;
    }
}
