package com.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
	//Usuarios
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
		http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/","/home","/login").permitAll()
        .antMatchers("/admin/**").hasAnyRole("ADMIN")
        .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login") //Formulario de Login Personalizado
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/user",true)
        .and()
        .logout()
        .logoutUrl("/logout")
        .deleteCookies("JSESSIONID");
		 
	}

}
