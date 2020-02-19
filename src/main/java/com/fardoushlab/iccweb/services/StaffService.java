package com.fardoushlab.iccweb.services;

import com.fardoushlab.iccweb.config.persistancy.HibernateConfig;
import com.fardoushlab.iccweb.dtos.StaffDto;
import com.fardoushlab.iccweb.models.CoachingStaff;
import com.fardoushlab.iccweb.models.TeamStaff;
import com.fardoushlab.iccweb.request_models.Staff;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class StaffService {
    private HibernateConfig hibernateConfig;

    public StaffService(HibernateConfig hibernateConfig) {
        this.hibernateConfig = hibernateConfig;
    }

    @Transactional
    public void addStaff(StaffDto staffDto){

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        CoachingStaff staff = new CoachingStaff();
        BeanUtils.copyProperties(staffDto,staff);

        try{
            session.save(staff);
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

 /*   public boolean isStaffAlreadyExists(String staffName){
        //staffName = staffName.trim().toUpperCase();

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Staff> staffCriteriaQuery = cb.createQuery(Staff.class);
        Root<Staff> root = staffCriteriaQuery.from(Staff.class);

        staffCriteriaQuery.where(cb.equal(root.get("name"), staffName));
        var query = session.createQuery(staffCriteriaQuery);

        var staffList = new ArrayList<Staff>();
        try {
            staffList = (ArrayList<Staff>) query.getResultList();
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }


        return staffList.size() > 0 ? true: false;

    }*/

    public List<StaffDto> getAllStaff(){

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<CoachingStaff> staffCriteriaQuery = cb.createQuery(CoachingStaff.class);
        Root<CoachingStaff> root = staffCriteriaQuery.from(CoachingStaff.class);

        var query = session.createQuery(staffCriteriaQuery);

        var staffDtoList = new ArrayList<StaffDto>();
        try {
            query.getResultList().forEach(staff -> {
                StaffDto dto = new StaffDto();
                BeanUtils.copyProperties(staff,dto);
                staffDtoList.add(dto);
            });
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }


        return staffDtoList;
    }

    public List<StaffDto> getNonAssignedCountryStaff(long countryId){
        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        //SELECT * FROm staffs WHERE c_id=16 AND id NOT IN (SELECT DISTINCT p_id FROM team_staffs)

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<CoachingStaff> staffQuery = cb.createQuery(CoachingStaff.class);
        Root<CoachingStaff> staffRoot = staffQuery.from(CoachingStaff.class);

        // staffQuery.where(cb.equal(staffRoot.get("c_id"),countryId));

        Subquery<Long> sq = staffQuery.subquery(Long.class);
        Root<TeamStaff> tsroot = sq.from(TeamStaff.class);

        sq.select(tsroot.get("staff"));
        sq.distinct(true);

        Predicate[] p = {
                cb.equal(staffRoot.get("country"),countryId),
                staffRoot.get("id").in(sq).not()
        };

        staffQuery.where(p);

        var query = session.createQuery(staffQuery);
        var staffList = new ArrayList<CoachingStaff>();
        try{
            staffList = (ArrayList<CoachingStaff>) query.getResultList();
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        //System.out.println(staffList.size());
        var staffDtoList = new ArrayList<StaffDto>();

        staffList.forEach(staff->{
            var dto = new StaffDto();
            BeanUtils.copyProperties(staff,dto);
            staffDtoList.add(dto);
        });

        return staffDtoList;
    }

    public List<StaffDto> getTeamStaffs(long teamId){
        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }



        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<CoachingStaff> staffQuery = cb.createQuery(CoachingStaff.class);
        Root<CoachingStaff> staffRoot = staffQuery.from(CoachingStaff.class);

        Subquery<Long> sq = staffQuery.subquery(Long.class);
        Root<TeamStaff> tproot = sq.from(TeamStaff.class);

        sq.select(tproot.get("staff"));
        sq.where(cb.equal(tproot.get("team"),teamId));
        sq.distinct(true);

        Predicate[] p = {
                staffRoot.get("id").in(sq)
        };

        staffQuery.where(p);

        var query = session.createQuery(staffQuery);
        var staffList = new ArrayList<CoachingStaff>();
        try{
            staffList = (ArrayList<CoachingStaff>) query.getResultList();
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        //System.out.println(staffList.size());
        var staffDtoList = new ArrayList<StaffDto>();

        staffList.forEach(staff->{
            var dto = new StaffDto();
            BeanUtils.copyProperties(staff,dto);
            staffDtoList.add(dto);
        });

        return staffDtoList;
    }
}
