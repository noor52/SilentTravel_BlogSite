package com.fardoushlab.iccweb.request_models;

import java.io.Serializable;

public class Team implements Serializable {


    private long id;
    private String name;
    private long countryId;
    private String countryName;

    public Team() {
    }

    public Team(long id, String name, long coundtryId) {
        this.id = id;
        this.name = name;

        this.countryId = coundtryId;
    }

    public Team(long id, String name, long coundtryId, String countryName) {
        this.id = id;
        this.name = name;
        this.countryId = coundtryId;
        this.countryName = countryName;
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

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryId=" + countryId +
                ", countryName='" + countryName + '\'' +
                '}';
    }


}
