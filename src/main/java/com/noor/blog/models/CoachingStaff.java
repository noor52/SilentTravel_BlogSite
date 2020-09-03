package com.noor.blog.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "coaching_staff")
public class CoachingStaff  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "isActive",updatable = true, nullable = false)
    private boolean isActive;

    @Column(name = "age",updatable = true, nullable = false)
    private long age;

    @Column(name = "dob",updatable = true, nullable = false)
    private LocalDate dob;

    @ManyToOne
    @JoinColumn(name = "c_id", updatable = true, nullable = false)
    private Country country;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "u_id", updatable = true, nullable = false)
    private User user;


    public CoachingStaff() {

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
