package com.fardoushlab.iccweb.services;

import com.fardoushlab.iccweb.config.persistancy.HibernateConfig;
import com.fardoushlab.iccweb.dtos.PlayerDto;
import com.fardoushlab.iccweb.exceptions.ResourceNotFoundException;
import com.fardoushlab.iccweb.models.Player;
import com.fardoushlab.iccweb.models.TeamPlayer;
import com.fardoushlab.iccweb.models.User;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {
    private HibernateConfig hibernateConfig;

    public PlayerService(HibernateConfig hibernateConfig) {
        this.hibernateConfig = hibernateConfig;
    }

    @Transactional
    public void addPlayer(PlayerDto playerDto){

    //    if(isPlayerAlreadyExists(playerName)) throw new ResourceAlreadyExistException("Sorry, a player already exists with this name");


        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        Player player = new Player();
        BeanUtils.copyProperties(playerDto,player);

        try{
            session.save(player);
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
    public void saveEditedPlayer(PlayerDto playerDto){

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        Player player = new Player();
        BeanUtils.copyProperties(playerDto,player);

        try{
            //session.save(player);
            session.update(player);
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

    public boolean isPlayerAlreadyExists(String playerName){
        playerName = playerName.trim().toUpperCase();

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Player> playerCriteriaQuery = cb.createQuery(Player.class);
        Root<Player> root = playerCriteriaQuery.from(Player.class);

        playerCriteriaQuery.where(cb.equal(root.get("name"), playerName));
        var query = session.createQuery(playerCriteriaQuery);

        var playerList = new ArrayList<Player>();
        try {
            playerList = (ArrayList<Player>) query.getResultList();
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }


        return playerList.size() > 0 ? true: false;

    }

    public List<PlayerDto> getAllPlayer(){

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Player> playerCriteriaQuery = cb.createQuery(Player.class);
        Root<Player> root = playerCriteriaQuery.from(Player.class);

        var query = session.createQuery(playerCriteriaQuery);

        var playerDtoList = new ArrayList<PlayerDto>();
        try {
            query.getResultList().forEach(player -> {
                PlayerDto dto = new PlayerDto();
                BeanUtils.copyProperties(player,dto);
                playerDtoList.add(dto);
            });
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }


        return playerDtoList;
    }

    public PlayerDto  getPlayerById(long playerId){


        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Player> playerCriteriaQuery = cb.createQuery(Player.class);
        Root<Player> root = playerCriteriaQuery.from(Player.class);
        playerCriteriaQuery.where(cb.equal(root.get("id"),playerId));

        var query = session.createQuery(playerCriteriaQuery);

        PlayerDto playerDto = new PlayerDto();
        try {
           Player player = query.getSingleResult();
           if (player == null){
               throw new ResourceNotFoundException("No player found");
           }
            BeanUtils.copyProperties(player,playerDto);
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return playerDto;
    }

    public List<PlayerDto> getNonAssignedCountryPlayer(long countryId){
        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        //SELECT * FROm players WHERE c_id=16 AND id NOT IN (SELECT DISTINCT p_id FROM team_players)

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Player> playerQuery = cb.createQuery(Player.class);
        Root<Player> playerRoot = playerQuery.from(Player.class);

       // playerQuery.where(cb.equal(playerRoot.get("c_id"),countryId));

        Subquery<Long> sq = playerQuery.subquery(Long.class);
        Root<TeamPlayer> tproot = sq.from(TeamPlayer.class);

        sq.select(tproot.get("player"));
        sq.distinct(true);

        Predicate[] p = {
                cb.equal(playerRoot.get("country"),countryId),
                playerRoot.get("id").in(sq).not()
        };

        playerQuery.where(p);

        var query = session.createQuery(playerQuery);
        var playerList = new ArrayList<Player>();
        try{
            playerList = (ArrayList<Player>) query.getResultList();
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        //System.out.println(playerList.size());
        var playerDtoList = new ArrayList<PlayerDto>();

        playerList.forEach(player->{
            var dto = new PlayerDto();
           BeanUtils.copyProperties(player,dto);
            playerDtoList.add(dto);
        });

        return playerDtoList;
    }

    public List<PlayerDto> getTeamPlayers(long teamId){
        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        //select * from team where p_id in (select p_id from teamplayer where t_id = teamId)

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Player> playerQuery = cb.createQuery(Player.class);
        Root<Player> playerRoot = playerQuery.from(Player.class);

        Subquery<Long> sq = playerQuery.subquery(Long.class);
        Root<TeamPlayer> tproot = sq.from(TeamPlayer.class);

        sq.select(tproot.get("player"));
        sq.where(cb.equal(tproot.get("team"),teamId));
        sq.distinct(true);

        Predicate[] p = {
                playerRoot.get("id").in(sq)
        };

        playerQuery.where(p);

        var query = session.createQuery(playerQuery);
        var playerList = new ArrayList<Player>();
        try{
            playerList = (ArrayList<Player>) query.getResultList();
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        //System.out.println(playerList.size());
        var playerDtoList = new ArrayList<PlayerDto>();

        playerList.forEach(player->{
            var dto = new PlayerDto();
            BeanUtils.copyProperties(player,dto);
            playerDtoList.add(dto);
        });

        return playerDtoList;
    }


}
