package com.noor.blog.dtos;

import com.noor.blog.models.Country;

import java.io.Serializable;

public class TeamDto implements Serializable {


    private long id;
    private String name;
    private Country country;

    public TeamDto() {
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

}

