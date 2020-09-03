package com.noor.blog.dtos;

import com.noor.blog.models.Country;
import com.noor.blog.models.User;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDate;

public class PlayerDto implements Serializable {

    private long id;

    private boolean isActive;

    private long age;

    @Column(name = "dob",updatable = true, nullable = false)
    private LocalDate dob;


    private Country country;

    private User user;


    public PlayerDto() {
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


    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "PlayerDto{" +
                "id=" + id +
                ", isActive=" + isActive +
                ", age=" + age +
                ", dob=" + dob +
                ", country=" + country +
                ", user=" + user +
                '}';
    }
}
