package com.noor.blog.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "team_players", uniqueConstraints = @UniqueConstraint(columnNames = {"t_id","p_id"}))
public class TeamPlayer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "t_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "p_id")
    private Player player;

    public TeamPlayer() {

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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
