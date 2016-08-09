package com.example;

/**
 * Created by lav on 29/7/16.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

@Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
               .antMatchers("/home","/").permitAll()
               .antMatchers("/purchasePannel").hasAuthority("ACCOUNT")
                .antMatchers("/adminPannel").hasAuthority("ADMIN")
                .antMatchers("/productionPannel").hasAuthority("PRODUCE")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().permitAll().and().exceptionHandling().accessDeniedPage("/403");

        http.
                csrf().
                disable();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {


        auth.userDetailsService(userDetailsService());

    }
    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }


}



