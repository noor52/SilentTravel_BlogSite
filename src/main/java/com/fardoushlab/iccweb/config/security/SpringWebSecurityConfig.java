package com.fardoushlab.iccweb.config.security;


import com.fardoushlab.iccweb.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Autowired
    public SpringWebSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

       // auth.inMemoryAuthentication().withUser("Admin").password("{noop}secret").roles("ADMIN");
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/images/**","/profile/images/**", "/css/**", "/js/**","/auth/register")
                .permitAll()
            .and()
                .authorizeRequests()
                .antMatchers("/user/add-admin").hasRole("SUPER_ADMIN")
                .antMatchers("/country/show-all").hasAnyRole("SUPER_ADMIN","ADMIN","TEAM_MANAGER","CAPTAIN","PLAYER","COACHING_STAFF","USER")
                .antMatchers("/country/add","/country/edit","/country/delete").hasAnyRole("SUPER_ADMIN","ADMIN")
                .antMatchers("/team/add","/team/edit","/team/delete").hasAnyRole("SUPER_ADMIN","ADMIN")
                .antMatchers("/team/add-team-staff").hasAnyRole("SUPER_ADMIN","ADMIN","TEAM_MANAGER")
                .antMatchers("/team/add-team-player").hasAnyRole("SUPER_ADMIN","TEAM_MANAGER")
                .antMatchers("/team/team-members").hasAnyRole("SUPER_ADMIN","ADMIN","TEAM_MANAGER","CAPTAIN","PLAYER","COACHING_STAFF","USER")
                .antMatchers("/player/add").hasAnyRole("SUPER_ADMIN","TEAM_MANAGER")
                .antMatchers("/player/edit").hasAnyRole("SUPER_ADMIN","TEAM_MANAGER","PLAYER")
                .antMatchers("/player/delete").hasAnyRole("SUPER_ADMIN","TEAM_MANAGER")
                .antMatchers("/staff/add","/staff/edit").hasAnyRole("SUPER_ADMIN","ADMIN","TEAM_MANAGER")
                .antMatchers("/staff/delete").hasAnyRole("SUPER_ADMIN","ADMIN")
                .antMatchers("/staff/show-all").hasAnyRole("SUPER_ADMIN","ADMIN","TEAM_MANAGER","CAPTAIN","PLAYER","COACHING_STAFF","USER")

                .anyRequest()
                .authenticated()
            .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .loginProcessingUrl("/login-processing")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/index")
                .failureUrl("/login?error=true")
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
            .and()
                .exceptionHandling()
                .accessDeniedPage("/403");

    }
}
