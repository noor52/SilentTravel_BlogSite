package com.fardoushlab.iccweb.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {

    /**OTHERS FIELD SHOULD COME LATER
     *
     * */

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "name", updatable = true, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", updatable = true, nullable = false)
    private Role role;

    @Column(name = "password", length = 512, updatable = true, nullable = false)
    private String password;

    @Column(name = "isActive", updatable = true, nullable = true)
    private Boolean isActive;

    @Column(name = "joinDate", updatable = true, nullable = true)
    private String joinDate;


    @Column(name = "resignDate", updatable = true, nullable = true)
    private String resignDate;

    @Column(name = "pp_url")
    private String profilePictureUrl;

    public User() {

    }

    public User(String name, Role role, String password) {
        this.name = name;
        this.role = role;
        this.password = password;
    }

    public User(String name, Role role, String password, Boolean isActive, String joinDate, String resignDate) {
        this.name = name;
        this.role = role;
        this.password = password;
        this.isActive = isActive;
        this.joinDate = joinDate;
        this.resignDate = resignDate;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getResignDate() {
        return resignDate;
    }

    public void setResignDate(String resignDate) {
        this.resignDate = resignDate;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
}
