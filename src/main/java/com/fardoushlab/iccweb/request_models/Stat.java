package com.fardoushlab.iccweb.request_models;

public class Stat {
    private Long totalCountry;
    private Long activeCountry;
    private Long inActiveCountry;
    private Long totalUser;
    private Long activeUser;
    private Long inActiveUser;
    private Long totalPlayer;
    private Long activePlayer;
    private Long inActivePlayer;
    private Long totalTeam;
    private Long activeTeam;
    private Long inactiveTeam;
    private Long totalCoach;
    private Long activeCoach;
    private Long inActiveCoach;

    public Stat() {

    }

    public Long getTotalCountry() {
        return totalCountry;
    }

    public void setTotalCountry(Long totalCountry) {
        this.totalCountry = totalCountry;
    }

    public Long getActiveCountry() {
        return activeCountry;
    }

    public void setActiveCountry(Long activeCountry) {
        this.activeCountry = activeCountry;
    }

    public Long getInActiveCountry() {
        return inActiveCountry;
    }

    public void setInActiveCountry(Long inActiveCountry) {
        this.inActiveCountry = inActiveCountry;
    }

    public Long getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(Long totalUser) {
        this.totalUser = totalUser;
    }

    public Long getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(Long activeUser) {
        this.activeUser = activeUser;
    }

    public Long getInActiveUser() {
        return inActiveUser;
    }

    public void setInActiveUser(Long inActiveUser) {
        this.inActiveUser = inActiveUser;
    }

    public Long getTotalPlayer() {
        return totalPlayer;
    }

    public void setTotalPlayer(Long totalPlayer) {
        this.totalPlayer = totalPlayer;
    }

    public Long getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Long activePlayer) {
        this.activePlayer = activePlayer;
    }

    public Long getInActivePlayer() {
        return inActivePlayer;
    }

    public void setInActivePlayer(Long inActivePlayer) {
        this.inActivePlayer = inActivePlayer;
    }

    public Long getTotalTeam() {
        return totalTeam;
    }

    public void setTotalTeam(Long totalTeam) {
        this.totalTeam = totalTeam;
    }

    public Long getActiveTeam() {
        return activeTeam;
    }

    public void setActiveTeam(Long activeTeam) {
        this.activeTeam = activeTeam;
    }

    public Long getInactiveTeam() {
        return inactiveTeam;
    }

    public void setInactiveTeam(Long inactiveTeam) {
        this.inactiveTeam = inactiveTeam;
    }

    public Long getTotalCoach() {
        return totalCoach;
    }

    public void setTotalCoach(Long totalCoach) {
        this.totalCoach = totalCoach;
    }

    public Long getActiveCoach() {
        return activeCoach;
    }

    public void setActiveCoach(Long activeCoach) {
        this.activeCoach = activeCoach;
    }

    public Long getInActiveCoach() {
        return inActiveCoach;
    }

    public void setInActiveCoach(Long inActiveCoach) {
        this.inActiveCoach = inActiveCoach;
    }

    @Override
    public String toString() {
        return "Stat{" +
                "totalCountry=" + totalCountry +
                ", activeCountry=" + activeCountry +
                ", inActiveCountry=" + inActiveCountry +
                ", totalUser=" + totalUser +
                ", activeUser=" + activeUser +
                ", inActiveUser=" + inActiveUser +
                ", totalPlayer=" + totalPlayer +
                ", activePlayer=" + activePlayer +
                ", inActivePlayer=" + inActivePlayer +
                ", totalTeam=" + totalTeam +
                ", activeTeam=" + activeTeam +
                ", inactiveTeam=" + inactiveTeam +
                ", totalCoach=" + totalCoach +
                ", activeCoach=" + activeCoach +
                ", inActiveCoach=" + inActiveCoach +
                '}';
    }
}
