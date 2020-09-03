package com.noor.blog;

import com.noor.blog.config.persistancy.HibernateConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Configuration
@ComponentScan(basePackages = {"com.noor.blog.config.security",
        "com.noor.blog.config.persistancy",
        "com.noor.blog.services" })
public class AppConfig {


    @Bean
    public StandardServletMultipartResolver multipartResolver(){
        return new StandardServletMultipartResolver();
    }

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
