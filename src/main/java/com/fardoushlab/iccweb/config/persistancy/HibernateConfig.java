package com.fardoushlab.iccweb.config.persistancy;

import javassist.Modifier;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;

import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HibernateConfig {

    private SessionFactory sessionFactory = null;
    private Session session = null;

    public Session getSession(){
        try{
            this.session = createAndGetLocalSessionFactoryBean().getCurrentSession();

        }catch(HibernateException e){
            e.printStackTrace();
            this.session = createAndGetLocalSessionFactoryBean().openSession();
        }

        return this.session;

    }


   public CriteriaBuilder getCriteriaBuilder() {

        Session session = getSession();
        var tx = session.getTransaction();
        if(!tx.isActive()) {
            tx = session.beginTransaction();
        }
        return session.getCriteriaBuilder();

    }



    public SessionFactory createAndGetLocalSessionFactoryBean() {

        if(this.sessionFactory == null) {

            try {
                Configuration configuration = new Configuration();
                Properties settings = getProperties("hibernate.properties");
                configuration.setProperties(settings);
                configuration.addPackage("com.fardoushlab.iccweb.models");

                for(Class<?> modelClass: (new Reflections("com.fardoushlab.iccweb.models")).getTypesAnnotatedWith(Entity.class) ) {

                    if(!Modifier.isAbstract(modelClass.getModifiers())) {
                        configuration.addAnnotatedClass(modelClass);
                    }

                }

                StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder()
                        .applySettings(settings);

                sessionFactory = configuration.buildSessionFactory(serviceRegistryBuilder.build());
            } catch (MappingException e) {

                e.printStackTrace();
            } catch (HibernateException e) {
                e.printStackTrace();
            }	catch(Exception e) {
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }


    private Properties getProperties(String propertyFileName) {

        Properties properties = new Properties();
        InputStream input = Hibernate.class.getClassLoader().getResourceAsStream(propertyFileName);

        try {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }


}
