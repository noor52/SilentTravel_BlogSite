package com.noor.blog.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.*;

@Entity
@Table(name = "players")
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id",updatable = false, nullable = false)
    private long id;

    @Column(name = "isActive",updatable = true, nullable = false)
    private boolean isActive;

    @Column(name = "age",updatable = true, nullable = false)
    private long age;

  /*  @Column(name = "dob",updatable = true, nullable = false)
    private String dob;*/

  @Column(name = "dob",updatable = true, nullable = false)
  private LocalDate dob;



    @ManyToOne
    @JoinColumn(name = "c_id")
    private Country country;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "u_id")
    private User user;

    public Player() {
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
}
