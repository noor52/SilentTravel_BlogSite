package com.noor.blog.services;

import com.noor.blog.config.persistancy.HibernateConfig;
import com.noor.blog.dtos.CountryDto;
import com.noor.blog.exceptions.ResourceAlreadyExistException;
import com.noor.blog.exceptions.ResourceNotFoundException;
import com.noor.blog.models.Country;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {
    private HibernateConfig hibernateConfig;

    public CountryService(HibernateConfig hibernateConfig) {
        this.hibernateConfig = hibernateConfig;
    }

    @Transactional
    public void addCountry(String countryName) {

        if (isCountryAlreadyExists(countryName))
            throw new ResourceAlreadyExistException("Sorry, a country already exists with this name");

        countryName = countryName.trim().toUpperCase();


        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        Country country = new Country();
        country.setName(countryName);
        country.setActive(true);
        try {
            session.save(country);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Transactional
    public void saveEditedCourse(CountryDto countryDto) {

        var session = hibernateConfig.getSession();
        var transection = session.getTransaction();

        if (!transection.isActive()) {
            transection = session.beginTransaction();
        }

        Country country = new Country();

        BeanUtils.copyProperties(countryDto, country);

        try {
            session.update(country);
            transection.commit();
        } catch (HibernateException e) {
            if (transection != null) {
                transection.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }


    }

    public boolean isCountryAlreadyExists(String countryName) {
        countryName = countryName.trim().toUpperCase();

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Country> countryCriteriaQuery = cb.createQuery(Country.class);
        Root<Country> root = countryCriteriaQuery.from(Country.class);

        countryCriteriaQuery.where(cb.equal(root.get("name"), countryName));
        var query = session.createQuery(countryCriteriaQuery);

        var countryList = new ArrayList<Country>();
        try {
            countryList = (ArrayList<Country>) query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }


        return countryList.size() > 0;

    }

    public List<CountryDto> getAllCountry() {

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Country> countryCriteriaQuery = cb.createQuery(Country.class);
        Root<Country> root = countryCriteriaQuery.from(Country.class);
        countryCriteriaQuery.where(cb.isTrue(root.get("isActive")));

        var query = session.createQuery(countryCriteriaQuery);

        var countryDtoList = new ArrayList<CountryDto>();
        try {
            query.getResultList().forEach(country -> {
                CountryDto dto = new CountryDto();
                BeanUtils.copyProperties(country, dto);
                countryDtoList.add(dto);
            });
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }


        return countryDtoList;
    }

    public CountryDto getCountryDtoById(long countryId) {
        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();
        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Country> countryCriteriaQuery = cb.createQuery(Country.class);
        Root<Country> root = countryCriteriaQuery.from(Country.class);

        countryCriteriaQuery.where(cb.equal(root.get("id"), countryId));
        var query = session.createQuery(countryCriteriaQuery);

        Country country = null;
        try {
            country = query.getSingleResult();

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        if (country == null) {
            throw new ResourceNotFoundException("No corresponding country found");
        }

        var dto = new CountryDto();
        BeanUtils.copyProperties(country, dto);
        return dto;
    }

    public Country getCountryById(long countryId) {
        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Country> countryCriteriaQuery = cb.createQuery(Country.class);
        Root<Country> root = countryCriteriaQuery.from(Country.class);

        countryCriteriaQuery.where(cb.equal(root.get("id"), countryId));
        var query = session.createQuery(countryCriteriaQuery);

        Country country = null;
        try {
            country = query.getSingleResult();

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        if (country == null) {
            throw new ResourceNotFoundException("No corresponding country found");
        }

        return country;
    }


    @Transactional
    public void changeCountryActiveStatus(long countryId,boolean isActive) {

            var session = hibernateConfig.getSession();
            var transection = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaUpdate<Country> ccd = criteriaBuilder.createCriteriaUpdate(Country.class);
            Root<Country> root = ccd.from(Country.class);
            ccd.where(criteriaBuilder.equal(root.get("id"), countryId));
            ccd.set("isActive",isActive);

            var query = session.createQuery(ccd);

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

    public Long countTotalcountry(){

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> countryCriteriaQuery = cb.createQuery(Long.class);
        Root<Country> root = countryCriteriaQuery.from(Country.class);
        countryCriteriaQuery.select(cb.count(root.get("name")));


        var query = session.createQuery(countryCriteriaQuery);

        Long totalCountry = Long.valueOf(0);
        try {
            totalCountry =   query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return totalCountry;
    }

    public Long countActivecountry(){

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> countryCriteriaQuery = cb.createQuery(Long.class);
        Root<Country> root = countryCriteriaQuery.from(Country.class);
        countryCriteriaQuery.select(cb.count(root.get("name")));
        countryCriteriaQuery.where(cb.isTrue(root.get("isActive")));


        var query = session.createQuery(countryCriteriaQuery);

        Long activeCountry = Long.valueOf(0);
        try {
            activeCountry =   query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return activeCountry;
    }


    }
