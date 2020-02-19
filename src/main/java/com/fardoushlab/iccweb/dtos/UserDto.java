package com.fardoushlab.iccweb.dtos;

import com.fardoushlab.iccweb.models.Role;

import java.io.Serializable;

public class UserDto implements Serializable {

    private long id;

    private String name;

    private Role role;

    private String password;

    private Boolean isActive;

    private String joinDate;

    private String resignDate;

    public UserDto() {

    }

    public UserDto(long id, String name, Role role, String password, Boolean isActive, String joinDate, String resignDate) {
        this.id = id;
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
}
