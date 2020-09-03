package com.noor.blog.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "teams")
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "name", updatable = true, nullable = false)
    private String name;

    @Column(name = "isActive",updatable = true, nullable = false)
    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "c_id")
    private Country country;

    public Team() {
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
