package com.fardoushlab.iccweb.dtos;

import com.fardoushlab.iccweb.models.Country;
import com.fardoushlab.iccweb.models.User;

import java.io.Serializable;

public class PlayerDto implements Serializable {

    private long id;

    private boolean isActive;

    private long age;
    private String dob;

    private Country country;

    private User user;


    public PlayerDto() {
    }

    public PlayerDto(long id, boolean isActive, long age, String dob, Country country, User user) {
        this.id = id;
        this.isActive = isActive;
        this.age = age;
        this.dob = dob;
        this.country = country;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "PlayerDto{" +
                "id=" + id +
                ", isActive=" + isActive +
                ", age=" + age +
                ", dob='" + dob + '\'' +
                ", country=" + country +
                ", user=" + user +
                '}';
    }
}
