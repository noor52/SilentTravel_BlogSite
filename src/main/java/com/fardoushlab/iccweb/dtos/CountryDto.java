package com.fardoushlab.iccweb.dtos;

import java.io.Serializable;

public class CountryDto implements Serializable {

    private long id;
    private String name;
    private boolean isActive;

    public CountryDto() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
