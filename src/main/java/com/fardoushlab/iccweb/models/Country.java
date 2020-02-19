package com.fardoushlab.iccweb.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="countries")
public class Country implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "name", updatable = true, nullable = false, unique = true)
    private String name;

    @Column(name = "isActive", updatable = true, nullable = false)
    private boolean isActive;

    public Country() {
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
