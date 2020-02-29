package com.fardoushlab.iccweb.services;

import com.fardoushlab.iccweb.config.persistancy.HibernateConfig;
import com.fardoushlab.iccweb.dtos.TeamDto;
import com.fardoushlab.iccweb.exceptions.ResourceAlreadyExistException;
import com.fardoushlab.iccweb.exceptions.ResourceNotFoundException;
import com.fardoushlab.iccweb.models.CoachingStaff;
import com.fardoushlab.iccweb.models.Team;
import com.fardoushlab.iccweb.models.TeamPlayer;
import com.fardoushlab.iccweb.models.TeamStaff;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {
    private HibernateConfig hibernateConfig;

    public TeamService(HibernateConfig hibernateConfig) {
        this.hibernateConfig = hibernateConfig;
    }

    public void addNewTeam( TeamDto teamDto){
        Team team = new Team();
        BeanUtils.copyProperties(teamDto,team);
        team.setActive(true);

        if(isTeamAlreadyExists(team)) throw new ResourceAlreadyExistException("Sorry, a Team already exists with this name");


        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        try{
            session.save(team);
            transaction.commit();
        }catch (HibernateException e){
            if (transaction!= null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }


    }

    public void saveEditedTeam(TeamDto dto){

        Team team = new Team();
        BeanUtils.copyProperties(dto,team);

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        try{
            session.update(team);
            transaction.commit();
        }catch (HibernateException e){
            if (transaction!= null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }

    }

    public boolean isTeamAlreadyExists(Team team){


        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Team> teamCriteriaQuery = cb.createQuery(Team.class);
        Root<Team> root = teamCriteriaQuery.from(Team.class);

        teamCriteriaQuery.where(cb.equal(root.get("name"), team.getName()));
        var query = session.createQuery(teamCriteriaQuery);

        var teamList = new ArrayList<Team>();
        try {
            teamList = (ArrayList<Team>) query.getResultList();
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return teamList.size() > 0 ? true: false;

    }
    public TeamDto getTeamDtoById(long teamId) {
        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();
        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Team> teamCriteriaQuery = cb.createQuery(Team.class);
        Root<Team> root = teamCriteriaQuery.from(Team.class);

        teamCriteriaQuery.where(cb.equal(root.get("id"), teamId));
        var query = session.createQuery(teamCriteriaQuery);

        Team team = null;
        try {
            team = query.getSingleResult();

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        if (team == null) {
            throw new ResourceNotFoundException("No corresponding team found");
        }

        var dto = new TeamDto();
        BeanUtils.copyProperties(team, dto);
        return dto;
    }

    public List<TeamDto> getAllTeam(){

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Team> teamCriteriaQuery = cb.createQuery(Team.class);
        Root<Team> root = teamCriteriaQuery.from(Team.class);

        teamCriteriaQuery.where(cb.isTrue(root.get("isActive")));
        var query = session.createQuery(teamCriteriaQuery);

        var teamList = new ArrayList<TeamDto>();
        try {
            query.getResultList().forEach(team->{
                var teamDto = new TeamDto();
                BeanUtils.copyProperties(team,teamDto);
                teamList.add(teamDto);
            });
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return teamList;
    }

    public TeamDto getTeamByCountry(long countryId){

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Team> teamCriteriaQuery = cb.createQuery(Team.class);
        Root<Team> root = teamCriteriaQuery.from(Team.class);

        teamCriteriaQuery.where(cb.equal(root.get("country"),countryId));

        var query = session.createQuery(teamCriteriaQuery);

        var teamList = new ArrayList<TeamDto>();
        try {
            query.getResultList().forEach(team->{
                var teamDto = new TeamDto();
                BeanUtils.copyProperties(team,teamDto);
                teamList.add(teamDto);
            });
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        if (teamList.size() <= 0){
         throw  new ResourceNotFoundException("No team found with this country");
        }

        return teamList.get(0);
    }
    @Transactional
    public void addPlayers(List<TeamPlayer> players){
        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        try{
            players.forEach(player->{
                session.save(player);
            });

            transaction.commit();
        }catch (HibernateException e){
            if (transaction!= null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    @Transactional
    public void addStaffs(List<TeamStaff> staffs){
        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        try{
            staffs.forEach(staff->{
                session.save(staff);
            });

            transaction.commit();
        }catch (HibernateException e){
            if (transaction!= null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    @Transactional
    public void changeTeamActiveStatus(long teamId, boolean isActive) {

        var session = hibernateConfig.getSession();
        var transection = session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaUpdate<Team> teamDelete = criteriaBuilder.createCriteriaUpdate(Team.class);
        Root<Team> root = teamDelete.from(Team.class);
        teamDelete.where(criteriaBuilder.equal(root.get("id"), teamId));
        teamDelete.set("isActive",isActive);

        var query = session.createQuery(teamDelete);

        try {
            query.executeUpdate();
            transection.commit();

        }catch(HibernateException e) {

            if(transection!= null ) {
                transection.rollback();
            }
            e.printStackTrace();

        }finally {
            session.close();
        }

    }

    public void deactiveTeamPlayers(long teamId){
        var session = hibernateConfig.getSession();
        var transection = session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaUpdate<TeamPlayer> tpquery = criteriaBuilder.createCriteriaUpdate(TeamPlayer.class);
        Root<TeamPlayer> root = tpquery.from(TeamPlayer.class);
        tpquery.where(criteriaBuilder.equal(root.get("team"), teamId));
        tpquery.set("isActive",false);

        var query = session.createQuery(tpquery);

        try {
            query.executeUpdate();
            transection.commit();

        }catch(HibernateException e) {

            if(transection!= null ) {
                transection.rollback();
            }
            e.printStackTrace();

        }finally {
            session.close();
        }
    }

    @Transactional
    public void deactiveTeamStaff(long teamId){

        var session = hibernateConfig.getSession();
        var transection = session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaUpdate<TeamStaff> ctquery = criteriaBuilder.createCriteriaUpdate(TeamStaff.class);
        Root<TeamStaff> root = ctquery.from(TeamStaff.class);
        ctquery.where(criteriaBuilder.equal(root.get("team"), teamId));
        ctquery.set("isActive",false);

        var query = session.createQuery(ctquery);

        try {
            query.executeUpdate();
            transection.commit();

        }catch(HibernateException e) {

            if(transection!= null ) {
                transection.rollback();
            }
            e.printStackTrace();

        }finally {
            session.close();
        }
    }

    @Transactional
    public void changeCountryTeamActiveStatus(long countryId, boolean isActive){

        var session = hibernateConfig.getSession();
        var transection = session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaUpdate<Team> teamDelete = criteriaBuilder.createCriteriaUpdate(Team.class);
        Root<Team> root = teamDelete.from(Team.class);
        teamDelete.where(criteriaBuilder.equal(root.get("country"), countryId));
        teamDelete.set("isActive",isActive);

        var query = session.createQuery(teamDelete);

        try {
            query.executeUpdate();
            transection.commit();

        }catch(HibernateException e) {

            if(transection!= null ) {
                transection.rollback();
            }
            e.printStackTrace();

        }finally {
            session.close();
        }

    }

    public Long countTotalTeam(){

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> teamCountQuery = cb.createQuery(Long.class);
        Root<Team> root = teamCountQuery.from(Team.class);
        teamCountQuery.select(cb.count(root.get("id")));


        var query = session.createQuery(teamCountQuery);

        Long totalTeam = Long.valueOf(0);
        try {
            totalTeam =   query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return totalTeam;
    }

    public Long countActiveTeam(){

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> teamCountQuery = cb.createQuery(Long.class);
        Root<Team> root = teamCountQuery.from(Team.class);
        teamCountQuery.select(cb.count(root.get("id")));
        teamCountQuery.where(cb.isTrue(root.get("isActive")));


        var query = session.createQuery(teamCountQuery);

        Long activeTeam = Long.valueOf(0);
        try {
            activeTeam =   query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return activeTeam;
    }

}
