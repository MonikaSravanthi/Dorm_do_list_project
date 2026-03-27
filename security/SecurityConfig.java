package com.taskmanager.taskmanager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())   // ← disables default /login
                .httpBasic(basic -> basic.disable());

        http.authorizeHttpRequests(configurer -> configurer
                .requestMatchers(
                        "/index.html",
                        "/login.html",
                        "/loginScript.js",
                        "/signup.html",
                        "/registration",
                        "/api/login",
//                        "/favicon.ico",
                        "/style.css",
                        "/script.js",
                        "/images/**"

                ).permitAll().requestMatchers(HttpMethod.GET,"/api/tasks").hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.GET,"/api/task/**").hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.GET,"/api/users").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/tasks").hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.POST,"/api/user/file").hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.GET,"/api/users/me").hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.PATCH,"/api/update-status").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "/api/tasks/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/tasks/**").hasRole("ADMIN")
                        .requestMatchers("/api/user-tasks").hasRole("USER")

                .anyRequest().authenticated())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);




return http.build();

    }}
