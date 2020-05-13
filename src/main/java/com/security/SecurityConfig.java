package com.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Project: springSecurity 
 * File: SecurityConfig.java
 * @author jaime 
 * Em 11-05-2020 **/

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	 
	@Bean
	public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	//Autention in Memory
	@Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
          .withUser("user").password(passwordEncoder().encode("123")).roles("USER")
          .and()
          .withUser("admin").password(passwordEncoder().encode("123")).roles("ADMIN");
    }
	//Metodos httpSecurity
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 *   roles admin acess /admin/**
		 *   roles user acess /user/***
		 */
		http.csrf().disable();
		
		//The pages not require Login
        http.authorizeRequests().antMatchers("/","/home","/login").permitAll();
        //The Pages Require Login
        http.authorizeRequests().antMatchers("/admin","/user").hasAnyRole("ADMIN")
                                .antMatchers("/user").hasAnyRole("USER","ADMIN")
                                .anyRequest().authenticated();
        //Config Form Login
        http.authorizeRequests().and().formLogin()
        .loginPage("/login") //Formulario de Login Personalizado
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/user",true)
        .failureUrl("/login?error=true")
        .usernameParameter("username")
        .passwordParameter("password")
        //Config Logout Page
        .and()
        .logout()
        .logoutUrl("/logout")
        .deleteCookies("JSESSIONID");
		 
	}
	/*Permit static images and css*/
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/static/**", "/images/**");
	}
}
