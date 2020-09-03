package com.noor.blog.services;

import com.noor.blog.config.persistancy.HibernateConfig;
import com.noor.blog.dtos.UserDto;
import com.noor.blog.exceptions.ResourceNotFoundException;
import com.noor.blog.models.User;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {


    private HibernateConfig hibernateConfig;

    public UserService(HibernateConfig hibernateConfig) {
        this.hibernateConfig = hibernateConfig;
    }

    @Transactional
    public void saveUser(User user){

        var session = hibernateConfig.getSession();
        var tx = session.getTransaction();

        if (!tx.isActive()){
            tx = session.beginTransaction();
        }

        try{
            session.save(user);
            tx.commit();
        }catch (HibernateException e){
            if (tx!= null){
                tx.rollback();
            }
            e.printStackTrace();

        }finally {
            session.close();
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var session = hibernateConfig.getSession();
        var tx = session.getTransaction();

        if (!tx.isActive()){
            tx = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> userCriteriaQuery = cb.createQuery(User.class);
        Root<User> root = userCriteriaQuery.from(User.class);

        userCriteriaQuery.where(cb.equal(root.get("name"),username));

        var query  = session.createQuery(userCriteriaQuery);
        var user = new User();
        try {
            user = query.getSingleResult();
        }catch (HibernateException e){

            e.printStackTrace();
        }finally {
            session.close();
        }

        if (user ==  null){
            throw  new UsernameNotFoundException("No user found with this email address.");
        }

        List<GrantedAuthority> authorities = new java.util.ArrayList<>();

        User finalUser = user;
        authorities.add((GrantedAuthority) () -> finalUser.getRole().name());


        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);

    }



    public boolean isUserExists(String username){

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> userCriteriaQuery = cb.createQuery(User.class);
        Root<User> root = userCriteriaQuery.from(User.class);

        userCriteriaQuery.where(cb.equal(root.get("name"), username));
        var query = session.createQuery(userCriteriaQuery);

        var userList = new ArrayList<User>();
        try {
            userList = (ArrayList<User>) query.getResultList();
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }


        return userList.size() > 0 ? true: false;

    }

    public UserDto getUserDtoByName(String userName){

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> userCriteriaQuery = cb.createQuery(User.class);
        Root<User> root = userCriteriaQuery.from(User.class);

        userCriteriaQuery.where(cb.equal(root.get("name"), userName));
        var query = session.createQuery(userCriteriaQuery);

        var userList = new ArrayList<User>();
        try {
            userList = (ArrayList<User>) query.getResultList();
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        var userDto = new UserDto();
        if (userList.size() > 0){

            BeanUtils.copyProperties(userList.get(0),userDto);
        }else {
            throw new ResourceNotFoundException("No user found");
        }

        return userDto;
    }


    @Transactional
    public void updateuserProfilePicture(String name, String path) {
        var session = hibernateConfig.getSession();
        var transection = session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaUpdate<User> tpquery = criteriaBuilder.createCriteriaUpdate(User.class);
        Root<User> root = tpquery.from(User.class);
        tpquery.where(criteriaBuilder.equal(root.get("name"), name));
        tpquery.set("profilePictureUrl",path);

        var query = session.createQuery(tpquery);

        try {
            int updatevalue = query.executeUpdate();
            transection.commit();

            System.out.println("update value: "+updatevalue);

        }catch(HibernateException e) {

            if(transection!= null ) {
                transection.rollback();
            }
            e.printStackTrace();

        }finally {
            session.close();
        }

    }


    public Long countTotalUser(){

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> userCriteriaQuery = cb.createQuery(Long.class);
        Root<User> root = userCriteriaQuery.from(User.class);
        userCriteriaQuery.select(cb.count(root.get("id")));


        var query = session.createQuery(userCriteriaQuery);

        Long totalUser = Long.valueOf(0);
        try {
            totalUser =   query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return totalUser;
    }

    public Long countActiveUser(){

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> userCriteriaQuery = cb.createQuery(Long.class);
        Root<User> root = userCriteriaQuery.from(User.class);
        userCriteriaQuery.select(cb.count(root.get("id")));
        userCriteriaQuery.where(cb.isTrue(root.get("isActive")));


        var query = session.createQuery(userCriteriaQuery);

        Long activeUser = Long.valueOf(0);
        try {
            activeUser =   query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return activeUser;
    }
}
