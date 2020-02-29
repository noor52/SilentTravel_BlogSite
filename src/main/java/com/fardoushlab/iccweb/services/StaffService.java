package com.fardoushlab.iccweb.services;

import com.fardoushlab.iccweb.config.persistancy.HibernateConfig;
import com.fardoushlab.iccweb.dtos.StaffDto;
import com.fardoushlab.iccweb.exceptions.ResourceNotFoundException;
import com.fardoushlab.iccweb.models.CoachingStaff;
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

    @Transactional
    public void saveEditedCoachingStaff(StaffDto staffDto){

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        CoachingStaff staff = new CoachingStaff();
        BeanUtils.copyProperties(staffDto,staff);

        try{
            //session.save(staff);
            session.update(staff);
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

    public StaffDto  getCoachingStaffById(long staffId){


        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<CoachingStaff> staffCriteriaQuery = cb.createQuery(CoachingStaff.class);
        Root<CoachingStaff> root = staffCriteriaQuery.from(CoachingStaff.class);
        staffCriteriaQuery.where(cb.equal(root.get("id"),staffId));

        var query = session.createQuery(staffCriteriaQuery);

        StaffDto staffDto = new StaffDto();
        try {
            CoachingStaff staff = query.getSingleResult();
            if (staff == null){
                throw new ResourceNotFoundException("No staff found");
            }
            BeanUtils.copyProperties(staff,staffDto);
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return staffDto;
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

    @Transactional
    public void changeCoachingStaffActiveStatus(long staffId, boolean isActive) {

        var session = hibernateConfig.getSession();
        var transection = session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaUpdate<CoachingStaff> staffdelete = criteriaBuilder.createCriteriaUpdate(CoachingStaff.class);
        Root<CoachingStaff> root = staffdelete.from(CoachingStaff.class);
        staffdelete.where(criteriaBuilder.equal(root.get("id"), staffId));
        staffdelete.set("isActive",isActive);

        var query = session.createQuery(staffdelete);

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
    public void deactivateTeamStaff(long staffId){

        var session = hibernateConfig.getSession();
        var transection = session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaUpdate<TeamStaff> ctquery = criteriaBuilder.createCriteriaUpdate(TeamStaff.class);
        Root<TeamStaff> root = ctquery.from(TeamStaff.class);
        ctquery.where(criteriaBuilder.equal(root.get("staff"), staffId));
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
    public void changeCountryCoachingStaffActiveStatus(long countryId, boolean isActive) {

        var session = hibernateConfig.getSession();
        var transection = session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaUpdate<CoachingStaff> staffdelete = criteriaBuilder.createCriteriaUpdate(CoachingStaff.class);
        Root<CoachingStaff> root = staffdelete.from(CoachingStaff.class);
        staffdelete.where(criteriaBuilder.equal(root.get("country"), countryId));
        staffdelete.set("isActive",isActive);

        var query = session.createQuery(staffdelete);

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


    public Long countTotalStaff(){

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> staffCriteriaQuery = cb.createQuery(Long.class);
        Root<CoachingStaff> root = staffCriteriaQuery.from(CoachingStaff.class);
        staffCriteriaQuery.select(cb.count(root.get("id")));


        var query = session.createQuery(staffCriteriaQuery);

        Long totalStaff = Long.valueOf(0);
        try {
            totalStaff =   query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return totalStaff;
    }

    public Long countActiveStaff(){

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> staffCriteriaQuery = cb.createQuery(Long.class);
        Root<CoachingStaff> root = staffCriteriaQuery.from(CoachingStaff.class);
        staffCriteriaQuery.select(cb.count(root.get("id")));
        staffCriteriaQuery.where(cb.isTrue(root.get("isActive")));


        var query = session.createQuery(staffCriteriaQuery);

        Long activeStaff = Long.valueOf(0);
        try {
            activeStaff =   query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return activeStaff;
    }

}
