package com.fardoushlab.iccweb;

import com.fardoushlab.iccweb.config.persistancy.HibernateConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan(basePackages = {"com.fardoushlab.iccweb.config.security",
        "com.fardoushlab.iccweb.config.persistancy",
        "com.fardoushlab.iccweb.services" })
public class AppConfig {

    @Bean
    public HibernateConfig getHibernateConfig(){
        return new HibernateConfig();
    }

    @Bean
    public GlobalExceptionHandler getGobalExcetionHandler(){
        return new GlobalExceptionHandler();
    }

    @Bean
    public PasswordEncoder PasswordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
