package com.noor.blog.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "team_staffs", uniqueConstraints = @UniqueConstraint(columnNames = {"t_id","staff_id"}))
public class TeamStaff implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "t_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private CoachingStaff staff;

    public TeamStaff() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public CoachingStaff getStaff() {
        return staff;
    }

    public void setStaff(CoachingStaff staff) {
        this.staff = staff;
    }
}
